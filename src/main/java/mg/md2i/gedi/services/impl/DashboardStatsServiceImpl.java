package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.dto.dashboard.ConcoursTimelineStat;
import mg.md2i.gedi.dto.dashboard.ConcoursVolumeStat;
import mg.md2i.gedi.dto.dashboard.MonthlyDocumentStat;
import mg.md2i.gedi.dto.dashboard.StorageStat;
import mg.md2i.gedi.dto.dashboard.UploadHeatmapPoint;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.repository.DocumentRepository;
import mg.md2i.gedi.repository.ListeDossierConcoursCandidatRepository;
import mg.md2i.gedi.repository.ConcoursRepository;
import mg.md2i.gedi.repository.UtilisateurRepository;
import mg.md2i.gedi.services.DashboardStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardStatsServiceImpl implements DashboardStatsService {

    private final DocumentRepository documentRepository;
    private final ListeDossierConcoursCandidatRepository dossierRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ConcoursRepository concoursRepository;

    @Autowired
    public DashboardStatsServiceImpl(DocumentRepository documentRepository,
                                     ListeDossierConcoursCandidatRepository dossierRepository,
                                     UtilisateurRepository utilisateurRepository,
                                     ConcoursRepository concoursRepository) {
        this.documentRepository = documentRepository;
        this.dossierRepository = dossierRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.concoursRepository = concoursRepository;
    }

    @Override
    public long getTotalDocuments() {
        return documentRepository.countByActif(1);
    }

    @Override
    public long getTotalDocumentSize() {
        Long sum = documentRepository.sumActiveDocumentSizes();
        return sum != null ? sum : 0L;
    }

    @Override
    public List<StorageStat> getStorageByType() {
        return documentRepository.sumSizeByType()
                .stream()
                .map(row -> new StorageStat(
                        row[0] != null ? row[0].toString() : "Autres",
                        row[1] != null ? ((Number) row[1]).longValue() : 0L))
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyDocumentStat> getDocumentMonthlyStats(int monthsBack) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -monthsBack + 1);
        Date fromDate = cal.getTime();

        List<Object[]> raw = documentRepository.countAndSizeByMonth(fromDate);
        List<MonthlyDocumentStat> stats = new ArrayList<>();
        for (Object[] row : raw) {
            Integer year = (Integer) row[0];
            Integer month = (Integer) row[1];
            YearMonth yearMonth = YearMonth.of(year, month);
            long count = row[2] != null ? ((Number) row[2]).longValue() : 0L;
            long size = row[3] != null ? ((Number) row[3]).longValue() : 0L;
            stats.add(new MonthlyDocumentStat(yearMonth, count, size));
        }
        return stats;
    }

    @Override
    public List<Document> getRecentDocuments(int limit) {
        return documentRepository.findByActifOrderByDateUploadDesc(1, PageRequest.of(0, limit));
    }

    @Override
    public long getTotalDossiers() {
        return dossierRepository.countByActif(1);
    }

    @Override
    public long getValidatedDossiers() {
        return dossierRepository.countByActifAndEtatDocument(1, DocumentValidationEtat.VALIDE.getCode());
    }

    @Override
    public Map<DocumentValidationEtat, Long> getDossierStatusCounts() {
        List<Object[]> rows = dossierRepository.countByEtat();
        Map<DocumentValidationEtat, Long> map = new EnumMap<>(DocumentValidationEtat.class);
        for (Object[] row : rows) {
            Integer code = row[0] != null ? ((Number) row[0]).intValue() : null;
            DocumentValidationEtat etat = DocumentValidationEtat.fromCode(code);
            long count = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            map.put(etat, count);
        }
        return map;
    }

    @Override
    public List<ConcoursVolumeStat> getDossierCountsByConcours() {
        return dossierRepository.countByConcours()
                .stream()
                .map(row -> new ConcoursVolumeStat(
                        row[0] != null ? row[0].toString() : "Concours non défini",
                        row[1] != null ? ((Number) row[1]).longValue() : 0L))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcoursTimelineStat> getConcoursTimelineStats() {
        return concoursRepository.findByActif(1).stream()
                .map(concours -> {
                    Date start = concours.getDateDebutLimiteAge();
                    Date end = concours.getDateFinLimiteAge();
                    int duration = 0;
                    if (start != null && end != null) {
                        LocalDate s = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate e = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        duration = (int) ChronoUnit.DAYS.between(s, e);
                        if (duration < 0) duration = 0;
                    }
                    return new ConcoursTimelineStat(
                            concours.getDisplayInfo(),
                            start,
                            end,
                            duration);
                })
                .sorted(Comparator.comparing(ConcoursTimelineStat::getStartDate, Comparator.nullsLast(Date::compareTo)))
                .collect(Collectors.toList());
    }

    @Override
    public List<UploadHeatmapPoint> getUploadHeatmapStats(int daysBack) {
        int window = Math.max(daysBack, 1);
        LocalDate from = LocalDate.now().minusDays(window - 1L);
        Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Map<LocalDate, Long> counts = documentRepository.countByDay(fromDate).stream()
                .collect(Collectors.toMap(
                        row -> convertToLocalDate((Date) row[0]),
                        row -> row[1] != null ? ((Number) row[1]).longValue() : 0L));

        List<UploadHeatmapPoint> points = new ArrayList<>();
        LocalDate cursor = from;
        LocalDate today = LocalDate.now();
        while (!cursor.isAfter(today)) {
            points.add(new UploadHeatmapPoint(cursor, counts.getOrDefault(cursor, 0L)));
            cursor = cursor.plusDays(1);
        }
        return points;
    }

    private LocalDate convertToLocalDate(Date date) {
        if (date == null) return LocalDate.now();
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public long getTotalUsers() {
        return utilisateurRepository.count();
    }

    @Override
    public long getActiveUsers() {
        return utilisateurRepository.countByActif(1);
    }
}
