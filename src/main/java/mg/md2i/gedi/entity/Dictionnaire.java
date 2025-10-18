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
 * Entite(dictionnaire) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_dictionnaire")
public class Dictionnaire implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dictionnaire_id",unique=true, nullable = false)
	private Integer dictionnaireId;

	@Column(name="dc_date_entre", nullable =false)
	private Date dateEntre;


	@Column(name="dc_num_inventaire", nullable =false)
	private Integer numInventaire;


	@Column(name="dc_cote", nullable =false)
	private String cote;

	@Column(name="dc_titre",nullable=false)
	private String titre;
	
	@Column(name="dc_edition", nullable =false)
	private String edition;


	@Column(name="dc_annee_edition", nullable =false)
	private String anneeEdition;


	@Column(name="dc_nombre_exemplaire", nullable =false)
	private Integer nombreExemplaire;


	@Column(name="dc_remarque", nullable =false)
	private String remarque;


	@Column(name="dc_version")
	private Integer version=1;


	@Column(name="dc_actif")
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

	
	@Column(name="collection_ouvrage_id", nullable =false)
	private Integer collectionOuvrageId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "collection_ouvrage_id", insertable=false , updatable=false)
	private CollectionOuvrage collectionOuvrage;

	
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



	public Integer getDictionnaireId(){
		return dictionnaireId;
	}
	public void setDictionnaireId(Integer dictionnaireId) { 
		this.dictionnaireId = dictionnaireId;
	}




	public Date getDateEntre(){
		return dateEntre;
	}
	public void setDateEntre(Date dateEntre) { 
		this.dateEntre = dateEntre;
	}




	public Integer getNumInventaire(){
		return numInventaire;
	}
	public void setNumInventaire(Integer numInventaire) { 
		this.numInventaire = numInventaire;
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
	public void setTitre(String titre){
		this.titre=titre;
	}


	public String getEdition(){
		return edition;
	}
	public void setEdition(String edition) { 
		this.edition = edition;
	}




	public String getAnneeEdition(){
		return anneeEdition;
	}
	public void setAnneeEdition(String anneeEdition) { 
		this.anneeEdition = anneeEdition;
	}


	public Integer getNombreExemplaire(){
		return nombreExemplaire;
	}
	public void setNombreExemplaire(Integer nombreExemplaire) { 
		this.nombreExemplaire = nombreExemplaire;
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

	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}
	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	
	
	public void setCollectionOuvrageId(Integer collectionOuvrageId) {
		this.collectionOuvrageId = collectionOuvrageId;
	}
	public CollectionOuvrage getCollectionOuvrage() {
		return collectionOuvrage;
	}

}

