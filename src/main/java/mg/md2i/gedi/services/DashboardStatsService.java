package mg.md2i.gedi.services;

import mg.md2i.gedi.dto.dashboard.ConcoursTimelineStat;
import mg.md2i.gedi.dto.dashboard.ConcoursVolumeStat;
import mg.md2i.gedi.dto.dashboard.MonthlyDocumentStat;
import mg.md2i.gedi.dto.dashboard.StorageStat;
import mg.md2i.gedi.dto.dashboard.UploadHeatmapPoint;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.enums.DocumentValidationEtat;

import java.util.List;
import java.util.Map;

public interface DashboardStatsService {
    long getTotalDocuments();
    long getTotalDocumentSize();
    List<StorageStat> getStorageByType();
    List<MonthlyDocumentStat> getDocumentMonthlyStats(int monthsBack);
    List<Document> getRecentDocuments(int limit);

    long getTotalDossiers();
    long getValidatedDossiers();
    Map<DocumentValidationEtat, Long> getDossierStatusCounts();
    List<ConcoursVolumeStat> getDossierCountsByConcours();
    List<ConcoursTimelineStat> getConcoursTimelineStats();
    List<UploadHeatmapPoint> getUploadHeatmapStats(int daysBack);

    long getTotalUsers();
    long getActiveUsers();
}
