package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zk.ui.util.Clients;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImportViewModel {

    private static final Logger log = LoggerFactory.getLogger(ImportViewModel.class);

    private List<Concours> allConcours;
    private List<Candidat> allCandidats;
    private List<DocumentConcours> allDocumentTypes;
    private List<Concours> filteredConcours;
    private List<Candidat> filteredCandidats;
    private Concours selectedConcours;
    private Candidat selectedCandidat;

    private List<CandidatDocumentStatus> candidatDocumentStatusList;
    private DocumentConcours targetDocumentTypeForUpload;
    
    private Media pendingMedia;
    private String pendingFileName;
    private long pendingFileSize;
    private String uploadStatus = "idle";
    
    // NOUVELLES PROPRIÉTÉS POUR LA BARRE DE PROGRESSION
    private boolean uploading = false;
    private int uploadProgress = 0;
    private int importStep = 1;

    @Init
    public void init() {
        allConcours = ConcoursGestion.findAll();
        allCandidats = CandidatGestion.findAll();
        allDocumentTypes = DocumentConcoursGestion.findAll();
        filteredConcours = allConcours;
        candidatDocumentStatusList = Collections.emptyList();
    }

    @Command @NotifyChange({"filteredConcours"})
    public void filterConcours(@BindingParam("text") String text) {
        if (text == null || text.trim().isEmpty()) {
            filteredConcours = allConcours;
        } else {
            String lower = text.toLowerCase();
            filteredConcours = allConcours.stream()
                    .filter(c -> c.getDisplayInfo().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }
    }

    @Command @NotifyChange({"filteredCandidats"})
    public void filterCandidats(@BindingParam("text") String text) {
        if (selectedConcours == null) return;
        List<Candidat> list = allCandidats.stream()
                .filter(c -> selectedConcours.getConcoursId().equals(c.getConcoursId()))
                .collect(Collectors.toList());
        if (text == null || text.trim().isEmpty()) {
            filteredCandidats = list;
        } else {
            String lower = text.toLowerCase();
            filteredCandidats = list.stream()
                    .filter(c -> (c.getNom() + " " + c.getPrenom()).toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }
    }

    @Command
    @NotifyChange({"selectedConcours", "filteredCandidats", "selectedCandidat", "candidatDocumentStatusList", "candidatDossierTitle", "targetDocumentTypeForUpload", "pendingFileName", "uploadStatus", "importStep"})
    public void onSelectConcours() {
        selectedCandidat = null;
        candidatDocumentStatusList = Collections.emptyList();
        targetDocumentTypeForUpload = null;
        pendingFileName = null;
        pendingMedia = null;
        uploadStatus = "idle";
        importStep = 1;
        if (selectedConcours != null) {
            filteredCandidats = allCandidats.stream()
                    .filter(c -> selectedConcours.getConcoursId().equals(c.getConcoursId()))
                    .collect(Collectors.toList());
        } else {
            filteredCandidats = Collections.emptyList();
        }
    }

    @Command
    @NotifyChange({"selectedCandidat", "candidatDocumentStatusList", "candidatDossierTitle", "targetDocumentTypeForUpload", "pendingFileName", "uploadStatus", "importStep"})
    public void onSelectCandidat() {
        targetDocumentTypeForUpload = null;
        pendingFileName = null;
        pendingMedia = null;
        uploadStatus = "idle";
        if (selectedCandidat != null) {
            loadCandidatDossier();
            importStep = 2;
        } else {
            candidatDocumentStatusList = Collections.emptyList();
            importStep = 1;
        }
    }
    
    @Command
    @NotifyChange({"targetDocumentTypeForUpload", "pendingFileName", "pendingMedia", "importStep"})
    public void prepareUploadForDocument(@BindingParam("doc") DocumentConcours docType) {
        if (docType.equals(this.targetDocumentTypeForUpload)) {
            this.targetDocumentTypeForUpload = null; 
        } else {
            this.targetDocumentTypeForUpload = docType;
        }
        this.pendingMedia = null;
        this.pendingFileName = null;
        this.importStep = (this.targetDocumentTypeForUpload != null) ? 3 : (selectedCandidat != null ? 2 : 1);
    }

    @Command
    @NotifyChange({"pendingFileName", "pendingFileSize", "uploadStatus"})
    public void handleFileUpload(@BindingParam("file") Media media) {
        if (media != null) {
            pendingMedia = media;
            pendingFileName = media.getName();
            pendingFileSize = media.getByteData() != null ? media.getByteData().length : 0;
            uploadStatus = "idle";
        }
    }
    
    @Command
    @NotifyChange({"pendingFileName", "pendingMedia", "uploadStatus", "targetDocumentTypeForUpload", "importStep"})
    public void cancelUpload(){
        pendingMedia = null;
        pendingFileName = null;
        pendingFileSize = 0;
        uploadStatus = "idle";
        targetDocumentTypeForUpload = null; // Permet de revenir à la liste
        importStep = selectedCandidat != null ? 2 : 1;
    }

    @Command
    @NotifyChange("importStep")
    public void goToImportStep(@BindingParam("step") int step) {
        if (step <= 1) {
            importStep = 1;
            return;
        }
        if (step == 2) {
            if (selectedCandidat == null) return;
            importStep = 2;
        } else if (step == 3) {
            if (targetDocumentTypeForUpload == null) return;
            importStep = 3;
        }
    }

    @Command
    @NotifyChange({"candidatDocumentStatusList", "pendingFileName", "pendingMedia", "targetDocumentTypeForUpload", "uploadStatus", "uploading", "uploadProgress"})
    public void executeImport() {
        if (pendingMedia == null || selectedCandidat == null || targetDocumentTypeForUpload == null) {
            Messagebox.show("Contexte d'importation invalide.", "Erreur", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        Clients.showBusy("Importation en cours...");
        this.uploading = true;
        this.uploadProgress = 10;
        BindUtils.postNotifyChange(null, null, this, "uploading");
        BindUtils.postNotifyChange(null, null, this, "uploadProgress");
        
        ListeDossierConcoursCandidat existing = ListeDossierConcoursCandidatGestion.findByCandidatIdAndDocumentId(
                selectedCandidat.getCandidatId(), targetDocumentTypeForUpload.getDocumentConcoursId());

        this.uploadProgress = 50;
        BindUtils.postNotifyChange(null, null, this, "uploadProgress");

        if (existing != null) {
            String message = String.format("Un document '%s' existe déjà pour ce candidat (version %d). Voulez-vous le remplacer par une nouvelle version ?",
                                           targetDocumentTypeForUpload.getLibelle(), existing.getVersion());
            Messagebox.show(message, "Confirmation de mise à jour", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, event -> {
                if (Messagebox.ON_YES.equals(event.getName())) {
                    performUpdate(existing);
                } else {
                    this.uploading = false;
                    BindUtils.postNotifyChange(null, null, this, "uploading");
                    Clients.clearBusy();
                }
            });
        } else {
            performInsert();
        }
    }

    @Command
    public void viewDocument(@BindingParam("doc") ListeDossierConcoursCandidat doc) {
        if (doc == null || doc.getRemarqueFacultatif() == null) return;
        try {
            File file = new File(doc.getRemarqueFacultatif());
            if (file.exists()) {
                Filedownload.save(file, null);
            } else {
                Messagebox.show("Le fichier physique est introuvable sur le serveur.", "Erreur", Messagebox.OK, Messagebox.ERROR);
            }
        } catch (FileNotFoundException e) {
            log.error("Fichier non trouvé pour le téléchargement", e);
            Messagebox.show("Erreur lors du téléchargement du fichier.", "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void showHistory(@BindingParam("docType") DocumentConcours docType) {
        Messagebox.show("La fonctionnalité d'historique des versions pour '" + docType.getLibelle() + "' est en cours de développement.", 
                        "Information", Messagebox.OK, Messagebox.INFORMATION);
    }
    
    private void performUpdate(ListeDossierConcoursCandidat existing) {
        try {
            int newVersion = existing.getVersion() + 1;
            Path newPath = saveFile(newVersion);
            
            existing.setVersion(newVersion);
            existing.setRemarqueFacultatif(newPath.toString());
            existing.setEtatDocument(DocumentValidationEtat.EN_COURS.getCode());
            existing.setRemarque("Mis à jour le " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) + " - En attente de validation");
            
            ListeDossierConcoursCandidatGestion.updateAndIndex(existing);
            registerDocumentRecord(newPath, newVersion);
            
            postImportSuccess();
        } catch (IOException e) {
            log.error("Erreur lors de la mise à jour du fichier", e);
            this.uploading = false;
            BindUtils.postNotifyChange(null, null, this, "uploading");
            Clients.clearBusy();
            Messagebox.show("Erreur technique lors de la mise à jour du fichier.", "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    private void performInsert() {
        try {
            Path newPath = saveFile(1);
            
            ListeDossierConcoursCandidat entity = new ListeDossierConcoursCandidat();
            entity.setCandidatId(selectedCandidat.getCandidatId());
            entity.setDocumentConcoursId(targetDocumentTypeForUpload.getDocumentConcoursId());
            entity.setEtatDocument(DocumentValidationEtat.EN_COURS.getCode());
            entity.setRemarque("Importé le " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) + " - En attente de validation");
            entity.setRemarqueFacultatif(newPath.toString());
            entity.setActif(1);
            entity.setVersion(1);

            ListeDossierConcoursCandidatGestion.saveAndIndex(entity);
            registerDocumentRecord(newPath, 1);
            
            postImportSuccess();
        } catch (IOException e) {
            log.error("Erreur lors de la création du fichier", e);
            this.uploading = false;
            BindUtils.postNotifyChange(null, null, this, "uploading");
            Clients.clearBusy();
            Messagebox.show("Erreur technique lors de la création du fichier.", "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private Path saveFile(int version) throws IOException {
        String ext = getFileExtension(pendingMedia.getName());
        Path uploadDir = Paths.get(System.getProperty("user.home"), "gedi_storage", "concours",
                String.valueOf(selectedConcours.getConcoursId()), String.valueOf(selectedCandidat.getCandidatId()));
        Files.createDirectories(uploadDir);

        String typeSlug = sanitizeSlug(targetDocumentTypeForUpload.getLibelle());
        String storedFileName = String.format("%s_v%d%s", typeSlug, version, ext);
        Path targetPath = uploadDir.resolve(storedFileName);

        if (pendingMedia.isBinary()) {
            Files.write(targetPath, pendingMedia.getByteData());
        } else {
            try (InputStream in = pendingMedia.getStreamData()) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        return targetPath;
    }
    
    private void postImportSuccess(){
        this.uploadProgress = 100;
        BindUtils.postNotifyChange(null, null, this, "uploadProgress");
        Messagebox.show("Document importé avec succès !", "Succès", Messagebox.OK, Messagebox.INFORMATION);
        
        this.uploading = false;
        this.pendingMedia = null;
        this.pendingFileName = null;
        this.pendingFileSize = 0;
        this.targetDocumentTypeForUpload = null;
        this.importStep = 2;
        Clients.clearBusy();
        loadCandidatDossier();
        BindUtils.postNotifyChange(null, null, this, "candidatDocumentStatusList");
        BindUtils.postNotifyChange(null, null, this, "importStep");
    }

    private void registerDocumentRecord(Path storedPath, int version) {
        try {
            if (storedPath == null) return;
            Document doc = new Document();
            String candidateName = selectedCandidat != null ? (selectedCandidat.getNom() + " " + selectedCandidat.getPrenom()).trim() : "";
            String concoursLibelle = selectedConcours != null ? selectedConcours.getDisplayInfo() : "";
            String typeLabel = targetDocumentTypeForUpload != null ? targetDocumentTypeForUpload.getLibelle() : "Document";

            doc.setTitre(String.format("%s - %s", typeLabel, candidateName).trim());
            doc.setPath(storedPath.toString());
            doc.setType(typeLabel);
            doc.setTaille(Files.size(storedPath));
            doc.setVersion(version);
            doc.setRemarque("Import automatique via GEDI");
            doc.setResume(String.format("Candidat: %s | Concours: %s | Type: %s", candidateName, concoursLibelle, typeLabel));
            DocumentGestion.save(doc);
        } catch (Exception e) {
            log.warn("Impossible d'enregistrer le document global : {}", e.getMessage());
        }
    }

    private void loadCandidatDossier() {
        if (selectedCandidat == null) return;
        List<ListeDossierConcoursCandidat> existingDocs = ListeDossierConcoursCandidatGestion.findByCandidatId(selectedCandidat.getCandidatId());
        Map<Integer, ListeDossierConcoursCandidat> existingDocsMap = existingDocs.stream()
                .collect(Collectors.toMap(ListeDossierConcoursCandidat::getDocumentConcoursId, doc -> doc, (doc1, doc2) -> doc1.getVersion() > doc2.getVersion() ? doc1 : doc2));

        candidatDocumentStatusList = allDocumentTypes.stream()
                .map(docType -> new CandidatDocumentStatus(docType, existingDocsMap.get(docType.getDocumentConcoursId())))
                .collect(Collectors.toList());
    }
    
    private String getFileExtension(String filename) {
        return (filename != null && filename.lastIndexOf('.') > 0) ? filename.substring(filename.lastIndexOf('.')) : "";
    }

    private String sanitizeSlug(String s) {
        return (s == null) ? "" : s.trim().toLowerCase().replaceAll("[\\s/]+", "_").replaceAll("[^a-z0-9_.-]+", "");
    }

    private static String formatSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public List<Concours> getFilteredConcours() { return filteredConcours; }
    public List<Candidat> getFilteredCandidats() { return filteredCandidats; }
    public Concours getSelectedConcours() { return selectedConcours; }
    public void setSelectedConcours(Concours c) { this.selectedConcours = c; }
    public Candidat getSelectedCandidat() { return selectedCandidat; }
    public void setSelectedCandidat(Candidat c) { this.selectedCandidat = c; }
    public String getPendingFileName() { return pendingFileName; }
    public List<CandidatDocumentStatus> getCandidatDocumentStatusList() { return candidatDocumentStatusList; }
    public DocumentConcours getTargetDocumentTypeForUpload() { return targetDocumentTypeForUpload; }
    public long getPendingFileSize() { return pendingFileSize; }
    public String getUploadStatus() { return uploadStatus; }
    
    // NOUVEAUX GETTERS
    public boolean isUploading() { return uploading; }
    public int getUploadProgress() { return uploadProgress; }
    public int getImportStep() { return importStep; }

    public String getCandidatDossierTitle() {
        if (selectedCandidat != null) {
            return "Dossier de " + selectedCandidat.getPrenom() + " " + selectedCandidat.getNom();
        }
        return "Dossier du Candidat";
    }
    
    public static class CandidatDocumentStatus {
        private final DocumentConcours documentType;
        private final ListeDossierConcoursCandidat existingDocument;
        private final boolean isMissing;

        public CandidatDocumentStatus(DocumentConcours documentType, ListeDossierConcoursCandidat existingDocument) {
            this.documentType = documentType;
            this.existingDocument = existingDocument;
            this.isMissing = (existingDocument == null);
        }

        public DocumentConcours getDocumentType() { return documentType; }
        public ListeDossierConcoursCandidat getExistingDocument() { return existingDocument; }
        public boolean isMissing() { return isMissing; }
        public String getStatusLabel() { return isMissing ? "Non soumis" : "Soumis"; }
        public String getStatusSclass() { return isMissing ? "text-warning font-weight-bold" : "text-success font-weight-bold"; }
        public String getStatusIconSclass() { return isMissing ? "z-icon-times-circle text-warning" : "z-icon-check-circle text-success"; }
        public String getVersionInfo(){ return isMissing ? "N/A" : "v" + existingDocument.getVersion(); }

        public String getFileSizeDisplay() {
            if (isMissing || existingDocument.getRemarqueFacultatif() == null) return "-";
            try {
                long size = Files.size(Paths.get(existingDocument.getRemarqueFacultatif()));
                return formatSize(size);
            } catch (IOException e) {
                log.warn("Impossible de lire la taille du fichier : " + existingDocument.getRemarqueFacultatif());
                return "Erreur";
            }
        }

        public String getUploadDateDisplay() {
            if (isMissing || existingDocument.getRemarque() == null) return "-";
            String remarque = existingDocument.getRemarque();
            if (remarque.contains(" le ")) {
                return remarque.substring(remarque.indexOf(" le ") + 4);
            }
            return remarque;
        }
    }
}
