package mg.md2i.gedi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(documentMouvement) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
// @Audited
@Table(name = "l_document_mouvement")
public class DocumentMouvement implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "document_mouvement_id", unique = true, nullable = false)
	private Integer documentMouvementId;

	@Column(name = "utilisateur_id", nullable = false)
	private Integer utilisateurId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "utilisateur_id", nullable = false, insertable = false, updatable = false)
	private Utilisateur utilisateur;

	@Column(name = "dm_date_mouvement", nullable = false)
	private Date dateMouvement;

	@Column(name = "dm_duree_pret", nullable = false)
	private Integer dureePret;

	@Column(name = "dm_date_retour_prevue", nullable = false)
	private Date dateRetourPrevue;

	@Column(name = "dm_date_retour_reelle", nullable = true)
	private Date dateRetourReelle;

	@Column(name = "dm_responsable_bibliotheque", nullable = false)
	private String responsableBibliotheque;

	@Column(name = "dm_remarque", nullable = false)
	private String remarque;

	@Column(name = "dm_actif")
	private Integer actif = 1;

	@Column(name = "dm_version")
	private Integer version = 1;

	@Column(name = "detail_type_document_biblio_id", nullable = false)
	private Integer detailTypeDocumentBiblioId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = false, insertable = false, updatable = false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;

	public Integer getDocumentMouvementId() {
		return documentMouvementId;
	}

	public void setDocumentMouvementId(Integer documentMouvementId) {
		this.documentMouvementId = documentMouvementId;
	}

	public Integer getUtilisateurId() {
		return utilisateurId;
	}

	public void setUtilisateurId(Integer utilisateurId) {
		this.utilisateurId = utilisateurId;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Date getDateMouvement() {
		return dateMouvement;
	}

	public void setDateMouvement(Date dateMouvement) {
		this.dateMouvement = dateMouvement;
	}

	public Integer getDureePret() {
		return dureePret;
	}

	public void setDureePret(Integer dureePret) {
		this.dureePret = dureePret;
	}

	public Date getDateRetourPrevue() {
		return dateRetourPrevue;
	}

	public void setDateRetourPrevue(Date dateRetourPrevue) {
		this.dateRetourPrevue = dateRetourPrevue;
	}

	public Date getDateRetourReelle() {
		return dateRetourReelle;
	}

	public void setDateRetourReelle(Date dateRetourReelle) {
		this.dateRetourReelle = dateRetourReelle;
	}

	public String getResponsableBibliotheque() {
		return responsableBibliotheque;
	}

	public void setResponsableBibliotheque(String responsableBibliotheque) {
		this.responsableBibliotheque = responsableBibliotheque;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public Integer getActif() {
		return actif;
	}

	public void setActif(Integer actif) {
		this.actif = actif;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	private static final long serialVersionUID = 1L;

	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}

	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}

	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}

	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}

}

