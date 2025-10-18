package mg.md2i.gedi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Version;

import java.io.Serializable;
//import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(listeDossierConcoursCandidat) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_liste_dossier_concours_candidat")
public class ListeDossierConcoursCandidat implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "liste_dossier_concours_candidat_id",unique=true, nullable = false)
	private Integer listeDossierConcoursCandidatId;

	@Column(name="candidat_id", nullable =false)
	private Integer candidatId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "candidat_id", nullable = false, insertable=false , updatable=false)
	private Candidat candidat;

	@Column(name="document_concours_id", nullable =false)
	private Integer documentConcoursId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "document_concours_id", nullable = false, insertable=false , updatable=false)
	private DocumentConcours documentConcours;

	@Column(name="ldcc_etat_document", nullable =false)
	private Integer etatDocument;


	@Column(name="ldcc_remarque", nullable =false)
	private String remarque;


	@Column(name="ldcc_actif")
	private Integer actif=1;


	@Column(name="ldcc_version")
	private Integer version=1;


	@Column(name="ldcc_remarque_facultatif", nullable =true)
	private String remarqueFacultatif;


	public String getRemarqueFacultatif() {
		return remarqueFacultatif;
	}
	public void setRemarqueFacultatif(String remarqueFacultatif) {
		this.remarqueFacultatif = remarqueFacultatif;
	}
	public Integer getListeDossierConcoursCandidatId(){
		return listeDossierConcoursCandidatId;
	}
	public void setListeDossierConcoursCandidatId(Integer listeDossierConcoursCandidatId) { 
		this.listeDossierConcoursCandidatId = listeDossierConcoursCandidatId;
	}


	public Integer getCandidatId(){
		return candidatId;
	}
	public void setCandidatId(Integer candidatId) { 
		this.candidatId = candidatId;
	}
	public Candidat getCandidat() {
		return candidat;
	}
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}




	public Integer getDocumentConcoursId(){
		return documentConcoursId;
	}
	public void setDocumentConcoursId(Integer documentConcoursId) { 
		this.documentConcoursId = documentConcoursId;
	}
	public DocumentConcours getDocumentConcours() {
		return documentConcours;
	}
	public void setDocumentConcours(DocumentConcours documentConcours) {
		this.documentConcours = documentConcours;
	}




	public Integer getEtatDocument(){
		return etatDocument;
	}
	public void setEtatDocument(Integer etatDocument) { 
		this.etatDocument = etatDocument;
	}




	public String getRemarque(){
		return remarque;
	}
	public void setRemarque(String remarque) { 
		this.remarque = remarque;
	}




	public Integer getActif(){
		return actif;
	}
	public void setActif(Integer actif) { 
		this.actif = actif;
	}




	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer version) { 
		this.version = version;
	}

	private static final long serialVersionUID = 1L;
	
	private String etatDoc;
	public String getEtatDoc() {
		return etatDoc;
	}
	public void setEtatDoc(String etatDoc) {
		this.etatDoc = etatDoc;
	}
	
	private String docComplet;
	public String getDocComplet() {
		return docComplet;
	}
	public void setDocComplet(String docComplet) {
		this.docComplet = docComplet;
	}
	
	
	
}
