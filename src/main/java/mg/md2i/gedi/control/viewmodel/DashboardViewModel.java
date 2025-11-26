package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.dto.dashboard.ConcoursVolumeStat;
import mg.md2i.gedi.dto.dashboard.StorageStat;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.DashboardStatsGestion;
import mg.md2i.gedi.gestionmetier.DocumentGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;

import java.io.File;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clean, Lombok-free version so ZK always finds getters.
 */
public class DashboardViewModel {

    private static final Logger log = LoggerFactory.getLogger(DashboardViewModel.class);
    private static final long STORAGE_CAPACITY_BYTES = 20L * 1024 * 1024 * 1024;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.FRENCH);

    private long totalDocuments;
    private long totalDossiers;
    private long dossiersValides;
    private long dossiersEnCours;
    private long dossiersRejetes;
    private int storageUsagePercent;
    private String storageUsageLabel = "0 Mo";
    private String lastRefreshLabel = "-";

    private List<RecentDocument> recentDocuments = new ArrayList<>();
    private List<ConcoursVolumeStat> concoursStats = new ArrayList<>();
    private List<DossierStatusStat> dossierStatusStats = new ArrayList<>();
    private List<StorageStat> storageStats = new ArrayList<>();

    // ---------------- Getters ----------------
    public long getTotalDocuments() { return totalDocuments; }
    public long getTotalDossiers() { return totalDossiers; }
    public long getDossiersValides() { return dossiersValides; }
    public long getDossiersEnCours() { return dossiersEnCours; }
    public long getDossiersRejetes() { return dossiersRejetes; }
    public int getStorageUsagePercent() { return storageUsagePercent; }
    public String getStorageUsageLabel() { return storageUsageLabel; }
    public String getLastRefreshLabel() { return lastRefreshLabel; }
    public List<RecentDocument> getRecentDocuments() { return recentDocuments; }
    public List<ConcoursVolumeStat> getConcoursStats() { return concoursStats; }
    public List<DossierStatusStat> getDossierStatusStats() { return dossierStatusStats; }
    public List<StorageStat> getStorageStats() { return storageStats; }

    // ---------------- Lifecycle ----------------
    @Init
    public void init() {
        loadDashboard();
    }

    // ---------------- Commands ----------------
    @Command
    @NotifyChange({"totalDocuments", "totalDossiers", "dossiersValides", "dossiersEnCours", "dossiersRejetes",
            "storageUsagePercent", "storageUsageLabel", "lastRefreshLabel", "recentDocuments", "concoursStats",
            "dossierStatusStats", "storageStats"})
    public void refreshDashboard() {
        loadDashboard();
    }

    @Command
    public void downloadFile(@BindingParam("doc") Object fileObj) {
        String path = null;
        String label = "fichier";
        if (fileObj instanceof RecentDocument) {
            RecentDocument rd = (RecentDocument) fileObj;
            path = rd.getPath();
            label = rd.getTitle();
        } else if (fileObj instanceof Document) {
            Document d = (Document) fileObj;
            path = d.getPath();
            label = d.getTitre();
        }

        if (path == null || path.trim().isEmpty()) {
            Clients.showNotification("Aucun fichier à télécharger", "warning", null, "top_center", 2000);
            return;
        }
        try {
            File f = new File(path);
            if (!f.exists()) {
                Clients.showNotification("Fichier introuvable", "warning", null, "top_center", 2000);
                return;
            }
            String contentType = Files.probeContentType(f.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            Filedownload.save(f, contentType);
        } catch (Exception e) {
            log.error("Erreur lors du téléchargement du fichier {}", label, e);
            Clients.showNotification("Impossible de télécharger le fichier", "error", null, "top_center", 2500);
        }
    }

    @Command
    public void selectDocument(@BindingParam("doc") Object doc) {
        downloadFile(doc);
    }

    @Command
    public void goToSuivi() {
        navigateTo("/documents/views/concours/suivi-dossiers.zul", "Suivi dossiers");
    }

    @Command
    public void goToValidationDossiers() {
        navigateTo("/documents/views/concours/validation-dossiers.zul", "Validation dossiers");
    }

    @Command
    public void goToValidationDocuments() {
        navigateTo("/documents/views/concours/validation-documents.zul", "Validation documents");
    }

    @Command
    public void goToConcoursList() {
        navigateTo("/documents/views/concours/list.zul", "Concours");
    }

    @Command
    public void goToUpload() {
        navigateTo("/documents/views/concours/upload-candidat.zul", "Dépôt candidat");
    }

    // ---------------- Helpers ----------------
    private void loadDashboard() {
        loadDossierStats();
        loadDocumentStats();
        loadConcoursStats();
        lastRefreshLabel = DATE_FORMAT.format(new Date());
    }

    private void loadDossierStats() {
        totalDossiers = DashboardStatsGestion.countDossiers();

        Map<DocumentValidationEtat, Long> statusCounts = Optional.ofNullable(DashboardStatsGestion.getDossierStatusCounts())
                .orElseGet(() -> new EnumMap<>(DocumentValidationEtat.class));

        dossiersValides = statusCounts.getOrDefault(DocumentValidationEtat.VALIDE, 0L);
        dossiersRejetes = statusCounts.getOrDefault(DocumentValidationEtat.REJETE, 0L);
        dossiersEnCours = statusCounts.getOrDefault(DocumentValidationEtat.EN_COURS, 0L);

        dossierStatusStats = Arrays.stream(DocumentValidationEtat.values())
                .map(etat -> {
                    long count = statusCounts.getOrDefault(etat, 0L);
                    int percent = totalDossiers == 0 ? 0 : (int) Math.round(count * 100.0 / totalDossiers);
                    return new DossierStatusStat(etat.getLabel(), count, percent, etat.getChipSclass());
                })
                .collect(Collectors.toList());
    }

    private void loadDocumentStats() {
        totalDocuments = DashboardStatsGestion.countDocuments();
        storageStats = Optional.ofNullable(DashboardStatsGestion.getStorageStats())
                .orElseGet(ArrayList::new);
        long totalSize = DashboardStatsGestion.sumDocumentSizes();
        storageUsageLabel = humanReadableByteCount(totalSize);
        storageUsagePercent = STORAGE_CAPACITY_BYTES == 0
                ? 0
                : (int) Math.min(100, Math.round((double) totalSize / STORAGE_CAPACITY_BYTES * 100));

        List<Document> raw = Optional.ofNullable(DashboardStatsGestion.getRecentDocuments(8))
                .orElseGet(ArrayList::new);
        if (raw.isEmpty()) {
            raw = Optional.ofNullable(DocumentGestion.findAllDocuments()).orElseGet(ArrayList::new);
            raw = raw.stream()
                    .sorted((a, b) -> {
                        Date da = a.getDateUpload();
                        Date db = b.getDateUpload();
                        if (da == null && db == null) return 0;
                        if (da == null) return 1;
                        if (db == null) return -1;
                        return db.compareTo(da);
                    })
                    .limit(8)
                    .collect(Collectors.toList());
        }
        recentDocuments = raw.stream().map(RecentDocument::new).collect(Collectors.toList());
    }

    private void loadConcoursStats() {
        concoursStats = Optional.ofNullable(DashboardStatsGestion.getConcoursVolumeStats())
                .map(list -> list.stream().limit(6).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    private void navigateTo(String view, String page) {
        Map<String, Object> args = new HashMap<>();
        args.put("view", view);
        args.put("section", "Concours");
        args.put("page", page);
        args.put("label", page);
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal", args);
    }

    public String getValidationRateLabel() {
        if (totalDossiers == 0) {
            return "Aucun dossier suivi";
        }
        int rate = (int) Math.round(dossiersValides * 100.0 / totalDossiers);
        return rate + "% validés";
    }

    public String formatDate(Date date) {
        if (date == null) return "-";
        return DATE_FORMAT.format(date);
    }

    public String formatSize(Long size) {
        if (size == null || size <= 0) return "-";
        return humanReadableByteCount(size);
    }

    public String getStorageUsageText() {
        return "Utilisation : " + storageUsagePercent + "%";
    }

    public boolean isHasRecentDocuments() {
        return recentDocuments != null && !recentDocuments.isEmpty();
    }

    public boolean isHasConcoursStats() {
        return concoursStats != null && !concoursStats.isEmpty();
    }

    private String humanReadableByteCount(long bytes) {
        if (bytes <= 0) {
            return "0 Mo";
        }
        String[] units = {"octets", "Ko", "Mo", "Go", "To"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return new DecimalFormat("#,##0.#", DecimalFormatSymbols.getInstance(Locale.FRENCH))
                .format(bytes / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static class DossierStatusStat {
        private final String label;
        private final long count;
        private final int percent;
        private final String sclass;

        public DossierStatusStat(String label, long count, int percent, String sclass) {
            this.label = label;
            this.count = count;
            this.percent = percent;
            this.sclass = sclass;
        }

        public String getLabel() {
            return label;
        }

        public long getCount() {
            return count;
        }

        public int getPercent() {
            return percent;
        }

        public String getSclass() {
            return sclass;
        }

        public String getPercentLabel() {
            return percent + "%";
        }

        public String getWidthStyle() {
            return "width:" + percent + "%";
        }
    }

    public static class RecentDocument {
        private final String title;
        private final String type;
        private final Long size;
        private final Date date;
        private final String path;

        public RecentDocument(Document doc) {
            this.title = doc != null ? doc.getTitre() : "-";
            this.type = doc != null && doc.getType() != null ? doc.getType() : "-";
            this.size = doc != null ? doc.getTaille() : null;
            this.date = doc != null ? doc.getDateUpload() : null;
            this.path = doc != null ? doc.getPath() : null;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type != null ? type : "-";
        }

        public String getSizeLabel() {
            if (size == null || size <= 0) {
                return "-";
            }
            return humanReadableByteCountStatic(size);
        }

        public String getImportDateLabel() {
            if (date == null) return "-";
            return DATE_FORMAT.format(date);
        }

        public String getPath() {
            return path;
        }

        private static String humanReadableByteCountStatic(long bytes) {
            if (bytes <= 0) {
                return "0 Mo";
            }
            String[] units = {"octets", "Ko", "Mo", "Go", "To"};
            int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
            return new DecimalFormat("#,##0.#", DecimalFormatSymbols.getInstance(Locale.FRENCH))
                    .format(bytes / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        }
    }
}
