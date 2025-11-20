package mg.md2i.gedi.dto.dashboard;

import java.time.LocalDate;

public class UploadHeatmapPoint {
    private final LocalDate date;
    private final long count;

    public UploadHeatmapPoint(LocalDate date, long count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getCount() {
        return count;
    }

    public String getLabel() {
        return date != null ? date.toString() : "";
    }
}
