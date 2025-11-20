package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.dto.dashboard.ConcoursTimelineStat;
import mg.md2i.gedi.dto.dashboard.ConcoursVolumeStat;
import mg.md2i.gedi.dto.dashboard.MonthlyDocumentStat;
import mg.md2i.gedi.dto.dashboard.StorageStat;
import mg.md2i.gedi.dto.dashboard.UploadHeatmapPoint;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.DashboardStatsGestion;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminDashboardViewModel {

    private static final long STORAGE_CAPACITY_BYTES = 20L * 1024 * 1024 * 1024;
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.FRENCH);
    private static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yy", Locale.FRENCH);

    private int totalDocuments;
    private int totalUsers;
    private int activeUsers;
    private int totalDossiers;
    private int storageUsagePercent;
    private String storageUsageLabel = "0 Mo";
    private String lastRefreshLabel = "-";
    private List<Document> recentDocuments = new ArrayList<>();

    private int dossiersValides;
    private Map<DocumentValidationEtat, Long> dossierStatus = new EnumMap<>(DocumentValidationEtat.class);
    private List<StorageStat> storageStats = new ArrayList<>();
    private List<ConcoursVolumeStat> concoursStats = new ArrayList<>();
    private List<MonthlyDocumentStat> trendStats = new ArrayList<>();
    private List<ConcoursTimelineStat> timelineStats = new ArrayList<>();
    private List<UploadHeatmapPoint> heatmapStats = new ArrayList<>();

    private PieModel dossierPieModel = new SimplePieModel();
    private PieModel storagePieModel = new SimplePieModel();
    private CategoryModel concoursBarModel = new SimpleCategoryModel();
    private CategoryModel trendLineModel = new SimpleCategoryModel();
    private CategoryModel timelineModel = new SimpleCategoryModel();
    private CategoryModel heatmapModel = new SimpleCategoryModel();

    @Init
    public void init() {
        loadDashboard();
    }

    @Command
    @NotifyChange({"totalDocuments", "totalUsers", "activeUsers", "totalDossiers", "storageUsagePercent",
            "storageUsageLabel", "lastRefreshLabel", "recentDocuments", "validationRateLabel",
            "storageStats", "concoursStats", "trendStats", "timelineStats", "heatmapStats",
            "dossierStatusEntries", "dossierStatusStats"})
    public void refreshDashboard() {
        loadDashboard();
    }

    private void loadDashboard() {
        loadDocumentStats();
        loadUserStats();
        loadDossierStats();
        lastRefreshLabel = DATE_TIME_FORMAT.format(new Date());
    }

    private void loadDocumentStats() {
        totalDocuments = (int) DashboardStatsGestion.countDocuments();
        recentDocuments = Optional.ofNullable(DashboardStatsGestion.getRecentDocuments(6))
                .orElseGet(ArrayList::new);

        storageStats = Optional.ofNullable(DashboardStatsGestion.getStorageStats())
                .orElseGet(ArrayList::new);
        trendStats = Optional.ofNullable(DashboardStatsGestion.getMonthlyDocumentStats(6))
                .orElseGet(ArrayList::new);
        timelineStats = Optional.ofNullable(DashboardStatsGestion.getConcoursTimelineStats())
                .orElseGet(ArrayList::new);
        heatmapStats = Optional.ofNullable(DashboardStatsGestion.getUploadHeatmapStats(35))
                .orElseGet(ArrayList::new);

        long totalSize = DashboardStatsGestion.sumDocumentSizes();
        storageUsagePercent = STORAGE_CAPACITY_BYTES == 0
                ? 0
                : (int) Math.min(100, Math.round((double) totalSize / STORAGE_CAPACITY_BYTES * 100));
        storageUsageLabel = humanReadableByteCount(totalSize);

        buildStoragePieModel();
        buildTrendModel();
        buildTimelineModel();
        buildHeatmapModel();
    }

    private void loadUserStats() {
        totalUsers = (int) DashboardStatsGestion.countUsers();
        activeUsers = (int) DashboardStatsGestion.countActiveUsers();
    }

    private void loadDossierStats() {
        totalDossiers = (int) DashboardStatsGestion.countDossiers();
        dossiersValides = (int) DashboardStatsGestion.countValidatedDossiers();

        Map<DocumentValidationEtat, Long> counts = DashboardStatsGestion.getDossierStatusCounts();
        EnumMap<DocumentValidationEtat, Long> aggregated = new EnumMap<>(DocumentValidationEtat.class);
        if (counts != null) {
            aggregated.putAll(counts);
        }
        dossierStatus = aggregated;

        concoursStats = Optional.ofNullable(DashboardStatsGestion.getConcoursVolumeStats())
                .map(list -> list.stream().limit(8).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        buildDossierPieModel();
        buildConcoursBarModel();
    }

    public int getTotalDocuments() {
        return totalDocuments;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public int getTotalDossiers() {
        return totalDossiers;
    }

    public int getStorageUsagePercent() {
        return storageUsagePercent;
    }

    public String getStorageUsageLabel() {
        return storageUsageLabel;
    }

    public String getStorageUsageText() {
        return "Utilisation : " + storageUsagePercent + "%";
    }

    public String getLastRefreshLabel() {
        return lastRefreshLabel;
    }

    public List<Document> getRecentDocuments() {
        return recentDocuments;
    }

    public List<StorageStat> getStorageStats() {
        return storageStats;
    }

    public List<ConcoursVolumeStat> getConcoursStats() {
        return concoursStats;
    }

    public List<MonthlyDocumentStat> getTrendStats() {
        return trendStats;
    }

    public List<ConcoursTimelineStat> getTimelineStats() {
        return timelineStats;
    }

    public List<UploadHeatmapPoint> getHeatmapStats() {
        return heatmapStats;
    }

    public PieModel getDossierPieModel() {
        return dossierPieModel;
    }

    public PieModel getStoragePieModel() {
        return storagePieModel;
    }

    public CategoryModel getConcoursBarModel() {
        return concoursBarModel;
    }

    public CategoryModel getTrendLineModel() {
        return trendLineModel;
    }

    public CategoryModel getTimelineModel() {
        return timelineModel;
    }

    public CategoryModel getHeatmapModel() {
        return heatmapModel;
    }

    public List<Map.Entry<DocumentValidationEtat, Long>> getDossierStatusEntries() {
        return new ArrayList<>(dossierStatus.entrySet());
    }

    public List<DossierStatusStat> getDossierStatusStats() {
        List<DossierStatusStat> stats = new ArrayList<>();
        for (DocumentValidationEtat etat : DocumentValidationEtat.values()) {
            long count = dossierStatus.getOrDefault(etat, 0L);
            int percent = totalDossiers == 0 ? 0 : (int) Math.round(count * 100.0 / totalDossiers);
            stats.add(new DossierStatusStat(etat.getLabel(), count, percent));
        }
        return stats;
    }

    public String getValidationRateLabel() {
        if (totalDossiers == 0) {
            return "Aucun dossier suivi";
        }
        int rate = (int) Math.round(dossiersValides * 100.0 / totalDossiers);
        return rate + "% de dossiers validés";
    }

    public String getActiveUsersLabel() {
        return "Utilisateurs actifs / " + totalUsers;
    }

    public String formatSize(Long size) {
        if (size == null || size <= 0) {
            return "-";
        }
        return humanReadableByteCount(size);
    }

    public String formatDate(Date date) {
        if (date == null) {
            return "-";
        }
        return DATE_TIME_FORMAT.format(date);
    }

    public String formatMonthLabel(MonthlyDocumentStat stat) {
        if (stat == null || stat.getMonth() == null) {
            return "-";
        }
        YearMonth month = stat.getMonth();
        return month.getMonth().getDisplayName(TextStyle.SHORT, Locale.FRENCH) + " " + month.getYear();
    }

    public String formatLocalDate(LocalDate date) {
        if (date == null) {
            return "-";
        }
        return date.format(LOCAL_DATE_FORMAT);
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

    private void buildDossierPieModel() {
        SimplePieModel model = new SimplePieModel();
        for (DocumentValidationEtat etat : DocumentValidationEtat.values()) {
            model.setValue(etat.getLabel(), dossierStatus.getOrDefault(etat, 0L));
        }
        dossierPieModel = model;
    }

    private void buildStoragePieModel() {
        SimplePieModel model = new SimplePieModel();
        storageStats.stream()
                .sorted((a, b) -> Long.compare(b.getSizeBytes(), a.getSizeBytes()))
                .forEach(stat -> model.setValue(stat.getLabel(), stat.getSizeBytes()));
        storagePieModel = model;
    }

    private void buildConcoursBarModel() {
        SimpleCategoryModel model = new SimpleCategoryModel();
        concoursStats.forEach(stat -> model.setValue("Dossiers", stat.getLabel(), stat.getDossiersCount()));
        concoursBarModel = model;
    }

    private void buildTrendModel() {
        SimpleCategoryModel model = new SimpleCategoryModel();
        trendStats.forEach(stat -> {
            String label = formatMonthLabel(stat);
            model.setValue("Documents", label, stat.getDocumentCount());
            model.setValue("Volume (Mo)", label, stat.getTotalSizeBytes() / 1_048_576d);
        });
        trendLineModel = model;
    }

    private void buildTimelineModel() {
        SimpleCategoryModel model = new SimpleCategoryModel();
        timelineStats.forEach(stat -> model.setValue("Durée (jours)", stat.getLabel(), stat.getDurationDays()));
        timelineModel = model;
    }

    private void buildHeatmapModel() {
        SimpleCategoryModel model = new SimpleCategoryModel();
        heatmapStats.forEach(point -> model.setValue("Imports", formatLocalDate(point.getDate()), point.getCount()));
        heatmapModel = model;
    }

    public static class DossierStatusStat {
        private final String label;
        private final long count;
        private final int percent;

        public DossierStatusStat(String label, long count, int percent) {
            this.label = label;
            this.count = count;
            this.percent = percent;
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
    }
}
