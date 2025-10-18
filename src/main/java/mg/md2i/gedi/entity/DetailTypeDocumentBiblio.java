package mg.md2i.gedi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * Entite(detailTypeDocumentBiblio) avec mapping pour les tables de la BDD pour
 * l'ENMG http://www.md2i.eu en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
// @Audited
@Table(name = "l_detail_type_document_biblio")
public class DetailTypeDocumentBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detail_type_document_biblio_id", unique = true, nullable = false)
	private Integer detailTypeDocumentBiblioId;

	@Column(name = "type_document_biblio_id", nullable = false)
	private Integer typeDocumentBiblioId;

	@ManyToOne
	@JoinColumn(name = "type_document_biblio_id", nullable = false, insertable = false, updatable = false)
	private TypeDocumentBiblio typeDocumentBiblio;

	@Column(name = "document_id", nullable = false)
	private Integer documentId;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private DocumentBiblio document;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private Cd cd;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private Dictionnaire dictionnaire;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private BulletinInformation bulletinInformation;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private EncyclopediJurisclasseur encyclopediJurisclasseur;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private JournalOfficiel journalOfficiel;
	
	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private JournalBiblio journalBiblio;	

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private Periodique periodique;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private RapportStage rapportStage;

	@OneToOne(mappedBy = "detailTypeDocumentBiblio", fetch = FetchType.LAZY)
	private RapportStageBiblio rapportStageBiblio;

	@Column(name = "dtdb_nombre_exemplaire", nullable = false)
	private Integer nombreExemplaire;

	@Column(name = "dtdb_disponibilite", nullable = false)
	private Integer disponibilite;

	@Column(name = "dtdb_pretable", nullable = false)
	private Integer pretable;

	@Column(name = "dtdb_actif")
	private Integer actif = 1;

	@Column(name = "dtdb_version")
	private Integer version = 1;	

	@Column(name = "dtdb_remarque", nullable = false)
	private String remarque;

	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}

	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}

	public Integer getTypeDocumentBiblioId() {
		return typeDocumentBiblioId;
	}

	public void setTypeDocumentBiblioId(Integer typeDocumentBiblioId) {
		this.typeDocumentBiblioId = typeDocumentBiblioId;
	}

	public TypeDocumentBiblio getTypeDocumentBiblio() {
		return typeDocumentBiblio;
	}

	public void setTypeDocumentBiblio(TypeDocumentBiblio typeDocumentBiblio) {
		this.typeDocumentBiblio = typeDocumentBiblio;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

//	public Document getDocument() {
//		return document;
//	}
//
//	public void setDocument(Document document) {
//		this.document = document;
//		
//	}

	public Integer getNombreExemplaire() {
		return nombreExemplaire;
	}

	public void setNombreExemplaire(Integer nombreExemplaire) {
		this.nombreExemplaire = nombreExemplaire;
	}

	public Integer getDisponibilite() {
		return disponibilite;
	}

	public void setDisponibilite(Integer disponibilite) {
		this.disponibilite = disponibilite;
	}

	public Integer getPretable() {
		return pretable;
	}

	public void setPretable(Integer pretable) {
		this.pretable = pretable;
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

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	private static final long serialVersionUID = 1L;

	public BulletinInformation getBulletinInformation() {
		return bulletinInformation;
	}

	public void setBulletinInformation(BulletinInformation bulletinInformation) {
		this.bulletinInformation = bulletinInformation;
	}

	public Cd getCd() {
		return cd;
	}

	public void setCd(Cd cd) {
		this.cd = cd;
	}

	public Dictionnaire getDictionnaire() {
		return dictionnaire;
	}

	public void setDictionnaire(Dictionnaire dictionnaire) {
		this.dictionnaire = dictionnaire;
	}

	public EncyclopediJurisclasseur getEncyclopediJurisclasseur() {
		return encyclopediJurisclasseur;
	}

	public void setEncyclopediJurisclasseur(EncyclopediJurisclasseur encyclopediJurisclasseur) {
		this.encyclopediJurisclasseur = encyclopediJurisclasseur;
	}

	public JournalOfficiel getJournalOfficiel() {
		return journalOfficiel;
	}

	public void setJournalOfficiel(JournalOfficiel journalOfficiel) {
		this.journalOfficiel = journalOfficiel;
	}

	public Periodique getPeriodique() {
		return periodique;
	}

	public void setPeriodique(Periodique periodique) {
		this.periodique = periodique;
	}

	public RapportStageBiblio getRapportStageBiblio() {
		return rapportStageBiblio;
	}

	public void setRapportStageBiblio(RapportStageBiblio rapportStageBiblio) {
		this.rapportStageBiblio = rapportStageBiblio;
	}

	public RapportStage getRapportStage() {
		return rapportStage;
	}

	public void setRapportStage(RapportStage rapportStage) {
		this.rapportStage = rapportStage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result + ((detailTypeDocumentBiblioId == null) ? 0 : detailTypeDocumentBiblioId.hashCode());
		result = prime * result + ((disponibilite == null) ? 0 : disponibilite.hashCode());
		result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
		result = prime * result + ((nombreExemplaire == null) ? 0 : nombreExemplaire.hashCode());
		result = prime * result + ((pretable == null) ? 0 : pretable.hashCode());
		result = prime * result + ((remarque == null) ? 0 : remarque.hashCode());
		result = prime * result + ((typeDocumentBiblioId == null) ? 0 : typeDocumentBiblioId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetailTypeDocumentBiblio other = (DetailTypeDocumentBiblio) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (detailTypeDocumentBiblioId == null) {
			if (other.detailTypeDocumentBiblioId != null)
				return false;
		} else if (!detailTypeDocumentBiblioId.equals(other.detailTypeDocumentBiblioId))
			return false;
		if (disponibilite == null) {
			if (other.disponibilite != null)
				return false;
		} else if (!disponibilite.equals(other.disponibilite))
			return false;
		if (documentId == null) {
			if (other.documentId != null)
				return false;
		} else if (!documentId.equals(other.documentId))
			return false;
		if (nombreExemplaire == null) {
			if (other.nombreExemplaire != null)
				return false;
		} else if (!nombreExemplaire.equals(other.nombreExemplaire))
			return false;
		if (pretable == null) {
			if (other.pretable != null)
				return false;
		} else if (!pretable.equals(other.pretable))
			return false;
		if (remarque == null) {
			if (other.remarque != null)
				return false;
		} else if (!remarque.equals(other.remarque))
			return false;
		if (typeDocumentBiblioId == null) {
			if (other.typeDocumentBiblioId != null)
				return false;
		} else if (!typeDocumentBiblioId.equals(other.typeDocumentBiblioId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public JournalBiblio getJournalBiblio() {
		return journalBiblio;
	}

	public void setJournalBiblio(JournalBiblio journalBiblio) {
		this.journalBiblio = journalBiblio;
	}

}
