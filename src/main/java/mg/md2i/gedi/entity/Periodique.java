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
 * Entite(periodique) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_periodique")
public class Periodique implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "periodique_id",unique=true, nullable = false)
	private Integer periodiqueId;

	@Column(name="pr_nom_periodique", nullable =false)
	private String nomPeriodique;


	@Column(name="pr_num_periodique", nullable =false)
	private String numPeriodique;


	@Column(name="pr_mois", nullable =false)
	private Date mois;


	@Column(name="pr_annee", nullable =false)
	private Date annee;


//	@Column(name="pr_origine", nullable =false)
//	private String origine;


	@Column(name="periodique_detail_id", nullable =false)
	private Integer periodiqueDetailId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "periodique_detail_id", nullable = false, insertable=false , updatable=false)
	private PeriodiqueDetail periodiqueDetail;

	
	
	@Column(name="pr_titre", nullable =false)
	private String titre;
	
	@Column(name="pr_remarque", nullable =false)
	private String remarque;


	@Column(name="pr_version")
	private Integer version=1;


	@Column(name="pr_actif")
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



	public Integer getPeriodiqueId(){
		return periodiqueId;
	}
	public void setPeriodiqueId(Integer periodiqueId) { 
		this.periodiqueId = periodiqueId;
	}




	public String getNomPeriodique(){
		return nomPeriodique;
	}
	public void setNomPeriodique(String nomPeriodique) { 
		this.nomPeriodique = nomPeriodique;
	}




	public String getNumPeriodique(){
		return numPeriodique;
	}
	public void setNumPeriodique(String numPeriodique) { 
		this.numPeriodique = numPeriodique;
	}




	public Date getMois(){
		return mois;
	}
	public void setMois(Date mois) { 
		this.mois = mois;
	}




	public Date getAnnee(){
		return annee;
	}
	public void setAnnee(Date annee) { 
		this.annee = annee;
	}




//	public String getOrigine(){
//		return origine;
//	}
//	public void setOrigine(String origine) { 
//		this.origine = origine;
//	}




	public Integer getPeriodiqueDetailId(){
		return periodiqueDetailId;
	}
	public void setPeriodiqueDetailId(Integer periodiqueDetailId) { 
		this.periodiqueDetailId = periodiqueDetailId;
	}
	public PeriodiqueDetail getPeriodiqueDetail() {
		return periodiqueDetail;
	}
	public void setPeriodiqueDetail(PeriodiqueDetail periodiqueDetail) {
		this.periodiqueDetail = periodiqueDetail;
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

	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}
	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	

}
