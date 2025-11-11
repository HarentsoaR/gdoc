package mg.md2i.gedi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entite(service) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="t_service")
public class Services implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "service_id",unique=true, nullable = false)
	private Integer serviceId;

	@Column(name="sv_code", nullable =true)
	private String code;


	@Column(name="sv_libelle", nullable =false)
	private String libelle;


	@Column(name="sv_remarque", nullable =true)
	private String remarque;


	@Column(name="sv_actif")
	private Integer actif=1;


	@Column(name="sv_action", nullable =false)
	private Integer action;


	@Column(name="sv_droite", nullable =false)
	private Integer droite;


	@Column(name="sv_gauche", nullable =false)
	private Integer gauche;


	@Column(name="sv_image", nullable =true)
	private String image;


	@Column(name="sv_lien", nullable =true)
	private String lien;


	@Column(name="sv_module", nullable =true)
	private Integer module;


	@Column(name="sv_niveau", nullable =false)
	private Integer niveau;


	@Column(name="sv_ordre", nullable =true)
	private Integer ordre;


	@Column(name="sv_sclass", nullable =true)
	private String sclass;


	@Column(name="sv_version")
	private Integer version=1;





	public Integer getServiceId(){
		return serviceId;
	}
	public void setServiceId(Integer serviceId) { 
		this.serviceId = serviceId;
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




	public Integer getAction(){
		return action;
	}
	public void setAction(Integer action) { 
		this.action = action;
	}




	public Integer getDroite(){
		return droite;
	}
	public void setDroite(Integer droite) { 
		this.droite = droite;
	}




	public Integer getGauche(){
		return gauche;
	}
	public void setGauche(Integer gauche) { 
		this.gauche = gauche;
	}




	public String getImage(){
		return image;
	}
	public void setImage(String image) { 
		this.image = image;
	}




	public String getLien(){
		return lien;
	}
	public void setLien(String lien) { 
		this.lien = lien;
	}




	public Integer getModule(){
		return module;
	}
	public void setModule(Integer module) { 
		this.module = module;
	}




	public Integer getNiveau(){
		return niveau;
	}
	public void setNiveau(Integer niveau) { 
		this.niveau = niveau;
	}




	public Integer getOrdre(){
		return ordre;
	}
	public void setOrdre(Integer ordre) { 
		this.ordre = ordre;
	}




	public String getSclass(){
		return sclass;
	}
	public void setSclass(String sclass) { 
		this.sclass = sclass;
	}




	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer version) { 
		this.version = version;
	}

	private static final long serialVersionUID = 1L;





	@Override
	public String toString() {
		return code + "  " + libelle;
	}
	

}
