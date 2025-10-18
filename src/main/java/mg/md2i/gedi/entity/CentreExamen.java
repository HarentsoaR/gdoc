package mg.md2i.gedi.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entite(centreExamen) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_centre_examen")
public class CentreExamen implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "centre_examen_id",unique=true, nullable = false)
	private Integer centreExamenId;

	@Column(name="ce_code", nullable =false)
	private String code;


	@Column(name="ce_libelle", nullable =false)
	private String libelle;


	@Column(name="ce_version")
	private Integer version=1;


	@Column(name="ce_actif")
	private Integer actif=1;





	public Integer getCentreExamenId(){
		return centreExamenId;
	}
	public void setCentreExamenId(Integer centreExamenId) { 
		this.centreExamenId = centreExamenId;
	}




	public String getCode(){
		return code;
	}
	public void setCode(String code) { 
		this.code = code;
	}




	public String getLibelle(){
		return libelle;
	}
	public void setLibelle(String libelle) { 
		this.libelle = libelle;
	}




	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer version) { 
		this.version = version;
	}




	public Integer getActif(){
		return actif;
	}
	public void setActif(Integer actif) { 
		this.actif = actif;
	}

	private static final long serialVersionUID = 1L;





	@Override
	public String toString() {
		return code+"|"+libelle;
	}
	

}

