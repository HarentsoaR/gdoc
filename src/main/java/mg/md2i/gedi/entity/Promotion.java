package mg.md2i.gedi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entite(promotion) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="a_promotion")
public class Promotion implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "promotion_id",unique=true, nullable = false)
	private Integer promotionId;

	@Column(name="filiere_id", nullable =false)
	private Integer filiereId;

	@ManyToOne
	@JoinColumn(name = "filiere_id",  insertable=false , updatable=false)
	private Filiere filiere;

	@Column(name="p_numero_promotion", nullable =false)
	private String numeroPromotion;
	
	@Column(name="p_date_debut", nullable =true)
	private Date dateDebut;


	@Column(name="p_date_fin", nullable =true)
	private Date dateFin;


	@Column(name="p_libelle", nullable =true)
	private String libelle;
	
	@Column(name="p_annee_concours", nullable =false)
	private Integer anneeConcours;


	@Column(name="p_remarque", nullable =true)
	private String remarque;


	@Column(name="p_actif")
	private Integer actif=1;


	@Column(name="p_version")
	private Integer version=1;



	public Integer getPromotionId(){
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) { 
		this.promotionId = promotionId;
	}

	public Integer getFiliereId(){
		return filiereId;
	}
	public void setFiliereId(Integer filiereId) { 
		this.filiereId = filiereId;
	}
	public Filiere getFiliere() {
		return filiere;
	}
	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
	}


	public Date getDateDebut(){
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) { 
		this.dateDebut = dateDebut;
	}


	public Date getDateFin(){
		return dateFin;
	}
	public void setDateFin(Date dateFin) { 
		this.dateFin = dateFin;
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

	private static final long serialVersionUID = 1L;
	@Override
	public String toString(){
		return numeroPromotion +" EME promotion ";
	}
	
	public Integer getAnneeConcours() {
		return anneeConcours;
	}
	public void setAnneeConcours(Integer anneeConcours) {
		this.anneeConcours = anneeConcours;
	}
	public String getNumeroPromotion() {
		return numeroPromotion;
	}
	public void setNumeroPromotion(String numeroPromotion) {
		this.numeroPromotion = numeroPromotion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result
				+ ((filiereId == null) ? 0 : filiereId.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result
				+ ((promotionId == null) ? 0 : promotionId.hashCode());
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
		Promotion other = (Promotion) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (filiereId == null) {
			if (other.filiereId != null)
				return false;
		} else if (!filiereId.equals(other.filiereId))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (promotionId == null) {
			if (other.promotionId != null)
				return false;
		} else if (!promotionId.equals(other.promotionId))
			return false;
		return true;
	}

	
	
}
