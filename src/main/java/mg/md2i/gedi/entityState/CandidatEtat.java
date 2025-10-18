package mg.md2i.gedi.entityState;

import java.util.List;
import java.util.Objects;

public class CandidatEtat {
	
	private String documentOk;
	private List<String>docTabIncomplet;
	private String documentEtat;
	private String ageOk;
	private String autreOk;
	private String ageEtat;
	private String autreEtat;
	private String nom;
	private String rangConcours;
	
	public String getDocumentOk() {
		return documentOk;
	}
	public void setDocumentOk(String documentOk) {
		this.documentOk = documentOk;
	}
	public List<String> getDocTabIncomplet() {
		return docTabIncomplet;
	}
	public void setDocTabIncomplet(List<String> docTabIncomplet) {
		this.docTabIncomplet = docTabIncomplet;
	}
	public String getDocumentEtat() {
		return documentEtat;
	}
	public void setDocumentEtat(String documentEtat) {
		this.documentEtat = documentEtat;
	}
	public String getAgeOk() {
		return ageOk;
	}
	public void setAgeOk(String ageOk) {
		this.ageOk = ageOk;
	}
	public String getAutreOk() {
		return autreOk;
	}
	public void setAutreOk(String autreOk) {
		this.autreOk = autreOk;
	}
	public String getAgeEtat() {
		return ageEtat;
	}
	public void setAgeEtat(String ageEtat) {
		this.ageEtat = ageEtat;
	}
	public String getAutreEtat() {
		return autreEtat;
	}
	public void setAutreEtat(String autreEtat) {
		this.autreEtat = autreEtat;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getRangConcours() {
		return rangConcours;
	}
	public void setRangConcours(String rangConcours) {
		this.rangConcours = rangConcours;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ageEtat, ageOk, autreEtat, autreOk, docTabIncomplet, documentEtat, documentOk, nom,
				rangConcours);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidatEtat other = (CandidatEtat) obj;
		return Objects.equals(ageEtat, other.ageEtat) && Objects.equals(ageOk, other.ageOk)
				&& Objects.equals(autreEtat, other.autreEtat) && Objects.equals(autreOk, other.autreOk)
				&& Objects.equals(docTabIncomplet, other.docTabIncomplet)
				&& Objects.equals(documentEtat, other.documentEtat) && Objects.equals(documentOk, other.documentOk)
				&& Objects.equals(nom, other.nom) && Objects.equals(rangConcours, other.rangConcours);
	}

}

