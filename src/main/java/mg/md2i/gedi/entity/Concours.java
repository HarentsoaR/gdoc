package mg.md2i.gedi.entity;

import java.io.Serializable;
import java.util.Date;

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
 * Entite(concours) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name = "g_concours")
public class Concours implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "co_actif")
	private Integer actif = 1;

	@Column(name = "co_avis_concours", nullable = false)
	private String avisConcours;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "concours_id", unique = true, nullable = false)
	private Integer concoursId;

	@Column(name = "co_date_arrete", nullable = false)
	private Date dateArrete;

	@Column(name = "co_date_debut_limite_age", nullable = false)
	private Date dateDebutLimiteAge;

	@Column(name = "co_date_fin_limite_age", nullable = false)
	private Date dateFinLimiteAge;

	@Column(name = "co_nombre_poste", nullable = false)
	private Integer nombrePoste;

	@Column(name = "co_numero_arrete", nullable = false)
	private String numeroArrete;

	@ManyToOne
	@JoinColumn(name = "promotion_id", insertable = false, updatable = false)
	private Promotion promotion;

	@Column(name = "promotion_id", nullable = false)
	private Integer promotionId;

	@Column(name = "co_remarque", nullable = true)
	private String remarque;

	@Column(name="co_statut", nullable =true)
	private boolean statut;
	
	@Column(name="co_list_plublier", nullable=true)
	private boolean listePublier;
	
	@Column(name = "co_version")
	private Integer version = 1;
	

	public Integer getActif() {
		return actif;
	}

	public String getAvisConcours() {
		return avisConcours;
	}

	public Integer getConcoursId() {
		return concoursId;
	}

	public Date getDateArrete() {
		return dateArrete;
	}

	public Date getDateDebutLimiteAge() {
		return dateDebutLimiteAge;
	}

	public Date getDateFinLimiteAge() {
		return dateFinLimiteAge;
	}

	public Integer getNombrePoste() {
		return nombrePoste;
	}

	public String getNumeroArrete() {
		return numeroArrete;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public String getRemarque() {
		return remarque;
	}

	public Integer getVersion() {
		return version;
	}

	public void setActif(Integer actif) {
		this.actif = actif;
	}

	public void setAvisConcours(String avisConcours) {
		this.avisConcours = avisConcours;
	}

	public void setConcoursId(Integer concoursId) {
		this.concoursId = concoursId;
	}

	public void setDateArrete(Date dateArrete) {
		this.dateArrete = dateArrete;
	}

	public void setDateDebutLimiteAge(Date dateDebutLimiteAge) {
		this.dateDebutLimiteAge = dateDebutLimiteAge;
	}

	public void setDateFinLimiteAge(Date dateFinLimiteAge) {
		this.dateFinLimiteAge = dateFinLimiteAge;
	}

	public void setNombrePoste(Integer nombrePoste) {
		this.nombrePoste = nombrePoste;
	}

	public void setNumeroArrete(String numeroArrete) {
		this.numeroArrete = numeroArrete;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	public boolean getListePublier() {
		return listePublier;
	}

	public void setListePublier(boolean listePublier) {
		this.listePublier = listePublier;
	}

	@Override
	public String toString() {
		return promotion.getFiliere().getCode() + "|" + numeroArrete + "|" + avisConcours;
	}

	public boolean getStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result + ((avisConcours == null) ? 0 : avisConcours.hashCode());
		result = prime * result + ((concoursId == null) ? 0 : concoursId.hashCode());
		result = prime * result + ((dateArrete == null) ? 0 : dateArrete.hashCode());
		result = prime * result + ((dateDebutLimiteAge == null) ? 0 : dateDebutLimiteAge.hashCode());
		result = prime * result + ((dateFinLimiteAge == null) ? 0 : dateFinLimiteAge.hashCode());
		result = prime * result + ((nombrePoste == null) ? 0 : nombrePoste.hashCode());
		result = prime * result + ((numeroArrete == null) ? 0 : numeroArrete.hashCode());
		result = prime * result + ((promotionId == null) ? 0 : promotionId.hashCode());
		result = prime * result + ((remarque == null) ? 0 : remarque.hashCode());
//		result = prime * result + ((statut == null) ? 0 : statut.hashCode());
//		result = prime * result + ((listePublier == null) ? 0 : listePublier.hashCode());
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
		Concours other = (Concours) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (avisConcours == null) {
			if (other.avisConcours != null)
				return false;
		} else if (!avisConcours.equals(other.avisConcours))
			return false;
		if (concoursId == null) {
			if (other.concoursId != null)
				return false;
		} else if (!concoursId.equals(other.concoursId))
			return false;
		if (dateArrete == null) {
			if (other.dateArrete != null)
				return false;
		} else if (!dateArrete.equals(other.dateArrete))
			return false;
		if (dateDebutLimiteAge == null) {
			if (other.dateDebutLimiteAge != null)
				return false;
		} else if (!dateDebutLimiteAge.equals(other.dateDebutLimiteAge))
			return false;
		if (dateFinLimiteAge == null) {
			if (other.dateFinLimiteAge != null)
				return false;
		} else if (!dateFinLimiteAge.equals(other.dateFinLimiteAge))
			return false;
		if (nombrePoste == null) {
			if (other.nombrePoste != null)
				return false;
		} else if (!nombrePoste.equals(other.nombrePoste))
			return false;
		if (numeroArrete == null) {
			if (other.numeroArrete != null)
				return false;
		} else if (!numeroArrete.equals(other.numeroArrete))
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
//		if (statut == null) {
//			if (other.statut != null)
//				return false;
//		} else if (!statut.equals(other.statut))
//			return false;
//		if (statut == null) {
//			if (other.listePublier != null)
//				return false;
//		} else if (!statut.equals(other.listePublier))
//			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
}

