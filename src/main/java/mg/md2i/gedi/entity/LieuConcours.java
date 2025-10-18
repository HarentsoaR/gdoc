package mg.md2i.gedi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Version;

import mg.md2i.enmg.tools.Utilities;

import java.io.Serializable;
//import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
//import javax.persistence.CascadeType;

/**
 * Entite(lieuConcours) avec mapping pour les tables de la BDD
 * http://www.md2i.eu 
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
@Table(name="g_lieu_concours")
public class LieuConcours implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lieu_concours_id",unique=true, nullable = false)
	private Integer lieuConcoursId;

	@Column(name="promotion_id", nullable =false)
	private Integer promotionId;

	@ManyToOne
	@JoinColumn(name = "promotion_id",  insertable=false , updatable=false)
	private Promotion promotion;
	
	@Column(name="centre_examen_id", nullable =false)
	private Integer centreExamenId;

	@ManyToOne
	@JoinColumn(name = "centre_examen_id",  insertable=false , updatable=false)
	private CentreExamen centreExamen;

	@Column(name="lco_centre", nullable =false)
	private String centre;


	@Column(name="lco_remarque")
	private String remarque;


	@Column(name="lco_actif")
	private Integer actif=1;


	@Column(name="lco_version")
	private Integer version=1;




	public Integer getLieuConcoursId(){
		return lieuConcoursId;
	}
	public void setLieuConcoursId(Integer lieuConcoursId) { 
		this.lieuConcoursId = lieuConcoursId;
	}


	public Integer getPromotionId(){
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) { 
		this.promotionId = promotionId;
	}
	public Promotion getPromotion() {
		return promotion;
	}
	
	public Integer getCentreExamenId() {
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
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}


	public String getCentre(){
		return centre;
	}
	public void setCentre(String centre) { 
		this.centre = centre;
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
	public String toString() {
		return Utilities.createLabel(centre);
	}

	private static final long serialVersionUID = 1L;

}

