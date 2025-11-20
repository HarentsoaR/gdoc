package mg.md2i.gedi.dto.dashboard;

public class StorageStat {
    private final String label;
    private final long sizeBytes;

    public StorageStat(String label, long sizeBytes) {
        this.label = label;
        this.sizeBytes = sizeBytes;
    }

    public String getLabel() {
        return label;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }
}
