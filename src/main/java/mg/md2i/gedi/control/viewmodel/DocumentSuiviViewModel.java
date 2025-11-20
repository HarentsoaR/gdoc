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
import java.util.*;
import java.util.stream.Collectors;

public class DocumentSuiviViewModel {

    private List<DossierRow> dossiers = new ArrayList<>();
    private SummaryStats summaryStats = new SummaryStats();

    private List<Concours> concoursList = new ArrayList<>();
    private List<CentreExamen> centreList = new ArrayList<>();
    private List<DocumentConcours> documentTypes = new ArrayList<>();
    private List<DocumentEtatFilterOption> etatFilterOptions = new ArrayList<>();

    private Concours selectedConcours;
    private CentreExamen selectedCentre;
    private DocumentConcours selectedDocumentType;
    private DocumentEtatFilterOption selectedEtatFilter;
    private String searchNomCandidat = "";
    private boolean filtersVisible = false;
    private DossierRow selectedDossier;
    private boolean detailsModalVisible = false;

    @Init
    public void init() {
        concoursList = ConcoursGestion.findAll();
        centreList = CentreExamenGestion.findAll();
        documentTypes = DocumentConcoursGestion.findAll();
        buildEtatOptions();
        selectedEtatFilter = etatFilterOptions.get(0); // Tous par défaut
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

        Map<Integer, List<ListeDossierConcoursCandidat>> grouped = raw.stream()
                .filter(item -> item.getCandidatId() != null)
                .collect(Collectors.groupingBy(ListeDossierConcoursCandidat::getCandidatId));

        dossiers = grouped.values().stream()
                .map(entries -> new DossierRow(entries, documentTypes))
                .sorted(Comparator.comparing(DossierRow::getCandidatLabel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList());

        summaryStats = SummaryStats.fromDossiers(dossiers);
        selectedDossier = null;
        detailsModalVisible = false;
    }

    @Command
    @NotifyChange({"dossiers", "summaryStats", "selectedDossier", "detailsModalVisible"})
    public void applyFilters() {
        loadDossiers();
    }

    @Command
    @NotifyChange({"dossiers", "summaryStats", "selectedConcours", "selectedCentre", "selectedDocumentType", "searchNomCandidat", "selectedEtatFilter", "selectedDossier", "detailsModalVisible"})
    public void clearFilters() {
        selectedConcours = null;
        selectedCentre = null;
        selectedDocumentType = null;
        searchNomCandidat = "";
        selectedEtatFilter = etatFilterOptions.get(0);
        loadDossiers();
    }

    @Command
    @NotifyChange({"dossiers", "summaryStats", "searchNomCandidat", "selectedDossier", "detailsModalVisible"})
    public void updateSearchNom(@BindingParam("keyword") String keyword) {
        searchNomCandidat = keyword != null ? keyword.trim() : "";
        loadDossiers();
    }

    @Command
    @NotifyChange({"selectedDossier", "detailsModalVisible"})
    public void openDossierDetails(@BindingParam("row") DossierRow row) {
        selectedDossier = row;
        detailsModalVisible = row != null;
    }

    @Command
    @NotifyChange("detailsModalVisible")
    public void closeDossierDetails() {
        detailsModalVisible = false;
    }

    @Command
    public void downloadDocument(@BindingParam("item") DocumentItem item) {
        if (item == null || item.getPath() == null) return;
        try {
            File file = new File(item.getPath());
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

    public List<DossierRow> getDossiers() {
        return dossiers;
    }

    public SummaryStats getSummaryStats() {
        return summaryStats;
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

    public List<DocumentEtatFilterOption> getEtatFilterOptions() {
        return etatFilterOptions;
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

    public String getSearchNomCandidat() {
        return searchNomCandidat;
    }

    public void setSearchNomCandidat(String searchNomCandidat) {
        this.searchNomCandidat = searchNomCandidat;
    }

    public boolean isFiltersVisible() {
        return filtersVisible;
    }

    @Command
    @NotifyChange("filtersVisible")
    public void toggleFilters() {
        filtersVisible = !filtersVisible;
    }

    public DossierRow getSelectedDossier() {
        return selectedDossier;
    }

    public boolean isDetailsModalVisible() {
        return detailsModalVisible;
    }

    public static class DossierRow {
        private final Integer candidatId;
        private final Candidat candidat;
        private final Concours concours;
        private final CentreExamen centre;
        private final List<DocumentItem> documents;
        private final long totalDocuments;
        private final long valides;
        private final long enCours;
        private final long rejetes;
        private final long sansPiece;
        private final long missingRequired;
        private final List<String> missingRequiredLabels;
        private final long expectedTotal;
        private final DocumentValidationEtat globalEtat;

        public DossierRow(List<ListeDossierConcoursCandidat> entries, List<DocumentConcours> requiredDocuments) {
            ListeDossierConcoursCandidat first = entries.get(0);
            this.candidatId = first.getCandidatId();
            this.candidat = first.getCandidat();
            this.concours = candidat != null ? candidat.getConcours() : null;
            this.centre = candidat != null ? candidat.getCentreExamen() : null;
            this.documents = entries.stream()
                    .map(DocumentItem::new)
                    .collect(Collectors.toList());
            this.totalDocuments = documents.size();
            this.valides = documents.stream().filter(DocumentItem::isValide).count();
            this.enCours = documents.stream().filter(DocumentItem::isEnCours).count();
            this.rejetes = documents.stream().filter(DocumentItem::isRejete).count();
            this.sansPiece = documents.stream().filter(doc -> !doc.hasAttachment()).count();

            Set<Integer> providedDocIds = entries.stream()
                    .map(ListeDossierConcoursCandidat::getDocumentConcoursId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            List<DocumentConcours> requiredList = Optional.ofNullable(requiredDocuments).orElse(Collections.emptyList());
            this.missingRequiredLabels = requiredList.stream()
                    .filter(doc -> doc.getDocumentConcoursId() != null && !providedDocIds.contains(doc.getDocumentConcoursId()))
                    .map(DocumentConcours::getLibelle)
                    .collect(Collectors.toList());
            this.missingRequired = missingRequiredLabels.size();
            this.expectedTotal = totalDocuments + missingRequired;
            this.globalEtat = resolveGlobalEtat();
        }

        private DocumentValidationEtat resolveGlobalEtat() {
            if (rejetes > 0) {
                return DocumentValidationEtat.REJETE;
            }
            if (valideEtComplets()) {
                return DocumentValidationEtat.VALIDE;
            }
            if (missingRequired > 0 || sansPiece > 0 || enCours > 0) {
                return DocumentValidationEtat.EN_COURS;
            }
            return DocumentValidationEtat.EN_COURS;
        }

        private boolean valideEtComplets() {
            return missingRequired == 0 && sansPiece == 0 && enCours == 0 && rejetes == 0 && valides == totalDocuments;
        }

        public Integer getCandidatId() {
            return candidatId;
        }

        public String getReference() {
            String num = Optional.ofNullable(candidat)
                    .map(Candidat::getNumInscription)
                    .map(val -> String.format("%05d", val))
                    .orElse(Optional.ofNullable(candidat).map(Candidat::getNumeroEnregistrement).orElse("N/A"));
            String concoursInfo = Optional.ofNullable(concours).map(Concours::getDisplayInfo).orElse("-");
            return "DOS-" + num + " | " + concoursInfo;
        }

        public String getCandidatLabel() {
            if (candidat == null) return "-";
            String nom = Optional.ofNullable(candidat.getNom()).orElse("");
            String prenom = Optional.ofNullable(candidat.getPrenom()).orElse("");
            return (nom + " " + prenom).trim();
        }

        public String getConcoursLabel() {
            return Optional.ofNullable(concours).map(Concours::getDisplayInfo).orElse("-");
        }

        public String getCentreLabel() {
            return Optional.ofNullable(centre).map(CentreExamen::getLibelle).orElse("-");
        }

        public String getGlobalEtatLabel() {
            return globalEtat.getLabel();
        }

        public String getGlobalEtatSclass() {
            return "status-label " + globalEtat.getChipSclass();
        }

        public String getDocumentsResume() {
            return String.format(Locale.FRENCH, "%d/%d pièces reçues • %d validés • %d en cours • %d rejetés",
                    totalDocuments, expectedTotal, valides, enCours, rejetes);
        }

        public boolean isComplet() {
            return sansPiece == 0 && missingRequired == 0;
        }

        public long getSansPiece() {
            return sansPiece;
        }

        public long getMissingRequired() {
            return missingRequired;
        }

        public long getTotalDocuments() { return totalDocuments; }
        public long getValides() { return valides; }
        public long getRejetes() { return rejetes; }
        public long getEnCours() { return enCours; }

        public boolean isFullyValidated() {
            return valideEtComplets();
        }

        public List<DocumentItem> getDocuments() {
            return documents;
        }

        public String getMissingRequiredLabel() {
            if (missingRequired <= 0) {
                return "";
            }
            return missingRequired + (missingRequired > 1 ? " documents requis manquants" : " document requis manquant");
        }

        public String getMissingRequiredDetails() {
            if (missingRequiredLabels.isEmpty()) {
                return "";
            }
            return String.join(", ", missingRequiredLabels);
        }

        public String getPiecesStateLabel() {
            if (missingRequired > 0 && sansPiece > 0) {
                return String.format(Locale.FRENCH, "%d doc. requis manquants • %d pièces sans fichier", missingRequired, sansPiece);
            }
            if (missingRequired > 0) {
                return getMissingRequiredLabel();
            }
            if (sansPiece > 0) {
                return sansPiece + (sansPiece > 1 ? " pièces sans fichier" : " pièce sans fichier");
            }
            return "Complet";
        }

        public String getPiecesStateSclass() {
            if (missingRequired > 0) {
                return "status-label status-danger";
            }
            if (sansPiece > 0) {
                return "status-label status-warning";
            }
            return "status-label status-success";
        }
    }

    public static class DocumentItem {
        private final ListeDossierConcoursCandidat dossier;
        private final DocumentValidationEtat etat;

        public DocumentItem(ListeDossierConcoursCandidat dossier) {
            this.dossier = dossier;
            this.etat = DocumentValidationEtat.fromCode(dossier.getEtatDocument());
        }

        public String getLabel() {
            DocumentConcours doc = dossier.getDocumentConcours();
            return doc != null ? doc.getLibelle() : "-";
        }

        public String getEtatLabel() {
            return etat.getLabel();
        }

        public String getEtatSclass() {
            return etat.getChipSclass();
        }

        public String getRemarque() {
            return dossier.getRemarque();
        }

        public Integer getVersion() {
            return dossier.getVersion();
        }

        public Integer getDocumentTypeId() {
            return dossier.getDocumentConcoursId();
        }

        public boolean hasAttachment() {
            String path = dossier.getRemarqueFacultatif();
            return path != null && !path.trim().isEmpty();
        }

        public boolean getHasAttachment() {
            return hasAttachment();
        }

        public String getPath() {
            return dossier.getRemarqueFacultatif();
        }

        public String getFileName() {
            String path = dossier.getRemarqueFacultatif();
            if (path == null) return "-";
            int idx = path.lastIndexOf(File.separator);
            return idx >= 0 ? path.substring(idx + 1) : path;
        }

        public boolean isValide() { return etat == DocumentValidationEtat.VALIDE; }
        public boolean isRejete() { return etat == DocumentValidationEtat.REJETE; }
        public boolean isEnCours() { return etat == DocumentValidationEtat.EN_COURS; }
    }

    public static class SummaryStats {
        private long total;
        private long valides;
        private long enCours;
        private long rejetes;

        public static SummaryStats fromDossiers(List<DossierRow> dossiers) {
            SummaryStats stats = new SummaryStats();
            if (dossiers == null) {
                return stats;
            }
            stats.total = dossiers.size();
            stats.valides = dossiers.stream().filter(DossierRow::isFullyValidated).count();
            stats.rejetes = dossiers.stream().filter(d -> d.globalEtat == DocumentValidationEtat.REJETE).count();
            stats.enCours = Math.max(0, stats.total - stats.valides - stats.rejetes);
            return stats;
        }

        public long getTotal() { return total; }
        public long getValides() { return valides; }
        public long getEnCours() { return enCours; }
        public long getRejetes() { return rejetes; }

        public String getProgressLabel() {
            if (total == 0) return "0%";
            int percent = (int)Math.round((double)valides / total * 100);
            return percent + "% validés";
        }

        public int getProgressPercent() {
            if (total == 0) return 0;
            return (int)Math.round((double)valides / total * 100);
        }

        public String getProgressStyle() {
            return "width:" + getProgressPercent() + "%";
        }
    }
}
