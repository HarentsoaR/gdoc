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
 * Entite(journalBiblio) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_journal_biblio")
public class JournalBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "journal_biblio_id",unique=true, nullable = false)
	private Integer journalBiblioId;

	@Column(name="jr_date_entree", nullable =false)
	private Date dateEntree;


	@Column(name="type_journal_id", nullable =false)
	private Integer typeJournalId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_journal_id", nullable = false, insertable=false , updatable=false)
	private TypeJournal typeJournal;

	@Column(name="jr_date", nullable =false)
	private Date date;

	@Column(name="jr_titre", nullable =false)
	private String titre;


	@Column(name="jr_auteur", nullable =false)
	private String auteur;


	@Column(name="article_journale_id", nullable =false)
	private Integer articleJournaleId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "article_journale_id", nullable = false, insertable=false , updatable=false)
	private ArticleJournal articleJournale;

	@Column(name="origine_id", nullable =false)
	private Integer origineId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "origine_id", nullable = false, insertable=false , updatable=false)
	private Origine origine;
	
	@Column(name="jr_numero", nullable=false)
	private Integer numero;
	 
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
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

	@Column(name="jr_remarque", nullable =false)
	private String remarque;


	@Column(name="jr_version")
	private Integer version=1;


	@Column(name="jr_actif")
	private Integer actif=1;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = false, insertable=false , updatable=false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;
	
	@Column(name="detail_type_document_biblio_id")
	private Integer detailTypeDocumentBiblioId;
	
	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}
	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}



	public Integer getJournalBiblioId(){
		return journalBiblioId;
	}
	public void setJournalBiblioId(Integer journalBiblioId) { 
		this.journalBiblioId = journalBiblioId;
	}




	public Date getDateEntree(){
		return dateEntree;
	}
	public void setDateEntree(Date dateEntree) { 
		this.dateEntree = dateEntree;
	}


	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
	}




	public String getAuteur(){
		return auteur;
	}
	public void setAuteur(String auteur) { 
		this.auteur = auteur;
	}




	public Integer getTypeJournalId(){
		return typeJournalId;
	}
	public void setTypeJournalId(Integer typeJournalId) { 
		this.typeJournalId = typeJournalId;
	}
	public TypeJournal getTypeJournal() {
		return typeJournal;
	}
	public void setTypeJournal(TypeJournal typeJournal) {
		this.typeJournal = typeJournal;
	}




	public Date getDate(){
		return date;
	}
	public void setDate(Date date) { 
		this.date = date;
	}




	public Integer getArticleJournaleId(){
		return articleJournaleId;
	}
	public void setArticleJournaleId(Integer articleJournaleId) { 
		this.articleJournaleId = articleJournaleId;
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

	public ArticleJournal getArticleJournale() {
		return articleJournale;
	}
	public void setArticleJournale(ArticleJournal articleJournale) {
		this.articleJournale = articleJournale;
	}
	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}
	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	

}
