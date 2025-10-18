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
//import javax.persistence.CascadeType;

/**
 * Entite(listeCentreConcoursCandidat) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_liste_centre_concours_candidat")
public class ListeCentreConcoursCandidat implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "liste_centre_concours_candidat_id",unique=true, nullable = false)
	private Integer listeCentreConcoursCandidatId;

	@Column(name="concours_phase_id", nullable =false)
	private Integer concoursPhaseId;

	@ManyToOne
	@JoinColumn(name = "concours_phase_id", insertable=false , updatable=false)
	private ConcoursPhase concoursPhase;

	@Column(name="centre_concours_id", nullable =false)
	private Integer centreConcoursId;

	@ManyToOne
	@JoinColumn(name = "centre_concours_id",  insertable=false , updatable=false)
	private CentreConcours centreConcours;

	@Column(name="candidat_id", nullable =false)
	private Integer candidatId;

	@ManyToOne
	@JoinColumn(name = "candidat_id", insertable=false , updatable=false)
	private Candidat candidat;

	@Column(name="lccc_remarque", nullable =true)
	private String remarque;


	@Column(name="lccc_actif")
	private Integer actif=1;


	@Column(name="lccc_version")
	private Integer version=1;





	public Integer getListeCentreConcoursCandidatId(){
		return listeCentreConcoursCandidatId;
	}
	public void setListeCentreConcoursCandidatId(Integer listeCentreConcoursCandidatId) { 
		this.listeCentreConcoursCandidatId = listeCentreConcoursCandidatId;
	}




	public Integer getConcoursPhaseId(){
		return concoursPhaseId;
	}
	public void setConcoursPhaseId(Integer concoursPhaseId) { 
		this.concoursPhaseId = concoursPhaseId;
	}
	public ConcoursPhase getConcoursPhase() {
		return concoursPhase;
	}
	public void setConcoursPhase(ConcoursPhase concoursPhase) {
		this.concoursPhase = concoursPhase;
	}




	public Integer getCentreConcoursId(){
		return centreConcoursId;
	}
	public void setCentreConcoursId(Integer centreConcoursId) { 
		this.centreConcoursId = centreConcoursId;
	}
	public CentreConcours getCentreConcours() {
		return centreConcours;
	}
	public void setCentreConcours(CentreConcours centreConcours) {
		this.centreConcours = centreConcours;
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
	

}

