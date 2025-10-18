package mg.md2i.gedi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(rapportStageBiblio) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_rapport_stage_biblio")
public class RapportStageBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rapport_stage_id",unique=true, nullable = false)
	private Integer rapportStageBiblioId;

	@Column(name="filiere_biblio_id", nullable =false)
	private Integer filiereBiblioId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "filiere_biblio_id", nullable = false, insertable=false , updatable=false)
	private FiliereBiblio filiereBiblio;

	@Column(name="promotion_biblio_id", nullable =false)
	private Integer promotionBiblioId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "promotion_biblio_id", nullable = false, insertable=false , updatable=false)
	private PromotionBiblio promotionBiblio;

	@Column(name="rs_cote", nullable =false)
	private String cote;


	@Column(name="rs_titre", nullable =false)
	private String titre;


	@Column(name="rs_nom", nullable =false)
	private String nom;


	@Column(name="rs_remarque", nullable =false)
	private String remarque;

	@Column(name="rs_date_entree", nullable =false)
	private Date dateEntree;
	
	@Column(name="rs_version")
	private Integer version=1;


	@Column(name="rs_actif")
	private Integer actif=1;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = false, insertable=false , updatable=false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;
	
	@Column(name="detail_type_document_biblio_id")
	private Integer detailTypeDocumentBiblioId;
	
	@Column(name="origine_id", nullable =false)
	private Integer origineId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "origine_id", nullable = false, insertable=false , updatable=false)
	private Origine origine;

	
	public Integer getOrigineId() {
		return origineId;
	}
	public void setOrigineId(Integer origineId) {
		this.origineId = origineId;
	}
	public Origine getOrigine() {
		return origine;
	}
	public void setOrigine(Origine origine) {
		this.origine = origine;
	}
	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}
	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}


	public Integer getRapportStageBiblioId(){
		return rapportStageBiblioId;
	}
	public void setRapportStageBiblioId(Integer rapportStageId) { 
		this.rapportStageBiblioId = rapportStageId;
	}




	public Integer getFiliereBiblioId(){
		return filiereBiblioId;
	}
	public void setFiliereBiblioId(Integer filiereBiblioId) { 
		this.filiereBiblioId = filiereBiblioId;
	}
	public FiliereBiblio getFiliereBiblio() {
		return filiereBiblio;
	}
	public void setFiliereBiblio(FiliereBiblio filiereBiblio) {
		this.filiereBiblio = filiereBiblio;
	}




	public Integer getPromotionBiblioId(){
		return promotionBiblioId;
	}
	public void setPromotionBiblioId(Integer promotionBiblioId) { 
		this.promotionBiblioId = promotionBiblioId;
	}
	public PromotionBiblio getPromotionBiblio() {
		return promotionBiblio;
	}
	public void setPromotionBiblio(PromotionBiblio promotionBiblio) {
		this.promotionBiblio = promotionBiblio;
	}




	public String getCote(){
		return cote;
	}
	public void setCote(String cote) { 
		this.cote = cote;
	}




	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
	}




	public String getNom(){
		return nom;
	}
	public void setNom(String nom) { 
		this.nom = nom;
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

	public Date getDateEntree() {
		return dateEntree;
	}
	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}
	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}
	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	

}

