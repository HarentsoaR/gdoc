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
 * Entite(candidatInterdit) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_candidat_interdit")
public class CandidatInterdit implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "candidat_interdit_id",unique=true, nullable = false)
	private Integer candidatInterditId;

	@Column(name="concours_id", nullable =false)
	private Integer concoursId;

	@ManyToOne
	@JoinColumn(name = "concours_id",  insertable=false , updatable=false)
	private Concours concours;

	@Column(name="ci_nom", nullable =false)
	private String nom;


	@Column(name="ci_observation", nullable =false)
	private String observation;


	@Column(name="ci_candidat", nullable =true)
	private Integer candidat;
	
	@Column(name="ci_flag", nullable =true)
	private Integer flag;


	@Column(name="ci_actif")
	private Integer actif=1;


	@Column(name="ci_version")
	private Integer version=1;





	public Integer getCandidatInterditId(){
		return candidatInterditId;
	}
	public void setCandidatInterditId(Integer candidatInterditId) { 
		this.candidatInterditId = candidatInterditId;
	}




	public Integer getConcoursId(){
		return concoursId;
	}
	public void setConcoursId(Integer concoursId) { 
		this.concoursId = concoursId;
	}
	public Concours getConcours() {
		return concours;
	}
	public void setConcours(Concours concours) {
		this.concours = concours;
	}




	public String getNom(){
		return nom;
	}
	public void setNom(String nom) { 
		this.nom = nom;
	}




	public String getObservation(){
		return observation;
	}
	public void setObservation(String observation) { 
		this.observation = observation;
	}




	public Integer getCandidat(){
		return candidat;
	}
	public void setCandidat(Integer candidat) { 
		this.candidat = candidat;
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





	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	

}
