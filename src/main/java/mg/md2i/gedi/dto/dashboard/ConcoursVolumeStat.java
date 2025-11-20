package mg.md2i.gedi.dto.dashboard;

public class ConcoursVolumeStat {
    private final String label;
    private final long dossiersCount;

    public ConcoursVolumeStat(String label, long dossiersCount) {
        this.label = label;
        this.dossiersCount = dossiersCount;
    }

    public String getLabel() {
        return label;
    }

    public long getDossiersCount() {
        return dossiersCount;
    }
}
