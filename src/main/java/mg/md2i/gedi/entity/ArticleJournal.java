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

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(articleJournal) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_article_journal")
public class ArticleJournal implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "article_journal_id",unique=true, nullable = false)
	private Integer articleJournalId;

	@Column(name="aj_titre", nullable =false)
	private String titre;


	@Column(name="aj_auteur", nullable =false)
	private String auteur;


	@Column(name="domaine_biblio_id", nullable =false)
	private Integer domaineBiblioId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "domaine_biblio_id", nullable = false, insertable=false , updatable=false)
	private DomaineBiblio domaineBiblio;

	@Column(name="aj_remarque", nullable =false)
	private String remarque;


	@Column(name="aj_version")
	private Integer version=1;


	@Column(name="aj_actif")
	private Integer actif=1;





	public Integer getArticleJournalId(){
		return articleJournalId;
	}
	public void setArticleJournalId(Integer articleJournalId) { 
		this.articleJournalId = articleJournalId;
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




	public Integer getDomaineBiblioId(){
		return domaineBiblioId;
	}
	public void setDomaineBiblioId(Integer domaineBiblioId) { 
		this.domaineBiblioId = domaineBiblioId;
	}
	public DomaineBiblio getDomaineBiblio() {
		return domaineBiblio;
	}
	public void setDomaineBiblio(DomaineBiblio domaineBiblio) {
		this.domaineBiblio = domaineBiblio;
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

	@Override
	public String toString() {
		return  titre + "|" + auteur+ "|" + domaineBiblio;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result + ((articleJournalId == null) ? 0 : articleJournalId.hashCode());
		result = prime * result + ((auteur == null) ? 0 : auteur.hashCode());
		result = prime * result + ((domaineBiblio == null) ? 0 : domaineBiblio.hashCode());
		result = prime * result + ((domaineBiblioId == null) ? 0 : domaineBiblioId.hashCode());
		result = prime * result + ((remarque == null) ? 0 : remarque.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		ArticleJournal other = (ArticleJournal) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (articleJournalId == null) {
			if (other.articleJournalId != null)
				return false;
		} else if (!articleJournalId.equals(other.articleJournalId))
			return false;
		if (auteur == null) {
			if (other.auteur != null)
				return false;
		} else if (!auteur.equals(other.auteur))
			return false;
		if (domaineBiblio == null) {
			if (other.domaineBiblio != null)
				return false;
		} else if (!domaineBiblio.equals(other.domaineBiblio))
			return false;
		if (domaineBiblioId == null) {
			if (other.domaineBiblioId != null)
				return false;
		} else if (!domaineBiblioId.equals(other.domaineBiblioId))
			return false;
		if (remarque == null) {
			if (other.remarque != null)
				return false;
		} else if (!remarque.equals(other.remarque))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	

}

