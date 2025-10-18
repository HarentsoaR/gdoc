package mg.md2i.gedi.entity;

import java.io.Serializable;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entite(centreConcours) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_centre_concours")
public class CentreConcours implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "centre_concours_id",unique=true, nullable = false)
	private Integer centreConcoursId;
	
	@ManyToOne
	@JoinColumn(name = "promotion_id",  insertable = false, updatable = false)
	private Promotion promotion;

	@Column(name = "promotion_id", nullable = false)
	private Integer promotionId;
	
	@Column(name="centre_examen_id", nullable =false)
	private Integer centreExamenId;

	@ManyToOne
	@JoinColumn(name = "centre_examen_id", insertable=false , updatable=false)
	private CentreExamen centreExamen;
	
	
	@Column(name="cco_centre", nullable =false)
	private String centre;


	@Column(name="cco_capacite", nullable =false)
	private Integer capacite;


	@Column(name="cco_adresse", nullable =false)
	private String adresse;


	@Column(name="cco_remarque", nullable =false)
	private String remarque;


	@Column(name="cco_actif")
	private Integer actif=1;


	@Column(name="cco_version")
	private Integer version=1;


	@ManyToOne
	@JoinColumn(name = "lieu_concours_id",  insertable = false, updatable = false)
	private LieuConcours lieuConcours;

	@Column(name = "lieu_concours_id", nullable = false)
	private Integer lieuConcoursId;


	@Column(name = "cco_cente_adresse", nullable = true)
	private String centeAdresse;
	
	
	public String getCenteAdresse() {
		return centeAdresse;
	}
	public void setCenteAdresse(String centeAdresse) {
		this.centeAdresse = centeAdresse;
	}
	public LieuConcours getLieuConcours() {
		return lieuConcours;
	}
	public void setLieuConcours(LieuConcours lieuConcours) {
		this.lieuConcours = lieuConcours;
	}
	public Integer getLieuConcoursId() {
		return lieuConcoursId;
	}
	public void setLieuConcoursId(Integer lieuConcoursId) {
		this.lieuConcoursId = lieuConcoursId;
	}
	public Integer getCentreConcoursId(){
		return centreConcoursId;
	}
	public void setCentreConcoursId(Integer centreConcoursId) { 
		this.centreConcoursId = centreConcoursId;
	}




	public Integer getCentreExamenId(){
		return centreExamenId;
	}
	public void setCentreExamenId(Integer centreExamenId) { 
		this.centreExamenId = centreExamenId;
	}
	public CentreExamen getCentreExamen() {
		return centreExamen;
	}
	public void setCentreExamen(CentreExamen centreExamen) {
		this.centreExamen = centreExamen;
	}




	public String getCentre(){
		return centre;
	}
	public void setCentre(String centre) { 
		this.centre = centre;
	}




	public Integer getCapacite(){
		return capacite;
	}
	public void setCapacite(Integer capacite) { 
		this.capacite = capacite;
	}




	public String getAdresse(){
		return adresse;
	}
	public void setAdresse(String adresse) { 
		this.adresse = adresse;
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


	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	@Override
	public String toString() {
		return centre+" - "+adresse;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + ((capacite == null) ? 0 : capacite.hashCode());
		result = prime * result + ((centre == null) ? 0 : centre.hashCode());
		result = prime * result + ((centreConcoursId == null) ? 0 : centreConcoursId.hashCode());
		result = prime * result + ((centreExamenId == null) ? 0 : centreExamenId.hashCode());
		result = prime * result + ((promotionId == null) ? 0 : promotionId.hashCode());
		result = prime * result + ((remarque == null) ? 0 : remarque.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CentreConcours other = (CentreConcours) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (capacite == null) {
			if (other.capacite != null)
				return false;
		} else if (!capacite.equals(other.capacite))
			return false;
		if (centre == null) {
			if (other.centre != null)
				return false;
		} else if (!centre.equals(other.centre))
			return false;
		if (centreConcoursId == null) {
			if (other.centreConcoursId != null)
				return false;
		} else if (!centreConcoursId.equals(other.centreConcoursId))
			return false;
		if (centreExamenId == null) {
			if (other.centreExamenId != null)
				return false;
		} else if (!centreExamenId.equals(other.centreExamenId))
			return false;
		if (promotionId == null) {
			if (other.promotionId != null)
				return false;
		} else if (!promotionId.equals(other.promotionId))
			return false;
		if (remarque == null) {
			if (other.remarque != null)
				return false;
		} else if (!remarque.equals(other.remarque))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	

}

