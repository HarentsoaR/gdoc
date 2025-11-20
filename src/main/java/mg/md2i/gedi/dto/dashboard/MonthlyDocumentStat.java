package mg.md2i.gedi.dto.dashboard;

import java.time.YearMonth;

public class MonthlyDocumentStat {
    private final YearMonth month;
    private final long documentCount;
    private final long totalSizeBytes;

    public MonthlyDocumentStat(YearMonth month, long documentCount, long totalSizeBytes) {
        this.month = month;
        this.documentCount = documentCount;
        this.totalSizeBytes = totalSizeBytes;
    }

    public YearMonth getMonth() {
        return month;
    }

    public long getDocumentCount() {
        return documentCount;
    }

    public long getTotalSizeBytes() {
        return totalSizeBytes;
    }
}
