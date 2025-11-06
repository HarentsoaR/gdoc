package mg.md2i.gedi.dto;

public class SearchResult {
    private Long dbId;
    private String candidateFullName;
    private String concoursDisplayInfo;
    private String docTypeName;
    private String filePath;
    private String filiere;
    private String promotion;
    private String centreExamen;

    public SearchResult(Long dbId, String candidateFullName, String concoursDisplayInfo, String docTypeName, String filePath, String filiere, String promotion, String centreExamen) {
        this.dbId = dbId;
        this.candidateFullName = candidateFullName;
        this.concoursDisplayInfo = concoursDisplayInfo;
        this.docTypeName = docTypeName;
        this.filePath = filePath;
        this.filiere = filiere;
        this.promotion = promotion;
        this.centreExamen = centreExamen;
    }

    public Long getDbId() { return dbId; }
    public void setDbId(Long dbId) { this.dbId = dbId; }
    public String getCandidateFullName() { return candidateFullName; }
    public void setCandidateFullName(String candidateFullName) { this.candidateFullName = candidateFullName; }
    public String getConcoursDisplayInfo() { return concoursDisplayInfo; }
    public void setConcoursDisplayInfo(String concoursDisplayInfo) { this.concoursDisplayInfo = concoursDisplayInfo; }
    public String getDocTypeName() { return docTypeName; }
    public void setDocTypeName(String docTypeName) { this.docTypeName = docTypeName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
    public String getPromotion() { return promotion; }
    public void setPromotion(String promotion) { this.promotion = promotion; }
    public String getCentreExamen() { return centreExamen; }
    public void setCentreExamen(String centreExamen) { this.centreExamen = centreExamen; }
}