package mg.md2i.gedi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(origine) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_origine")
public class Origine implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "origine_id",unique=true, nullable = false)
	private Integer origineId;

	@Column(name="a_origine", nullable =false)
	private String origine;


	@Column(name="a_remarque", nullable =false)
	private String remarque;


	@Column(name="a_actif")
	private Integer actif=1;


	@Column(name="a_version")
	private Integer version=1;





	public Integer getOrigineId(){
		return origineId;
	}
	public void setOrigineId(Integer origineId) { 
		this.origineId = origineId;
	}




	public String getOrigine(){
		return origine;
	}
	public void setOrigine(String origine) { 
		this.origine = origine;
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

