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

import mg.md2i.enmg.tools.ConvertDate;

/**
 * Entite(journalOfficiel) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_journal_officiel")
public class JournalOfficiel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "jo_journal_officiel_id",unique=true, nullable = false)
	private Integer journalOfficielId;

	@Column(name="jo_numero_ordre", nullable =false)
	private Integer numeroOrdre;


	@Column(name="jo_numero", nullable =false)
	private Integer numero;


	@Column(name="jo_date", nullable =false)
	private Date date;

	@Column(name="jo_date_entree", nullable =false)
	private Date dateEntree;
	
	@Column(name="type_journal_officiel_id", nullable =false)
	private Integer typeJournalOfficielId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_journal_officiel_id", nullable = false, insertable=false , updatable=false)
	private TypeJournalOfficiel typeJournalOfficiel;

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

	@Column(name="jo_type_texte", nullable =false)
	private String typeTexte;
	
	@Column(name="jo_remarque", nullable =false)
	private String remarque;


	@Column(name="jo_version")
	private Integer version=1;


	@Column(name="jo_actif")
	private Integer actif=1;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = true, insertable=false , updatable=false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;
	
	@Column(name="detail_type_document_biblio_id",nullable = true)
	private Integer detailTypeDocumentBiblioId;
	
	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}
	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}



	public Integer getJournalOfficielId(){
		return journalOfficielId;
	}
	public void setJournalOfficielId(Integer joJournalOfficielId) { 
		this.journalOfficielId = joJournalOfficielId;
	}




	public Integer getNumeroOrdre(){
		return numeroOrdre;
	}
	public void setNumeroOrdre(Integer numeroOrdre) { 
		this.numeroOrdre = numeroOrdre;
	}




	public Integer getNumero(){
		return numero;
	}
	public void setNumero(Integer numero) { 
		this.numero = numero;
	}




	public Date getDate(){
		return date;
	}
	public void setDate(Date date) { 
		this.date = date;
	}




	public Integer getTypeJournalOfficielId(){
		return typeJournalOfficielId;
	}
	public void setTypeJournalOfficielId(Integer typeJournalOfficielId) { 
		this.typeJournalOfficielId = typeJournalOfficielId;
	}
	public TypeJournalOfficiel getTypeJournalOfficiel() {
		return typeJournalOfficiel;
	}
	public void setTypeJournalOfficiel(TypeJournalOfficiel typeJournalOfficiel) {
		this.typeJournalOfficiel = typeJournalOfficiel;
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
	public String toString() {
		return "Journal Officiel du " + ConvertDate.getDateFormatter(date) + " numero " + numero + " " + typeJournalOfficiel.getTitre();
	}
	public Date getDateEntree() {
		return dateEntree;
	}
	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}
	public String getTypeTexte() {
		return typeTexte;
	}
	public void setTypeTexte(String typeTexte) {
		this.typeTexte = typeTexte;
	}
	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}
	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	

}
