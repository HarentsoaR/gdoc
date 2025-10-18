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
 * Entite(encyclopediJurisclasseur) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_encyclopedi_jurisclasseur")
public class EncyclopediJurisclasseur implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "encyclopedi_jurisclasseur_id",unique=true, nullable = false)
	private Integer encyclopediJurisclasseurId;

	@Column(name="type_document_id", nullable =false)
	private Integer typeDocumentId;

	@Column(name="ej_date", nullable =false)
	private Date dateEntree;
	
	@Column(name="ej_numero",nullable=false)
	private Integer numero;
	
	@Column(name="ej_code",nullable=false)
	private String code;
	
	@Column(name="ej_cote",nullable=false)
	private String cote;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_document_id", nullable = false , insertable=false , updatable=false)
	private TypeDocument typeDocument;

	@Column(name="editeur_id", nullable =false)
	private Integer editeurId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "editeur_id", nullable = false, insertable=false , updatable=false)
	private Editeur editeur;
	
	@Column(name="origine_id", nullable =false)
	private Integer origineId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "origine_id", nullable = false, insertable=false , updatable=false)
	private Origine origine;

	
	@Column(name="repertoire_id", nullable =false)
	private Integer repertoireId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "repertoire_id", nullable = false, insertable=false , updatable=false)
	private Repertoire repertoire;

	@Column(name="ej_titre", nullable = false)
	private String titre;


	@Column(name="ej_remarque", nullable =false)
	private String remarque;


	@Column(name="ej_version")
	private Integer version=1;


	@Column(name="ej_actif")
	private Integer actif=1;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = true, insertable=false , updatable=false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;
	
	@Column(name="detail_type_document_biblio_id", nullable = true)
	private Integer detailTypeDocumentBiblioId;

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
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCote() {
		return cote;
	}
	public void setCote(String cote) {
		this.cote = cote;
	}
	
	public Date getDateEntree(){
		return dateEntree;
	}
	public void setDateEntree(Date dateEntree) { 
		this.dateEntree = dateEntree;
	}


	public Integer getEncyclopediJurisclasseurId(){
		return encyclopediJurisclasseurId;
	}
	public void setEncyclopediJurisclasseurId(Integer encyclopediJurisclasseurId) { 
		this.encyclopediJurisclasseurId = encyclopediJurisclasseurId;
	}




	public Integer getTypeDocumentId(){
		return typeDocumentId;
	}
	public void setTypeDocumentId(Integer typeDocumentId) { 
		this.typeDocumentId = typeDocumentId;
	}
	public TypeDocument getTypeDocument() {
		return typeDocument;
	}
	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
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




	public Integer getRepertoireId(){
		return repertoireId;
	}
	public void setRepertoireId(Integer repertoireId) { 
		this.repertoireId = repertoireId;
	}
	public Repertoire getRepertoire() {
		return repertoire;
	}
	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
	}




	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
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
	public String getEncyclopediJurisclasseur() {
		return titre ;
	}
	@Override
	public String toString() {
		return typeDocument + " | " + titre;
	}
	

}

