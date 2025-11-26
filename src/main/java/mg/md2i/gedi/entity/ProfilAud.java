package mg.md2i.gedi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Version;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(profilAud) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="t_profil_aud")
@IdClass(ProfilAudId.class)
public class ProfilAud implements Serializable {
	@Id
	@Column(name = "profil_id",unique=true, nullable = false)
	private Integer profilId;

	@Id
	@Column(name = "REV",unique=true, nullable = false)
	private Integer REV;

	@Column(name="REVTYPE", nullable =false)
	private Integer REVTYPE;


	@Column(name="pl_actif")
	private Integer actif=1;


	@Column(name="pl_libelle", nullable =false)
	private String libelle;


	@Column(name="pl_numero", nullable =false)
	private Double numero;


	@Column(name="pl_remarque", nullable =false)
	private String remarque;


	@Column(name="pl_version")
	private Integer version=1;





	public Integer getProfilId(){
		return profilId;
	}
	public void setProfilId(Integer profilId) { 
		this.profilId = profilId;
	}




	public Integer getREV(){
		return REV;
	}
	public void setREV(Integer REV) { 
		this.REV = REV;
	}




	public Integer getREVTYPE(){
		return REVTYPE;
	}
	public void setREVTYPE(Integer REVTYPE) { 
		this.REVTYPE = REVTYPE;
	}




	public Integer getActif(){
		return actif;
	}
	public void setActif(Integer actif) { 
		this.actif = actif;
	}




	public String getLibelle(){
		return libelle;
	}
	public void setLibelle(String libelle) { 
		this.libelle = libelle;
	}




	public Double getNumero(){
		return numero;
	}
	public void setNumero(Double numero) { 
		this.numero = numero;
	}




	public String getRemarque(){
		return remarque;
	}
	public void setRemarque(String remarque) { 
		this.remarque = remarque;
	}




	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer version) { 
		this.version = version;
	}

	private static final long serialVersionUID = 1L;
	

}
