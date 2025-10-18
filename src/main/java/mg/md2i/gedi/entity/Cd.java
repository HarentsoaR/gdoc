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
 * Entite(cd) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_cd")
public class Cd implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cd_id",unique=true, nullable = false)
	private Integer cdId;

	@Column(name="cd_date", nullable =false)
	private Date date;


	@Column(name="cd_num_edition", nullable =false)
	private String numEdition;


	@Column(name="cd_mois", nullable =false)
	private String mois;


	@Column(name="cd_annee", nullable =false)
	private String annee;


	@Column(name="cd_nbr_ex", nullable =false)
	private Integer nbrEx;


	@Column(name="cd_editeur", nullable =false)
	private String editeur;


	@Column(name="cd_titre", nullable =false)
	private String titre;


	@Column(name="cd_code", nullable =false)
	private String code;


	@Column(name="cd_remaque", nullable =false)
	private String remaque;


	@Column(name="cd_version")
	private Integer version=1;


	@Column(name="cd_actif")
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
	public Integer getCdId(){
		return cdId;
	}
	public void setCdId(Integer cdId) { 
		this.cdId = cdId;
	}




	public Date getDate(){
		return date;
	}
	public void setDate(Date date) { 
		this.date = date;
	}




	public String getNumEdition(){
		return numEdition;
	}
	public void setNumEdition(String numEdition) { 
		this.numEdition = numEdition;
	}




	public String getMois(){
		return mois;
	}
	public void setMois(String mois) { 
		this.mois = mois;
	}




	public String getAnnee(){
		return annee;
	}
	public void setAnnee(String annee) { 
		this.annee = annee;
	}




	public Integer getNbrEx(){
		return nbrEx;
	}
	public void setNbrEx(Integer nbrEx) { 
		this.nbrEx = nbrEx;
	}




	public String getEditeur(){
		return editeur;
	}
	public void setEditeur(String editeur) { 
		this.editeur = editeur;
	}




	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
	}




	public String getCode(){
		return code;
	}
	public void setCode(String code) { 
		this.code = code;
	}




	public String getRemaque(){
		return remaque;
	}
	public void setRemaque(String remaque) { 
		this.remaque = remaque;
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
	

}
