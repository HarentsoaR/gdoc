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

    private List<SuiviRow> dossiers = new ArrayList<>();
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

        dossiers = raw.stream()
                .map(SuiviRow::new)
                .sorted(Comparator.comparing(SuiviRow::getCandidatLabel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList());
        summaryStats = SummaryStats.from(raw);
    }

    @Command
    @NotifyChange({"dossiers", "summaryStats"})
    public void applyFilters() {
        loadDossiers();
    }

    @Command
    @NotifyChange({"dossiers", "summaryStats", "selectedConcours", "selectedCentre", "selectedDocumentType", "searchNomCandidat", "selectedEtatFilter"})
    public void clearFilters() {
        selectedConcours = null;
        selectedCentre = null;
        selectedDocumentType = null;
        searchNomCandidat = "";
        selectedEtatFilter = etatFilterOptions.get(0);
        loadDossiers();
    }

    @Command
    public void download(@BindingParam("item") SuiviRow item) {
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

    public List<SuiviRow> getDossiers() {
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

    public static class SuiviRow {
        private final ListeDossierConcoursCandidat dossier;
        private final DocumentValidationEtat etat;

        public SuiviRow(ListeDossierConcoursCandidat dossier) {
            this.dossier = dossier;
            this.etat = DocumentValidationEtat.fromCode(dossier.getEtatDocument());
        }

        public String getCandidatLabel() {
            Candidat candidat = dossier.getCandidat();
            if (candidat == null) return "-";
            String nom = Optional.ofNullable(candidat.getNom()).orElse("");
            String prenom = Optional.ofNullable(candidat.getPrenom()).orElse("");
            return (nom + " " + prenom).trim();
        }

        public String getDocumentLabel() {
            DocumentConcours doc = dossier.getDocumentConcours();
            return doc != null ? doc.getLibelle() : "-";
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

        public DocumentValidationEtat getEtat() {
            return etat;
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

        public String getPath() {
            return dossier.getRemarqueFacultatif();
        }

        public String getFileName() {
            String path = dossier.getRemarqueFacultatif();
            if (path == null) return "-";
            int idx = path.lastIndexOf(File.separator);
            return idx >= 0 ? path.substring(idx + 1) : path;
        }
    }

    public static class SummaryStats {
        private long total;
        private long valides;
        private long enCours;
        private long rejetes;

        public static SummaryStats from(List<ListeDossierConcoursCandidat> docs) {
            SummaryStats stats = new SummaryStats();
            if (docs == null) return stats;
            stats.total = docs.size();
            stats.valides = docs.stream().filter(d -> Objects.equals(d.getEtatDocument(), DocumentValidationEtat.VALIDE.getCode())).count();
            stats.enCours = docs.stream().filter(d -> Objects.equals(d.getEtatDocument(), DocumentValidationEtat.EN_COURS.getCode())).count();
            stats.rejetes = docs.stream().filter(d -> Objects.equals(d.getEtatDocument(), DocumentValidationEtat.REJETE.getCode())).count();
            return stats;
        }

        public long getTotal() { return total; }
        public long getValides() { return valides; }
        public long getEnCours() { return enCours; }
        public long getRejetes() { return rejetes; }
    }
}
