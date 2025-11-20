package mg.md2i.gedi.dto;

import mg.md2i.gedi.enums.DocumentValidationEtat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResult {
    private Long dbId;
    private Integer candidatId;
    private Integer concoursId;
    private Integer centreExamenId;
    private String candidateFullName;
    private String concoursDisplayInfo;
    private String docTypeName;
    private String filePath;
    private String filiere;
    private String promotion;
    private String centreExamen;
    private String numeroEnregistrement;
    private String numeroInscription;
    private DocumentValidationEtat etat;

    public SearchResult(Long dbId,
                        Integer candidatId,
                        Integer concoursId,
                        Integer centreExamenId,
                        String candidateFullName,
                        String concoursDisplayInfo,
                        String docTypeName,
                        String filePath,
                        String filiere,
                        String promotion,
                        String centreExamen,
                        String numeroEnregistrement,
                        String numeroInscription,
                        DocumentValidationEtat etat) {
        this.dbId = dbId;
        this.candidatId = candidatId;
        this.concoursId = concoursId;
        this.centreExamenId = centreExamenId;
        this.candidateFullName = candidateFullName;
        this.concoursDisplayInfo = concoursDisplayInfo;
        this.docTypeName = docTypeName;
        this.filePath = filePath;
        this.filiere = filiere;
        this.promotion = promotion;
        this.centreExamen = centreExamen;
        this.numeroEnregistrement = numeroEnregistrement;
        this.numeroInscription = numeroInscription;
        this.etat = etat != null ? etat : DocumentValidationEtat.EN_COURS;
    }

    public Long getDbId() { return dbId; }
    public void setDbId(Long dbId) { this.dbId = dbId; }
    public Integer getCandidatId() { return candidatId; }
    public void setCandidatId(Integer candidatId) { this.candidatId = candidatId; }
    public Integer getConcoursId() { return concoursId; }
    public void setConcoursId(Integer concoursId) { this.concoursId = concoursId; }
    public Integer getCentreExamenId() { return centreExamenId; }
    public void setCentreExamenId(Integer centreExamenId) { this.centreExamenId = centreExamenId; }
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
    public String getNumeroEnregistrement() { return numeroEnregistrement; }
    public void setNumeroEnregistrement(String numeroEnregistrement) { this.numeroEnregistrement = numeroEnregistrement; }
    public String getNumeroInscription() { return numeroInscription; }
    public void setNumeroInscription(String numeroInscription) { this.numeroInscription = numeroInscription; }
    public DocumentValidationEtat getEtat() { return etat; }
    public void setEtat(DocumentValidationEtat etat) { this.etat = etat; }

    public String getEtatLabel() {
        return etat != null ? etat.getLabel() : DocumentValidationEtat.EN_COURS.getLabel();
    }

    public String getEtatSclass() {
        return etat != null ? etat.getChipSclass() : DocumentValidationEtat.EN_COURS.getChipSclass();
    }

    public boolean hasAttachment() {
        return filePath != null && !filePath.trim().isEmpty();
    }

    public String getMetaLine() {
        List<String> parts = new ArrayList<>();
        if (promotion != null && !promotion.isEmpty()) parts.add(promotion);
        if (filiere != null && !filiere.isEmpty()) parts.add(filiere);
        if (centreExamen != null && !centreExamen.isEmpty()) parts.add(centreExamen);
        return parts.stream().collect(Collectors.joining(" • "));
    }

    public String getDossierLine() {
        List<String> parts = new ArrayList<>();
        if (numeroEnregistrement != null && !numeroEnregistrement.isEmpty()) {
            parts.add("N° enregistrement " + numeroEnregistrement);
        }
        if (numeroInscription != null && !numeroInscription.isEmpty()) {
            parts.add("N° inscription " + numeroInscription);
        }
        return parts.stream().collect(Collectors.joining(" • "));
    }
}
