package mg.md2i.gedi.trash;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Simple DTO persisted in a JSON file to track documents moved to the trash.
 */
public class TrashEntry {
    private Integer documentId;
    private String documentLabel;
    private String candidatName;
    private String fileName;
    private long deletedAt;
    private String type = "document";

    public TrashEntry() {}

    public TrashEntry(Integer documentId, String documentLabel, String candidatName, String fileName, long deletedAt) {
        this.documentId = documentId;
        this.documentLabel = documentLabel;
        this.candidatName = candidatName;
        this.fileName = fileName;
        this.deletedAt = deletedAt;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentLabel() {
        return documentLabel;
    }

    public void setDocumentLabel(String documentLabel) {
        this.documentLabel = documentLabel;
    }

    public String getCandidatName() {
        return candidatName;
    }

    public void setCandidatName(String candidatName) {
        this.candidatName = candidatName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeletedAtLabel() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
        return fmt.format(Instant.ofEpochMilli(deletedAt));
    }
}
