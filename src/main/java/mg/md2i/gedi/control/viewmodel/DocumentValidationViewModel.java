package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.entity.CentreExamen;
import mg.md2i.gedi.entity.Concours;
import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.CentreExamenGestion;
import mg.md2i.gedi.gestionmetier.ConcoursGestion;
import mg.md2i.gedi.gestionmetier.DocumentConcoursGestion;
import mg.md2i.gedi.gestionmetier.ListeDossierConcoursCandidatGestion;
import mg.md2i.gedi.viewmodel.dto.DocumentEtatFilterOption;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentValidationViewModel {

    private List<ValidationRow> dossiers = new ArrayList<>();
    private List<Concours> concoursList = new ArrayList<>();
    private List<CentreExamen> centreList = new ArrayList<>();
    private List<DocumentConcours> documentTypes = new ArrayList<>();

    private Concours selectedConcours;
    private CentreExamen selectedCentre;
    private DocumentConcours selectedDocumentType;
    private DocumentEtatFilterOption selectedEtatFilter;
    private List<DocumentEtatFilterOption> etatFilterOptions = new ArrayList<>();
    private String searchNomCandidat = "";

    @Init
    public void init() {
        concoursList = ConcoursGestion.findAll();
        centreList = CentreExamenGestion.findAll();
        documentTypes = DocumentConcoursGestion.findAll();
        buildEtatOptions();
        selectedEtatFilter = etatFilterOptions.stream()
                .filter(o -> o.getEtat() == DocumentValidationEtat.EN_COURS)
                .findFirst()
                .orElse(etatFilterOptions.get(0));
        loadDossiers();
    }

    private void buildEtatOptions() {
        etatFilterOptions = new ArrayList<>();
        etatFilterOptions.add(new DocumentEtatFilterOption(null, "Tous les états"));
        for (DocumentValidationEtat etat : DocumentValidationEtat.values()) {
            etatFilterOptions.add(new DocumentEtatFilterOption(etat, etat.getLabel()));
        }
    }

    private void loadDossiers() {
        List<ListeDossierConcoursCandidat> raw = ListeDossierConcoursCandidatGestion.findWithAdvancedFilters(
                selectedDocumentType != null ? selectedDocumentType.getDocumentConcoursId() : null,
                selectedConcours != null ? selectedConcours.getConcoursId() : null,
                selectedCentre != null ? selectedCentre.getCentreExamenId() : null,
                searchNomCandidat);

        Integer code = (selectedEtatFilter != null && selectedEtatFilter.getEtat() != null)
                ? selectedEtatFilter.getEtat().getCode()
                : null;

        if (code != null) {
            raw = raw.stream()
                    .filter(doc -> Objects.equals(doc.getEtatDocument(), code))
                    .collect(Collectors.toList());
        }

        dossiers = raw.stream()
                .map(ValidationRow::new)
                .sorted(Comparator.comparing(ValidationRow::buildSortableName))
                .collect(Collectors.toList());
    }

    @Command
    @NotifyChange("dossiers")
    public void applyFilters() {
        loadDossiers();
    }

    @Command
    @NotifyChange({"dossiers", "selectedConcours", "selectedCentre", "selectedDocumentType", "searchNomCandidat", "selectedEtatFilter"})
    public void clearFilters() {
        selectedConcours = null;
        selectedCentre = null;
        selectedDocumentType = null;
        searchNomCandidat = "";
        selectedEtatFilter = etatFilterOptions.stream()
                .filter(o -> o.getEtat() == DocumentValidationEtat.EN_COURS)
                .findFirst()
                .orElse(etatFilterOptions.get(0));
        loadDossiers();
    }

    @Command
    @NotifyChange("dossiers")
    public void changeEtat(@BindingParam("item") ValidationRow item,
                           @BindingParam("etat") int etatCode) {
        if (item == null) return;

        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(etatCode);
        if (etat == DocumentValidationEtat.REJETE && (item.getCommentaire() == null || item.getCommentaire().trim().isEmpty())) {
            Clients.showNotification("Veuillez saisir une remarque pour rejeter le document.", "warning", null, "top_center", 2500);
            return;
        }

        ListeDossierConcoursCandidat dossier = item.getDossier();
        dossier.setEtatDocument(etat.getCode());
        String commentaire = item.getCommentaire();
        if (commentaire == null || commentaire.trim().isEmpty()) {
            commentaire = defaultRemark(etat);
        }
        dossier.setRemarque(commentaire.trim());
        ListeDossierConcoursCandidatGestion.saveAndIndex(dossier);

        Clients.showNotification("Statut mis à jour : " + etat.getLabel(), "info", null, "top_center", 2000);
        loadDossiers();
    }

    private String defaultRemark(DocumentValidationEtat etat) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        if (etat == DocumentValidationEtat.VALIDE) {
            return "Validé le " + date;
        } else if (etat == DocumentValidationEtat.REJETE) {
            return "Rejeté le " + date;
        }
        return "En cours de validation";
    }

    @Command
    public void download(@BindingParam("item") ValidationRow item) {
        if (item == null || item.getDossier().getRemarqueFacultatif() == null) return;
        try {
            File file = new File(item.getDossier().getRemarqueFacultatif());
            if (!file.exists()) {
                Clients.showNotification("Fichier introuvable", "warning", null, "top_center", 2000);
                return;
            }
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) contentType = "application/octet-stream";
            Filedownload.save(file, contentType);
        } catch (Exception e) {
            Clients.showNotification("Impossible de télécharger le fichier.", "error", null, "top_center", 2500);
        }
    }

    public List<ValidationRow> getDossiers() {
        return dossiers;
    }

    public List<Concours> getConcoursList() {
        return concoursList;
    }

    public List<CentreExamen> getCentreList() {
        return centreList;
    }

    public List<DocumentConcours> getDocumentTypes() {
        return documentTypes;
    }

    public Concours getSelectedConcours() {
        return selectedConcours;
    }

    public void setSelectedConcours(Concours selectedConcours) {
        this.selectedConcours = selectedConcours;
    }

    public CentreExamen getSelectedCentre() {
        return selectedCentre;
    }

    public void setSelectedCentre(CentreExamen selectedCentre) {
        this.selectedCentre = selectedCentre;
    }

    public DocumentConcours getSelectedDocumentType() {
        return selectedDocumentType;
    }

    public void setSelectedDocumentType(DocumentConcours selectedDocumentType) {
        this.selectedDocumentType = selectedDocumentType;
    }

    public DocumentEtatFilterOption getSelectedEtatFilter() {
        return selectedEtatFilter;
    }

    public void setSelectedEtatFilter(DocumentEtatFilterOption selectedEtatFilter) {
        this.selectedEtatFilter = selectedEtatFilter;
    }

    public List<DocumentEtatFilterOption> getEtatFilterOptions() {
        return etatFilterOptions;
    }

    public String getSearchNomCandidat() {
        return searchNomCandidat;
    }

    public void setSearchNomCandidat(String searchNomCandidat) {
        this.searchNomCandidat = searchNomCandidat;
    }

    public static class ValidationRow {
        private final ListeDossierConcoursCandidat dossier;
        private String commentaire;

        public ValidationRow(ListeDossierConcoursCandidat dossier) {
            this.dossier = dossier;
            this.commentaire = dossier.getRemarque();
        }

        public ListeDossierConcoursCandidat getDossier() {
            return dossier;
        }

        public String getCommentaire() {
            return commentaire;
        }

        public void setCommentaire(String commentaire) {
            this.commentaire = commentaire;
        }

        public DocumentValidationEtat getEtat() {
            return DocumentValidationEtat.fromCode(dossier.getEtatDocument());
        }

        public String getCandidatLabel() {
            Candidat candidat = dossier.getCandidat();
            if (candidat == null) return "-";
            String nom = Optional.ofNullable(candidat.getNom()).orElse("");
            String prenom = Optional.ofNullable(candidat.getPrenom()).orElse("");
            return (nom + " " + prenom).trim();
        }

        public String getConcoursLabel() {
            Candidat candidat = dossier.getCandidat();
            if (candidat != null && candidat.getConcours() != null) {
                return candidat.getConcours().getDisplayInfo();
            }
            return "-";
        }

        public String getCentreLabel() {
            Candidat candidat = dossier.getCandidat();
            if (candidat != null && candidat.getCentreExamen() != null) {
                return candidat.getCentreExamen().getLibelle();
            }
            return "-";
        }

        public String getDocumentLabel() {
            DocumentConcours doc = dossier.getDocumentConcours();
            return doc != null ? doc.getLibelle() : "-";
        }

        public String getFileName() {
            String path = dossier.getRemarqueFacultatif();
            if (path == null) return "-";
            int idx = path.lastIndexOf(File.separator);
            return idx >= 0 ? path.substring(idx + 1) : path;
        }

        private String buildSortableName() {
            return Optional.ofNullable(getCandidatLabel()).orElse("").toLowerCase();
        }

        public String getEtatLabel() {
            return getEtat().getLabel();
        }

        public String getEtatSclass() {
            return getEtat().getChipSclass();
        }

        public boolean isValide() {
            return getEtat() == DocumentValidationEtat.VALIDE;
        }

        public boolean isRejete() {
            return getEtat() == DocumentValidationEtat.REJETE;
        }

        public boolean isEnCours() {
            return getEtat() == DocumentValidationEtat.EN_COURS;
        }
    }

}
