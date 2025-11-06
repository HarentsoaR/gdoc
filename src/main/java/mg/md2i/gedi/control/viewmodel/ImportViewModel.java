package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.gestionmetier.*;
import mg.md2i.gedi.services.LuceneService;
import mg.md2i.gedi.services.impl.LuceneServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Messagebox;

import java.io.InputStream;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ImportViewModel {

    private static final Logger log = LoggerFactory.getLogger(ImportViewModel.class);
    private final LuceneService luceneService = new LuceneServiceImpl();

    private List<Concours> allConcours;
    private List<Candidat> allCandidats;
    private List<DocumentConcours> allDocumentTypes;
    private List<Concours> filteredConcours;
    private List<Candidat> filteredCandidats;
    private List<DocumentConcours> filteredDocumentTypes;
    private Concours selectedConcours;
    private Candidat selectedCandidat;
    private DocumentConcours selectedDocumentType;
    private Media pendingMedia;
    private String pendingFileName;

    @Init
    public void init() {
        allConcours = ConcoursGestion.findAll();
        allCandidats = CandidatGestion.findAll();
        allDocumentTypes = DocumentConcoursGestion.findAll();
        filteredConcours = allConcours;
        filteredCandidats = Collections.emptyList();
        filteredDocumentTypes = allDocumentTypes;
    }

    @Command @NotifyChange("filteredConcours")
    public void filterConcours(@BindingParam("text") String text) {
        if (text == null || text.trim().isEmpty()) filteredConcours = allConcours;
        else {
            String lower = text.toLowerCase();
            filteredConcours = allConcours.stream()
                    .filter(c -> c.getDisplayInfo().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }
    }

    @Command @NotifyChange("filteredCandidats")
    public void filterCandidats(@BindingParam("text") String text) {
        if (selectedConcours == null) return;
        List<Candidat> list = allCandidats.stream()
                .filter(c -> selectedConcours.getConcoursId().equals(c.getConcoursId()))
                .collect(Collectors.toList());
        if (text == null || text.trim().isEmpty()) filteredCandidats = list;
        else {
            String lower = text.toLowerCase();
            filteredCandidats = list.stream()
                    .filter(c -> (c.getNom() + " " + c.getPrenom()).toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }
    }

    @Command @NotifyChange("filteredDocumentTypes")
    public void filterDocumentTypes(@BindingParam("text") String text) {
        if (text == null || text.trim().isEmpty()) filteredDocumentTypes = allDocumentTypes;
        else {
            String lower = text.toLowerCase();
            filteredDocumentTypes = allDocumentTypes.stream()
                    .filter(d -> d.getLibelle().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }
    }

    @Command @NotifyChange({"selectedConcours", "filteredCandidats", "selectedCandidat", "selectedDocumentType", "pendingFileName"})
    public void onSelectConcours() {
        selectedCandidat = null;
        selectedDocumentType = null;
        pendingMedia = null;
        pendingFileName = null;
        if (selectedConcours != null) {
            filteredCandidats = allCandidats.stream()
                    .filter(c -> selectedConcours.getConcoursId().equals(c.getConcoursId()))
                    .collect(Collectors.toList());
        } else filteredCandidats = Collections.emptyList();
    }

    @Command @NotifyChange({"selectedCandidat", "selectedDocumentType", "pendingFileName"})
    public void onSelectCandidat() {
        selectedDocumentType = null;
        pendingMedia = null;
        pendingFileName = null;
    }

    @Command @NotifyChange("pendingFileName")
    public void prepareUpload(@BindingParam("file") Media media) {
        if (media != null) {
            pendingMedia = media;
            pendingFileName = media.getName();
        }
    }

    @Command
    @NotifyChange({"pendingFileName", "pendingMedia"})
    public void executeImport() {
        if (pendingMedia == null || selectedCandidat == null || selectedConcours == null || selectedDocumentType == null) {
            Messagebox.show("Veuillez compléter toutes les étapes avant d'importer.", "Erreur de contexte", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        try {
            String ext = getFileExtension(pendingMedia.getName());
            Path uploadDir = Paths.get(System.getProperty("user.home"), "gedi_storage", "concours",
                    String.valueOf(selectedConcours.getConcoursId()), String.valueOf(selectedCandidat.getCandidatId()));
            Files.createDirectories(uploadDir);

            String concoursSlug = sanitizeSlug(buildConcoursIdentifier(selectedConcours));
            String typeSlug = sanitizeSlug(selectedDocumentType.getLibelle());
            String nameSlug = sanitizeSlug(selectedCandidat.getPrenom() != null ? selectedCandidat.getPrenom() : selectedCandidat.getNom());
            String baseName = typeSlug + "_" + nameSlug + "_" + concoursSlug;
            String storedFileName = baseName + ext;
            Path targetPath = uploadDir.resolve(storedFileName);
            int idx = 1;
            while (Files.exists(targetPath)) {
                storedFileName = baseName + "(" + (idx++) + ")" + ext;
                targetPath = uploadDir.resolve(storedFileName);
            }

            if (pendingMedia.isBinary()) Files.write(targetPath, pendingMedia.getByteData());
            else try (InputStream in = pendingMedia.getStreamData()) { Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING); }

            ListeDossierConcoursCandidat entity = new ListeDossierConcoursCandidat();
            entity.setCandidatId(selectedCandidat.getCandidatId());
            entity.setDocumentConcoursId(selectedDocumentType.getDocumentConcoursId());
            entity.setEtatDocument(1);
            entity.setRemarque("Importé le " + new java.util.Date());
            entity.setRemarqueFacultatif(targetPath.toString());
            entity.setActif(1);
            entity.setVersion(1);

            ListeDossierConcoursCandidat saved = ListeDossierConcoursCandidatGestion.saveAndIndex(entity);

            Messagebox.show("✅ Document '" + storedFileName + "' importé et indexé avec succès.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
            pendingMedia = null;
            pendingFileName = null;
        } catch (Exception ex) {
            log.error("Erreur lors de l'importation ou de l'indexation du fichier.", ex);
            Messagebox.show("Erreur lors de l'importation du fichier.", "Erreur Technique", Messagebox.OK, Messagebox.ERROR);
        }
    }


    private String buildConcoursIdentifier(Concours concours) {
        String avis = concours.getAvisConcours() != null ? concours.getAvisConcours().trim() : "";
        String arrete = concours.getNumeroArrete() != null ? concours.getNumeroArrete().trim() : "";
        if (!avis.isEmpty() && !arrete.isEmpty()) return avis + "-" + arrete;
        if (!avis.isEmpty()) return avis;
        if (!arrete.isEmpty()) return arrete;
        return "concours_" + concours.getConcoursId();
    }

    private String getFileExtension(String filename) {
        return (filename.lastIndexOf('.') > 0) ? filename.substring(filename.lastIndexOf('.')) : "";
    }

    private String sanitizeSlug(String s) {
        return (s == null) ? "" : s.trim().toLowerCase().replaceAll("[\\s/]+", "_").replaceAll("[^a-z0-9_.-]+", "");
    }

    public List<Concours> getFilteredConcours() { return filteredConcours; }
    public List<Candidat> getFilteredCandidats() { return filteredCandidats; }
    public List<DocumentConcours> getFilteredDocumentTypes() { return filteredDocumentTypes; }
    public Concours getSelectedConcours() { return selectedConcours; }
    public void setSelectedConcours(Concours c) { this.selectedConcours = c; }
    public Candidat getSelectedCandidat() { return selectedCandidat; }
    public void setSelectedCandidat(Candidat c) { this.selectedCandidat = c; }
    public DocumentConcours getSelectedDocumentType() { return selectedDocumentType; }
    public void setSelectedDocumentType(DocumentConcours d) { this.selectedDocumentType = d; }
    public String getPendingFileName() { return pendingFileName; }
}
