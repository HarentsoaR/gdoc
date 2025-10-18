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
 * Entite(document) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author randriamaholimanana1@gmail.com
 */

@Entity
//@Audited
@Table(name="l_document")
public class DocumentBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "document_id",unique=true, nullable = false)
	private Integer documentId;

	
	@Column(name="do_date_entree", nullable =false)
	private Date dateEntree;


	@Column(name="do_numero_inventaire", nullable =false)
	private Integer numeroInventaire;


	@Column(name="do_titre", nullable =false)
	private String titre;


/*	@Column(name="do_code", nullable =false)
	private String code;
*/	

	@Column(name="do_numero_isbn", nullable =false)
	private String numeroIsbn;


	@Column(name="do_prix", nullable =false)
	private String prix;


	@Column(name="origine_id", nullable =false)
	private Integer origineId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "origine_id", nullable = false, insertable=false , updatable=false)
	private Origine origine;
	
	@Column(name="do_date_acquisition", nullable =false)
	private Date dateAcquisition;


	@Column(name="do_nombre_page", nullable =false)
	private Integer nombrePage;


	@Column(name="do_nombre_exemplaire", nullable =false)
	private Integer nombreExemplaire;


	@Column(name="do_resume", nullable =false)
	private String resume;


	@Column(name="editeur_id", nullable =false)
	private Integer editeurId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "editeur_id", nullable = false, insertable=false , updatable=false)
	private Editeur editeur;

	@Column(name="do_remarque", nullable =false)
	private String remarque;


	@Column(name="do_disponibilite", nullable =false)
	private Integer disponibilite;


	@Column(name="do_pretable", nullable =false)
	private Integer pretable;


	@Column(name="do_mot_cle", nullable =false)
	private String motCle;
	
	
	@Column(name="do_cote_ouvrage", nullable =false)
	private String coteOuvrage;	


	@Column(name="do_actif")
	private Integer actif=1;


	@Column(name="do_version")
	private Integer version=1;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = false, insertable=false , updatable=false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;
	
	@Column(name="detail_type_document_biblio_id")
	private Integer detailTypeDocumentBiblioId;

	@Column(name="domaine_ouvrage_id", nullable =false)
	private Integer domaineOuvrageId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "domaine_ouvrage_id", insertable=false , updatable=false)
	private DomaineOuvrage domaineOuvrage;
	
	
	@Column(name="categorie_ouvrage_id", nullable =false)
	private Integer categorieOuvrageId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "categorie_ouvrage_id", insertable=false , updatable=false)
	private CategorieOuvrage categorieOuvrage;


	@Column(name="collection_ouvrage_id", nullable =false)
	private Integer collectionOuvrageId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "collection_ouvrage_id", insertable=false , updatable=false)
	private CollectionOuvrage collectionOuvrage;

	


	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}
	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}
	public Integer getDocumentId(){
		return documentId;
	}
	public void setDocumentId(Integer documentId) { 
		this.documentId = documentId;
	}






	public Date getDateEntree(){
		return dateEntree;
	}
	public void setDateEntree(Date dateEntree) { 
		this.dateEntree = dateEntree;
	}




	public Integer getNumeroInventaire(){
		return numeroInventaire;
	}
	public void setNumeroInventaire(Integer numeroInventaire) { 
		this.numeroInventaire = numeroInventaire;
	}




	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
	}


	
	public void setCoteOuvrage(String coteOuvrage) {
		this.coteOuvrage = coteOuvrage;
	}
	public String getCoteOuvrage() {
		return coteOuvrage;
	}
	


/*	public String getCode(){
		return code;
	}
	public void setCode(String code) { 
		this.code = code;
	}
*/


	public String getNumeroIsbn(){
		return numeroIsbn;
	}
	public void setNumeroIsbn(String numeroIsbn) { 
		this.numeroIsbn = numeroIsbn;
	}




	public String getPrix(){
		return prix;
	}
	public void setPrix(String prix) { 
		this.prix = prix;
	}




//	public String getOrigine(){
//		return origine;
//	}
//	public void setOrigine(String origine) { 
//		this.origine = origine;
//	}




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
	public Date getDateAcquisition(){
		return dateAcquisition;
	}
	public void setDateAcquisition(Date dateAcquisition) { 
		this.dateAcquisition = dateAcquisition;
	}




	public Integer getNombrePage(){
		return nombrePage;
	}
	public void setNombrePage(Integer nombrePage) { 
		this.nombrePage = nombrePage;
	}




	public Integer getNombreExemplaire(){
		return nombreExemplaire;
	}
	public void setNombreExemplaire(Integer nombreExemplaire) { 
		this.nombreExemplaire = nombreExemplaire;
	}


	public String getResume(){
		return resume;
	}
	public void setResume(String resume) { 
		this.resume = resume;
	}


	public Integer getEditeurId(){
		return editeurId;
	}
	public void setEditeurId(Integer editeurId) { 
		this.editeurId = editeurId;
	}
	public Editeur getEditeur() {
		return editeur;
	}
	public void setEditeur(Editeur editeur) {
		this.editeur = editeur;
	}




	public String getRemarque(){
		return remarque;
	}
	public void setRemarque(String remarque) { 
		this.remarque = remarque;
	}




	public Integer getDisponibilite(){
		return disponibilite;
	}
	public void setDisponibilite(Integer disponibilite) { 
		this.disponibilite = disponibilite;
	}




	public Integer getPretable(){
		return pretable;
	}
	public void setPretable(Integer pretable) { 
		this.pretable = pretable;
	}




	public String getMotCle(){
		return motCle;
	}
	public void setMotCle(String motCle) { 
		this.motCle = motCle;
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



	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}
	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	public void setDomaineOuvrageId(Integer domaineOuvrageId) {
		this.domaineOuvrageId = domaineOuvrageId;
		
	}
	public DomaineOuvrage getDomaineOuvrage() {
		return domaineOuvrage;
	}
	public void setDomaineOuvrage(DomaineOuvrage domaineOuvrage) {
		this.domaineOuvrage = domaineOuvrage;
	}
	public Integer getCategorieOuvrageId() {
		return categorieOuvrageId;
	}
	public void setCategorieOuvrageId(Integer categorieOuvrageId) {
		this.categorieOuvrageId = categorieOuvrageId;
	}
	public CategorieOuvrage getCategorieOuvrage() {
		return categorieOuvrage;
	}
	public void setCategorieOuvrage(CategorieOuvrage categorieOuvrage) {
		this.categorieOuvrage = categorieOuvrage;
	}
	public Integer getDomaineOuvrageId() {
		return domaineOuvrageId;
	}
	
	public void setCollectionOuvrageId(Integer collectionOuvrageId) {
		this.collectionOuvrageId = collectionOuvrageId;
	}
	public CollectionOuvrage getCollectionOuvrage() {
		return collectionOuvrage;
	}

}
