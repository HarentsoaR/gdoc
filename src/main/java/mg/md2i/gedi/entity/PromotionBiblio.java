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
 * Entite(promotionBiblio) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_promotion_biblio")
public class PromotionBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "promotion_biblio_id",unique=true, nullable = false)
	private Integer promotionBiblioId;

	@Column(name="po_num", nullable =false)
	private Integer num;


	@Column(name="po_annee", nullable =false)
	private Date annee;


	@Column(name="po_remarque", nullable =false)
	private String remarque;


	@Column(name="po_version")
	private Integer version=1;


	@Column(name="po_actif")
	private Integer actif=1;





	public Integer getPromotionBiblioId(){
		return promotionBiblioId;
	}
	public void setPromotionBiblioId(Integer promotionBiblioId) { 
		this.promotionBiblioId = promotionBiblioId;
	}




	public Integer getNum(){
		return num;
	}
	public void setNum(Integer num) { 
		this.num = num;
	}




	public Date getAnnee(){
		return annee;
	}
	public void setAnnee(Date annee) { 
		this.annee = annee;
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




	public Integer getActif(){
		return actif;
	}
	public void setActif(Integer actif) { 
		this.actif = actif;
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString(){
		return num+"|"+annee;
	}

}

