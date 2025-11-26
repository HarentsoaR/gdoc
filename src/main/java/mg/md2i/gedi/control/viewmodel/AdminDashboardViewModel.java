package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.dto.dashboard.StorageStat;
import mg.md2i.gedi.entity.Connexion1;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.DashboardStatsGestion;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Tableau de bord Admin simplifié et alimenté en temps réel.
 */
public class AdminDashboardViewModel {

    private static final long STORAGE_CAPACITY_BYTES = 20L * 1024 * 1024 * 1024; // 20 Go
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm", java.util.Locale.FRENCH);

    private int totalDocuments;
    private int totalUsers;
    private int activeUsers;
    private int totalDossiers;
    private int dossiersValides;
    private int storageUsagePercent;
    private String storageUsageLabel = "0 Mo";
    private String lastRefreshLabel = "-";

    private List<Document> recentDocuments = new ArrayList<>();
    private List<Connexion1> recentConnections = new ArrayList<>();
    private Map<DocumentValidationEtat, Long> dossierStatus;
    private Map<DocumentValidationEtat, Long> concours101Status;
    private List<StorageStat> storageStats = new ArrayList<>();

    @Init
    public void init() {
        loadDashboard();
    }

    @Command
    @NotifyChange({"totalDocuments", "totalUsers", "activeUsers", "totalDossiers", "storageUsagePercent",
            "storageUsageLabel", "lastRefreshLabel", "recentDocuments", "validationRateLabel",
            "dossierStatusEntries", "dossierStatusStats", "recentConnections", "concours101Status"})
    public void refreshDashboard() {
        loadDashboard();
    }

    private void loadDashboard() {
        loadDocumentStats();
        loadUserStats();
        loadDossierStats();
        loadConnections();
        loadConcours101Status();
        lastRefreshLabel = DATE_TIME_FORMAT.format(new Date());
    }

    private void loadDocumentStats() {
        totalDocuments = (int) DashboardStatsGestion.countDocuments();
        recentDocuments = Optional.ofNullable(DashboardStatsGestion.getRecentDocuments(8))
                .orElseGet(ArrayList::new);

        storageStats = Optional.ofNullable(DashboardStatsGestion.getStorageStats())
                .orElseGet(ArrayList::new);
        long totalSize = DashboardStatsGestion.sumDocumentSizes();
        storageUsagePercent = STORAGE_CAPACITY_BYTES == 0
                ? 0
                : (int) Math.min(100, Math.round((double) totalSize / STORAGE_CAPACITY_BYTES * 100));
        storageUsageLabel = humanReadableByteCount(totalSize);
    }

    private void loadUserStats() {
        totalUsers = (int) DashboardStatsGestion.countUsers();
        activeUsers = (int) DashboardStatsGestion.countActiveUsers();
    }

    private void loadDossierStats() {
        totalDossiers = (int) DashboardStatsGestion.countDossiers();
        dossiersValides = (int) DashboardStatsGestion.countValidatedDossiers();
        dossierStatus = Optional.ofNullable(DashboardStatsGestion.getDossierStatusCounts())
                .orElseGet(() -> new java.util.EnumMap<>(DocumentValidationEtat.class));
    }

    private void loadConnections() {
        recentConnections = Optional.ofNullable(DashboardStatsGestion.getRecentConnections(10))
                .orElseGet(ArrayList::new);
    }

    private void loadConcours101Status() {
        concours101Status = Optional.ofNullable(DashboardStatsGestion.getDossierStatusCountsByConcours(101))
                .orElseGet(java.util.Collections::emptyMap);
    }

    public int getTotalDocuments() { return totalDocuments; }
    public int getTotalUsers() { return totalUsers; }
    public int getActiveUsers() { return activeUsers; }
    public int getTotalDossiers() { return totalDossiers; }
    public String getStorageUsageLabel() { return storageUsageLabel; }
    public int getStorageUsagePercent() { return storageUsagePercent; }
    public String getLastRefreshLabel() { return lastRefreshLabel; }
    public List<Document> getRecentDocuments() { return recentDocuments; }
    public List<Connexion1> getRecentConnections() { return recentConnections; }
    public Map<DocumentValidationEtat, Long> getDossierStatus() { return dossierStatus; }
    public Map<DocumentValidationEtat, Long> getConcours101Status() { return concours101Status; }

    public List<Map.Entry<DocumentValidationEtat, Long>> getDossierStatusEntries() {
        return dossierStatus == null ? new ArrayList<>() : new ArrayList<>(dossierStatus.entrySet());
    }

    public List<Map.Entry<DocumentValidationEtat, Long>> getConcours101Entries() {
        return concours101Status == null ? new ArrayList<>() : new ArrayList<>(concours101Status.entrySet());
    }

    public String getValidationRateLabel() {
        if (totalDossiers == 0) return "Aucun dossier suivi";
        int rate = (int) Math.round(dossiersValides * 100.0 / totalDossiers);
        return rate + "% de dossiers validés";
    }

    public String getActiveUsersLabel() {
        return "Utilisateurs actifs / " + totalUsers;
    }

    public String formatSize(Long size) {
        if (size == null || size <= 0) return "-";
        return humanReadableByteCount(size);
    }

    public String formatDate(Date date) {
        if (date == null) return "-";
        return DATE_TIME_FORMAT.format(date);
    }

    public String formatEtat(DocumentValidationEtat etat) {
        return etat != null ? etat.getLabel() : "-";
    }

    public String formatUser(Connexion1 cx) {
        if (cx == null) return "-";
        if (cx.getUtilisateur() != null && cx.getUtilisateur().getLogin() != null) {
            return cx.getUtilisateur().getLogin();
        }
        return cx.getUtilisateurId() != null ? String.valueOf(cx.getUtilisateurId()) : "-";
    }

    private String humanReadableByteCount(long bytes) {
        if (bytes < 1024) return bytes + " o";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format(java.util.Locale.FRENCH, "%.1f %so", bytes / Math.pow(1024, exp), pre);
    }
}
