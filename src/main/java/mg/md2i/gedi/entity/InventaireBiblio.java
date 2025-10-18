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
import java.util.Objects;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(inventaireBiblio) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
// @Audited
@Table(name = "l_inventaire_biblio")
public class InventaireBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invetaire_biblio_id", unique = true, nullable = false)
	private Integer inventaireBiblioId;

	@Column(name = "in_ex_init", nullable = false)
	private Integer exInit;

	@Column(name = "in_disponible", nullable = false)
	private Integer disponible;

	@Column(name = "in_manquant", nullable = false)
	private Integer manquant;

	@Column(name = "in_etat_deteriore", nullable = false)
	private Integer etatDeteriore;

	@Column(name = "in_etat_bon", nullable = false)
	private Integer etatBon;

	@Column(name = "in_remarque", nullable = false)
	private String remarque;

	@Column(name = "in_titre", nullable = false)
	private String titre;

	@Column(name = "in_version")
	private Integer version = 1;

	@Column(name = "in_actif")
	private Integer actif = 1;

	@Column(name = "detail_type_document_biblio_id", nullable = true)
	private Integer detailTypeDocumentBiblioId;

	@ManyToOne
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = true, insertable = false, updatable = false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;

	@Column(name = "in_date", nullable = true)
	private Date date;
	

	public Integer getInvetaireBiblioId() {
		return inventaireBiblioId;
	}

	public void setInvetaireBiblioId(Integer invetaireBiblioId) {
		this.inventaireBiblioId = inventaireBiblioId;
	}

	public Integer getExInit() {
		return exInit;
	}

	public void setExInit(Integer exInit) {
		this.exInit = exInit;
	}

	public Integer getDisponible() {
		return disponible;
	}

	public void setDisponible(Integer disponible) {
		this.disponible = disponible;
	}

	public Integer getManquant() {
		return manquant;
	}

	public void setManquant(Integer manquant) {
		this.manquant = manquant;
	}

	public Integer getEtatDeteriore() {
		return etatDeteriore;
	}

	public void setEtatDeteriore(Integer etatDeteriore) {
		this.etatDeteriore = etatDeteriore;
	}

	public Integer getEtatBon() {
		return etatBon;
	}

	public void setEtatBon(Integer etatBon) {
		this.etatBon = etatBon;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getActif() {
		return actif;
	}

	public void setActif(Integer actif) {
		this.actif = actif;
	}

	private static final long serialVersionUID = 1L;

	public Integer getInventaireBiblioId() {
		return inventaireBiblioId;
	}

	public void setInventaireBiblioId(Integer inventaireBiblioId) {
		this.inventaireBiblioId = inventaireBiblioId;
	}

	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}

	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}

	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	

}
