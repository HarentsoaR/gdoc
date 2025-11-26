package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.dto.dashboard.ConcoursTimelineStat;
import mg.md2i.gedi.dto.dashboard.ConcoursVolumeStat;
import mg.md2i.gedi.dto.dashboard.MonthlyDocumentStat;
import mg.md2i.gedi.dto.dashboard.StorageStat;
import mg.md2i.gedi.dto.dashboard.UploadHeatmapPoint;
import mg.md2i.gedi.entity.Connexion1;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.services.DashboardStatsService;

import java.util.List;
import java.util.Map;

public class DashboardStatsGestion {

    private static DashboardStatsService getService() {
        return ObjectFactory.getBean(DashboardStatsService.class);
    }

    public static long countDocuments() {
        return getService().getTotalDocuments();
    }

    public static long sumDocumentSizes() {
        return getService().getTotalDocumentSize();
    }

    public static List<StorageStat> getStorageStats() {
        return getService().getStorageByType();
    }

    public static List<MonthlyDocumentStat> getMonthlyDocumentStats(int monthsBack) {
        return getService().getDocumentMonthlyStats(monthsBack);
    }

    public static List<Document> getRecentDocuments(int limit) {
        return getService().getRecentDocuments(limit);
    }

    public static long countDossiers() {
        return getService().getTotalDossiers();
    }

    public static long countValidatedDossiers() {
        return getService().getValidatedDossiers();
    }

    public static Map<DocumentValidationEtat, Long> getDossierStatusCounts() {
        return getService().getDossierStatusCounts();
    }

    public static List<ConcoursVolumeStat> getConcoursVolumeStats() {
        return getService().getDossierCountsByConcours();
    }

    public static List<ConcoursTimelineStat> getConcoursTimelineStats() {
        return getService().getConcoursTimelineStats();
    }

    public static List<UploadHeatmapPoint> getUploadHeatmapStats(int daysBack) {
        return getService().getUploadHeatmapStats(daysBack);
    }

    public static long countUsers() {
        return getService().getTotalUsers();
    }

    public static long countActiveUsers() {
        return getService().getActiveUsers();
    }

    public static List<Connexion1> getRecentConnections(int limit) {
        return getService().getRecentConnections(limit);
    }

    public static Map<DocumentValidationEtat, Long> getDossierStatusCountsByConcours(Integer concoursId) {
        return getService().getDossierStatusCountsByConcours(concoursId);
    }
}
