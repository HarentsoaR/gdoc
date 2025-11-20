package mg.md2i.gedi.dto.dashboard;

import java.util.Date;

public class ConcoursTimelineStat {
    private final String label;
    private final Date startDate;
    private final Date endDate;
    private final int durationDays;

    public ConcoursTimelineStat(String label, Date startDate, Date endDate, int durationDays) {
        this.label = label;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationDays = durationDays;
    }

    public String getLabel() {
        return label;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getDurationDays() {
        return durationDays;
    }
}
