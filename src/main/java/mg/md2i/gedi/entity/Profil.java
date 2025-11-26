package mg.md2i.gedi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * Entite(profil) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="t_profil")
public class Profil implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "profil_id",unique=true, nullable = false)
	private Integer profilId;

	@Column(name="pl_libelle", nullable =true)
	private String libelle;


	@Column(name="pl_remarque", nullable =true)
	private String remarque;


	@Column(name="pl_version")
	private Integer version=1;


	@Column(name="pl_numero", nullable =true)
	private Double numero;


	@Column(name="pl_actif")
	private Integer actif=1;

	@Column(name="service_id")
	private Integer serviceId;
	
	@Column(name="pl_filiere")
	private String filiere = "TOUS";

	@Column(name="pl_sysid")
	private String sysid;

	@Column(name = "filiere_id")
	private Integer filiereId;

	@ManyToOne
	@JoinColumn(name = "filiere_id", insertable = false, updatable = false)
	private Filiere filiereObj;
	
	@Column(name = "promotion_id")
	private Integer promotionId;

	@ManyToOne
	@JoinColumn(name = "promotion_id", insertable = false, updatable = false)
	private Promotion promotion;
	
	
	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	public Integer getProfilId(){
		return profilId;
	}
	public void setProfilId(Integer profilId) { 
		this.profilId = profilId;
	}




	public Integer getFiliereId() {
		return filiereId;
	}
	public void setFiliereId(Integer filiereId) {
		this.filiereId = filiereId;
	}
	public Filiere getFiliereObj() {
		return filiereObj;
	}
	public void setFiliereObj(Filiere filiereObj) {
		this.filiereObj = filiereObj;
	}
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
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




	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer version) { 
		this.version = version;
	}




	public Double getNumero(){
		return numero;
	}
	public void setNumero(Double numero) { 
		this.numero = numero;
	}




	public Integer getActif(){
		return actif;
	}
	public void setActif(Integer actif) { 
		this.actif = actif;
	}

	private static final long serialVersionUID = 1L;




	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	@PrePersist
	@PreUpdate
	private void ensureDefaults() {
		// pl_filiere is an ENUM in DB (MAGISTRAT, GREFFIER, TOUS); keep a safe default
		if (filiere == null || filiere.trim().isEmpty()) {
			filiere = "TOUS";
		} else {
			String value = filiere.trim().toUpperCase();
			if ("MAGISTRAT".equals(value) || "GREFFIER".equals(value) || "TOUS".equals(value)) {
				filiere = value;
			} else {
				filiere = "TOUS";
			}
		}

		// pl_sysid is VARCHAR(5) in DB; trim and clamp length to avoid truncation errors
		if (sysid != null) {
			String cleaned = sysid.trim().toUpperCase();
			if (cleaned.isEmpty()) {
				sysid = null;
			} else {
				sysid = cleaned.length() > 5 ? cleaned.substring(0, 5) : cleaned;
			}
		}
	}
	
	
}
