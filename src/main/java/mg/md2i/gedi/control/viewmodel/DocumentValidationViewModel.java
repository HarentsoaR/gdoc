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
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentValidationViewModel {

    private List<ValidationRow> dossiers = new ArrayList<>();
    private List<DossierSummary> dossierSummaries = new ArrayList<>();
    private Set<DossierSummary> selectedSummaryRows = new LinkedHashSet<>();
    private List<Concours> concoursList = new ArrayList<>();
    private List<CentreExamen> centreList = new ArrayList<>();
    private List<DocumentConcours> documentTypes = new ArrayList<>();

    private Concours selectedConcours;
    private CentreExamen selectedCentre;
    private DocumentConcours selectedDocumentType;
    private DocumentEtatFilterOption selectedEtatFilter;
    private List<DocumentEtatFilterOption> etatFilterOptions = new ArrayList<>();
    private String searchNomCandidat = "";
    private Set<ValidationRow> selectedRows = new LinkedHashSet<>();
    private Integer focusedCandidatId;
    private String focusedCandidatName;
    private boolean detailMode = false;
    private boolean filtersVisible = false;

    @Init
    public void init() {
        concoursList = ConcoursGestion.findAll();
        centreList = CentreExamenGestion.findAll();
        documentTypes = DocumentConcoursGestion.findAll();
        restoreDetailContext();
        buildEtatOptions();
        selectedEtatFilter = etatFilterOptions.stream()
                .filter(o -> o.getEtat() == DocumentValidationEtat.EN_COURS)
                .findFirst()
                .orElse(etatFilterOptions.get(0));
        loadDossiers();
    }

    private void restoreDetailContext() {
        Object candidateAttr = Executions.getCurrent().getAttribute("validationCandidateId");
        if (candidateAttr instanceof Number) {
            focusedCandidatId = ((Number) candidateAttr).intValue();
            detailMode = true;
        }
        Object candidateNameAttr = Executions.getCurrent().getAttribute("validationCandidateName");
        if (candidateNameAttr instanceof String) {
            focusedCandidatName = (String) candidateNameAttr;
        }
        Executions.getCurrent().removeAttribute("validationCandidateId");
        Executions.getCurrent().removeAttribute("validationCandidateName");
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

        if (focusedCandidatId != null) {
            raw = raw.stream()
                    .filter(doc -> doc.getCandidat() != null
                            && Objects.equals(doc.getCandidat().getCandidatId(), focusedCandidatId))
                    .collect(Collectors.toList());
        }

        Integer code = (selectedEtatFilter != null && selectedEtatFilter.getEtat() != null)
                ? selectedEtatFilter.getEtat().getCode()
                : null;

        if (code != null) {
            raw = raw.stream()
                    .filter(doc -> Objects.equals(doc.getEtatDocument(), code))
                    .collect(Collectors.toList());
        }

        Set<Integer> previousSelection = selectedRows.stream()
                .map(ValidationRow::getDossierId)
                .collect(Collectors.toSet());

        dossiers = raw.stream()
                .map(ValidationRow::new)
                .sorted(Comparator.comparing(ValidationRow::buildSortableName))
                .collect(Collectors.toList());
        buildDossierSummaries();

        if (previousSelection.isEmpty()) {
            selectedRows.clear();
        } else {
            selectedRows = dossiers.stream()
                    .filter(row -> previousSelection.contains(row.getDossierId()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @Command
    @NotifyChange({"dossiers", "selectedRows", "dossierSummaries", "selectedSummaryRows"})
    public void applyFilters() {
        loadDossiers();
    }

    @Command
    @NotifyChange({"dossiers", "selectedRows", "dossierSummaries", "selectedSummaryRows", "selectedConcours", "selectedCentre", "selectedDocumentType", "searchNomCandidat", "selectedEtatFilter"})
    public void clearFilters() {
        selectedConcours = null;
        selectedCentre = null;
        selectedDocumentType = null;
        searchNomCandidat = "";
        selectedEtatFilter = etatFilterOptions.stream()
                .filter(o -> o.getEtat() == DocumentValidationEtat.EN_COURS)
                .findFirst()
                .orElse(etatFilterOptions.get(0));
        selectedRows.clear();
        selectedSummaryRows.clear();
        loadDossiers();
    }
    
    // FIX: Removed the updateSearchNom command as it's no longer necessary and caused performance issues.

    @Command
    @NotifyChange({"dossiers", "selectedRows", "dossierSummaries", "selectedSummaryRows"})
    public void changeEtat(@BindingParam("item") ValidationRow item,
                           @BindingParam("etat") int etatCode) {
        if (item == null) return;

        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(etatCode);
        if (etat == DocumentValidationEtat.REJETE && (item.getCommentaire() == null || item.getCommentaire().trim().isEmpty())) {
            Clients.showNotification("Veuillez saisir une remarque pour rejeter le document.", "warning", null, "top_center", 2500);
            return;
        }

        updateDossierEtat(item.getDossier(), etat, item.getCommentaire());

        Clients.showNotification("Statut mis à jour : " + etat.getLabel(), "info", null, "top_center", 2000);
        selectedRows.remove(item);
        loadDossiers();
    }

    @Command
    @NotifyChange({"dossiers", "selectedRows"})
    public void selectAllRows() {
        selectedRows = dossiers.stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Command
    @NotifyChange("selectedRows")
    public void clearSelection() {
        selectedRows.clear();
    }

    @Command
    @NotifyChange("selectedSummaryRows")
    public void selectAllSummaries() {
        selectedSummaryRows = dossierSummaries.stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Command
    @NotifyChange("selectedSummaryRows")
    public void toggleSummarySelection() {
        if (selectedSummaryRows.size() == dossierSummaries.size() && !dossierSummaries.isEmpty()) {
            selectedSummaryRows.clear();
        } else {
            selectAllSummaries();
        }
    }

    @Command
    public void bulkChangeEtat(@BindingParam("etat") int etatCode) {
        if (selectedRows == null || selectedRows.isEmpty()) {
            Clients.showNotification("Sélectionnez au moins un dossier.", "warning", null, "top_center", 2000);
            return;
        }

        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(etatCode);
        int selectionSize = selectedRows.size();
        String actionLabel = etat == DocumentValidationEtat.VALIDE ? "valider"
                : etat == DocumentValidationEtat.REJETE ? "rejeter"
                : "remettre en revue";

        Messagebox.show("Confirmer " + actionLabel + " " + selectionSize + " dossier(s) ?",
                "Confirmation", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        handleBulkChange(etat);
                    }
                });
    }

    private void handleBulkChange(DocumentValidationEtat etat) {
        List<String> missingRemarks = new ArrayList<>();
        int processed = 0;

        for (ValidationRow row : new ArrayList<>(selectedRows)) {
            if (etat == DocumentValidationEtat.REJETE && (row.getCommentaire() == null || row.getCommentaire().trim().isEmpty())) {
                missingRemarks.add(row.getCandidatLabel());
                continue;
            }
            updateDossierEtat(row.getDossier(), etat, row.getCommentaire());
            processed++;
        }

        selectedRows.clear();
        selectedSummaryRows.clear();
        loadDossiers();
        BindUtils.postNotifyChange(null, null, this, "dossiers");
        BindUtils.postNotifyChange(null, null, this, "selectedRows");
        BindUtils.postNotifyChange(null, null, this, "selectedSummaryRows");
        BindUtils.postNotifyChange(null, null, this, "dossierSummaries");
        BindUtils.postNotifyChange(null, null, this, "dossierSummaries");

        if (processed > 0) {
            Clients.showNotification(processed + " dossier(s) mis à jour", "info", null, "top_center", 2000);
        }
        if (!missingRemarks.isEmpty()) {
            Clients.showNotification("Remarque requise pour : " + String.join(", ", missingRemarks), "warning", null, "top_center", 3500);
        }
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

    private void updateDossierEtat(ListeDossierConcoursCandidat dossier, DocumentValidationEtat etat, String commentaire) {
        if (commentaire == null || commentaire.trim().isEmpty()) {
            commentaire = defaultRemark(etat);
        }
        dossier.setEtatDocument(etat.getCode());
        dossier.setRemarque(commentaire.trim());
        ListeDossierConcoursCandidatGestion.saveAndIndex(dossier);
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

    // getters and setters...
    // ... (rest of the class is unchanged)

    public List<ValidationRow> getDossiers() {
        return dossiers;
    }

    public List<DossierSummary> getDossierSummaries() {
        return dossierSummaries;
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

    public Set<ValidationRow> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(Set<ValidationRow> selectedRows) {
        this.selectedRows = (selectedRows != null)
                ? new LinkedHashSet<>(selectedRows)
                : new LinkedHashSet<>();
    }

    public boolean isFiltersVisible() {
        return filtersVisible;
    }

    @Command
    @NotifyChange("filtersVisible")
    public void toggleFilters() {
        filtersVisible = !filtersVisible;
    }

    public String getDocumentToggleLabel() {
        return isAllDocumentsSelected() ? "Tout désélectionner" : "Tout sélectionner";
    }

    public boolean isAllDocumentsSelected() {
        return dossiers != null && !dossiers.isEmpty() && selectedRows.size() == dossiers.size();
    }

    @Command
    @NotifyChange("selectedRows")
    public void toggleDocumentSelection() {
        if (isAllDocumentsSelected()) {
            selectedRows.clear();
        } else {
            selectedRows = dossiers.stream()
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public String getFocusedCandidatName() {
        return focusedCandidatName;
    }

    public String getDetailTitle() {
        if (detailMode) {
            return "Documents de " + (focusedCandidatName != null ? focusedCandidatName : "candidat");
        }
        return "Validation des documents";
    }

    public String getDetailSubtitle() {
        return detailMode
                ? "Contrôlez et validez les documents de ce dossier"
                : "Contrôlez et validez les documents de concours";
    }

    public Set<DossierSummary> getSelectedSummaryRows() {
        return selectedSummaryRows;
    }

    public void setSelectedSummaryRows(Set<DossierSummary> selectedSummaryRows) {
        this.selectedSummaryRows = (selectedSummaryRows != null)
                ? new LinkedHashSet<>(selectedSummaryRows)
                : new LinkedHashSet<>();
    }

    public String getDossierCountLabel() {
        int count = dossierSummaries != null ? dossierSummaries.size() : 0;
        return count + " dossier(s)";
    }

    public String getSummarySelectionLabel() {
        int count = selectedSummaryRows != null ? selectedSummaryRows.size() : 0;
        return count + " sélection(s)";
    }

    public String getDocumentSelectionLabel() {
        int count = selectedRows != null ? selectedRows.size() : 0;
        return count + " sélection(s)";
    }

    public String getSummaryToggleLabel() {
        if (isAllSummariesSelected()) {
            return "Tout désélectionner";
        }
        return "Tout sélectionner";
    }

    public boolean isAllSummariesSelected() {
        return dossierSummaries != null
                && !dossierSummaries.isEmpty()
                && selectedSummaryRows.size() == dossierSummaries.size();
    }

    private void buildDossierSummaries() {
        Map<Integer, DossierSummary> grouped = new LinkedHashMap<>();
        for (ValidationRow row : dossiers) {
            Integer key = row.getCandidateKey();
            grouped.computeIfAbsent(key, k -> new DossierSummary(row)).add(row);
        }
        Set<Integer> previousSelection = selectedSummaryRows.stream()
                .map(DossierSummary::getCandidatId)
                .collect(Collectors.toSet());
        dossierSummaries = new ArrayList<>(grouped.values());
        if (previousSelection.isEmpty()) {
            selectedSummaryRows.clear();
        } else {
            selectedSummaryRows = dossierSummaries.stream()
                    .filter(summary -> previousSelection.contains(summary.getCandidatId()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @Command
    public void openDossier(@BindingParam("summary") DossierSummary summary) {
        if (summary == null || summary.getCandidatId() == null) {
            Clients.showNotification("Dossier invalide", "warning", null, "top_center", 2000);
            return;
        }
        Executions.getCurrent().setAttribute("validationCandidateId", summary.getCandidatId());
        Executions.getCurrent().setAttribute("validationCandidateName", summary.getCandidateLabel());
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal", new HashMap<String, Object>() {{
            put("view", "/documents/views/concours/validation-documents.zul");
            put("section", "Concours");
            put("page", "Validation dossiers \u203A Documents du candidat");
            put("label", "Documents du candidat");
        }});
    }

    @Command
    public void backToDossiers() {
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal", new HashMap<String, Object>() {{
            put("view", "/documents/views/concours/validation-dossiers.zul");
            put("section", "Concours");
            put("page", "Validation dossiers");
            put("label", "Validation des dossiers");
        }});
    }

    @Command
    @NotifyChange({"dossiers", "selectedRows", "dossierSummaries", "selectedSummaryRows"})
    public void updateSummaryEtat(@BindingParam("summary") DossierSummary summary,
                                  @BindingParam("etat") int etatCode) {
        if (summary == null) return;
        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(etatCode);
        if (etat == DocumentValidationEtat.VALIDE && summary.hasBlockingDocuments()) {
            Clients.showNotification("Impossible de valider ce dossier : certains documents sont en attente ou manquants.",
                    "warning", null, "top_center", 3000);
            return;
        }
        if (etat == DocumentValidationEtat.REJETE && (summary.getRemark() == null || summary.getRemark().trim().isEmpty())) {
            Clients.showNotification("Ajoutez une remarque avant de rejeter le dossier.", "warning", null, "top_center", 2500);
            return;
        }
        for (ValidationRow row : dossiers) {
            if (summary.matches(row)) {
                row.setCommentaire(summary.getRemark());
                updateDossierEtat(row.getDossier(), etat, summary.getRemark());
            }
        }
        loadDossiers();
        Clients.showNotification("Dossier mis à jour : " + etat.getLabel(), "info", null, "top_center", 2000);
    }

    @Command
    @NotifyChange({"dossiers", "selectedRows", "dossierSummaries", "selectedSummaryRows"})
    public void bulkUpdateSummaries(@BindingParam("etat") int etatCode) {
        if (selectedSummaryRows == null || selectedSummaryRows.isEmpty()) {
            Clients.showNotification("Sélectionnez au moins un dossier.", "warning", null, "top_center", 2000);
            return;
        }
        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(etatCode);
        List<DossierSummary> blocked = selectedSummaryRows.stream()
                .filter(DossierSummary::hasBlockingDocuments)
                .collect(Collectors.toList());
        if (etat == DocumentValidationEtat.VALIDE && !blocked.isEmpty()) {
            Clients.showNotification("Certains dossiers contiennent encore des documents en attente ou manquants.",
                    "warning", null, "top_center", 3000);
            return;
        }
        List<DossierSummary> missingRemarks = selectedSummaryRows.stream()
                .filter(summary -> etat == DocumentValidationEtat.REJETE &&
                        (summary.getRemark() == null || summary.getRemark().trim().isEmpty()))
                .collect(Collectors.toList());
        if (!missingRemarks.isEmpty()) {
            Clients.showNotification("Ajoutez une remarque avant de rejeter les dossiers sélectionnés.",
                    "warning", null, "top_center", 3000);
            return;
        }
        for (DossierSummary summary : selectedSummaryRows) {
            for (ValidationRow row : dossiers) {
                if (summary.matches(row)) {
                    row.setCommentaire(summary.getRemark());
                    updateDossierEtat(row.getDossier(), etat, summary.getRemark());
                }
            }
        }
        selectedSummaryRows.clear();
        loadDossiers();
        Clients.showNotification("Dossiers mis à jour : " + etat.getLabel(), "info", null, "top_center", 2000);
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

        public String getRegistrationLabel() {
            Candidat candidat = dossier.getCandidat();
            if (candidat != null && candidat.getNumInscription() != null) {
                return String.valueOf(candidat.getNumInscription());
            }
            return getCandidatLabel();
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

        public Integer getDossierId() {
            return dossier.getListeDossierConcoursCandidatId();
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

        public Integer getCandidateKey() {
            if (dossier.getCandidat() != null && dossier.getCandidat().getCandidatId() != null) {
                return dossier.getCandidat().getCandidatId();
            }
            return dossier.getListeDossierConcoursCandidatId();
        }

        public boolean hasAttachment() {
            String path = dossier.getRemarqueFacultatif();
            return path != null && !path.trim().isEmpty();
        }

        public boolean getHasAttachment() {
            return hasAttachment();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ValidationRow)) return false;
            ValidationRow that = (ValidationRow) o;
            return Objects.equals(getDossierId(), that.getDossierId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getDossierId());
        }
    }

    public static class DossierSummary {
        private final Integer candidatId;
        private final String candidateLabel;
        private final String registrationLabel;
        private final String concoursLabel;
        private final String centreLabel;
        private int totalDocs = 0;
        private int validatedDocs = 0;
        private int rejectedDocs = 0;
        private int pendingDocs = 0;
        private int missingPieces = 0;
        private String remark;

        public DossierSummary(ValidationRow row) {
            this.candidatId = row.getCandidateKey();
            this.candidateLabel = row.getCandidatLabel();
            this.registrationLabel = row.getRegistrationLabel();
            this.concoursLabel = row.getConcoursLabel();
            this.centreLabel = row.getCentreLabel();
            this.remark = row.getCommentaire();
        }

        public void add(ValidationRow row) {
            totalDocs++;
            if (!row.hasAttachment()) {
                missingPieces++;
            } else {
                DocumentValidationEtat etat = row.getEtat();
                if (etat == DocumentValidationEtat.VALIDE) validatedDocs++;
                else if (etat == DocumentValidationEtat.REJETE) rejectedDocs++;
                else pendingDocs++;
            }

            if (remark == null || remark.trim().isEmpty()) {
                remark = row.getCommentaire();
            }
        }

        public Integer getCandidatId() {
            return candidatId;
        }

        public String getCandidateLabel() {
            return candidateLabel != null ? candidateLabel : "-";
        }

        public String getRegistrationLabel() {
            return registrationLabel != null ? registrationLabel : "-";
        }

        public String getConcoursLabel() {
            return concoursLabel != null ? concoursLabel : "-";
        }

        public String getCentreLabel() {
            return centreLabel != null ? centreLabel : "-";
        }

        public int getTotalDocs() {
            return totalDocs;
        }

        public int getValidatedDocs() {
            return validatedDocs;
        }

        public int getRejectedDocs() {
            return rejectedDocs;
        }

        public int getPendingDocs() {
            return pendingDocs;
        }

        public int getMissingPieces() {
            return missingPieces;
        }

        public String getProgressLabel() {
            return validatedDocs + "/" + totalDocs + " validés";
        }

        public String getStatusLabel() {
            if (missingPieces > 0) return "Pièces manquantes";
            if (rejectedDocs > 0) return "À corriger";
            if (pendingDocs > 0) return "En cours";
            if (validatedDocs == totalDocs && totalDocs > 0) return "Complet";
            return "Non défini";
        }

        public String getStatusSclass() {
            if (missingPieces > 0 || rejectedDocs > 0) return "status-label status-danger";
            if (pendingDocs > 0) return "status-label status-warning";
            if (validatedDocs == totalDocs && totalDocs > 0) return "status-label status-success";
            return "status-label";
        }

        public double getProgressRatio() {
            if (totalDocs == 0) return 0;
            return (double) validatedDocs / totalDocs;
        }

        public int getProgressPercent() {
            return (int) Math.round(getProgressRatio() * 100);
        }

        public String getProgressStyle() {
            return "width:" + getProgressPercent() + "%";
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public boolean matches(ValidationRow row) {
            if (row.getDossier().getCandidat() != null) {
                return Objects.equals(row.getDossier().getCandidat().getCandidatId(), candidatId);
            }
            return Objects.equals(row.getDossierId(), candidatId);
        }

        public boolean hasBlockingDocuments() {
            return pendingDocs > 0 || rejectedDocs > 0 || missingPieces > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DossierSummary)) return false;
            DossierSummary that = (DossierSummary) o;
            return Objects.equals(candidatId, that.candidatId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(candidatId);
        }
    }
}
