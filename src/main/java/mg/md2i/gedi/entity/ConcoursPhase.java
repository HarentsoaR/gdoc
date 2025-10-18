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
 * Entite(concoursPhase) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_concours_phase")
public class ConcoursPhase implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "concours_phase_id",unique=true, nullable = false)
	private Integer concoursPhaseId;

	@Column(name="concours_id", nullable =false)
	private Integer concoursId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "concours_id", nullable = false, insertable=false , updatable=false)
	private Concours concours;

	@Column(name="cp_numero", nullable =true)
	private Integer numero;


	@Column(name="cp_libelle", nullable =false)
	private String libelle;


	@Column(name="cp_remarque", nullable =false)
	private String remarque;


	@Column(name="cp_actif")
	private Integer actif=1;


	@Column(name="cp_version")
	private Integer version=1;





	public Integer getConcoursPhaseId(){
		return concoursPhaseId;
	}
	public void setConcoursPhaseId(Integer concoursPhaseId) { 
		this.concoursPhaseId = concoursPhaseId;
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




	public Integer getNumero(){
		return numero;
	}
	public void setNumero(Integer numero) { 
		this.numero = numero;
	}




	public String getLibelle(){
		return libelle;
	}
	public void setLibelle(String libelle) { 
		this.libelle = libelle;
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
	@Override
	public String toString(){
		return libelle+" "+numero;
	}
	private static final long serialVersionUID = 1L;
	

}
