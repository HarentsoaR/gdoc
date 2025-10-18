package mg.md2i.gedi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entite(documentConcours) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_document_concours")
public class DocumentConcours implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "document_concours_id",unique=true, nullable = false)
	private Integer documentConcoursId;

	@Column(name="do_libelle", nullable =false)
	private String libelle;


	@Column(name="do_remarque", nullable =false)
	private String remarque;


	@Column(name="do_actif")
	private Integer actif=1;


	@Column(name="do_version")
	private Integer version=1;





	public Integer getDocumentConcoursId(){
		return documentConcoursId;
	}
	public void setDocumentConcoursId(Integer documentConcoursId) { 
		this.documentConcoursId = documentConcoursId;
	}




	public String getLibelle(){
		return libelle;
	}
	public void setLibelle(String libelle) { 
		this.libelle = libelle;
	}




	public String getRemarque(){
		return remarque;
	}
	public void setRemarque(String remarque) { 
		this.remarque = remarque;
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

	@Override
	public String toString() {
		return  libelle;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result + ((documentConcoursId == null) ? 0 : documentConcoursId.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((remarque == null) ? 0 : remarque.hashCode());
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
		DocumentConcours other = (DocumentConcours) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (documentConcoursId == null) {
			if (other.documentConcoursId != null)
				return false;
		} else if (!documentConcoursId.equals(other.documentConcoursId))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equalsIgnoreCase(other.libelle))
			return false;
		if (remarque == null) {
			if (other.remarque != null)
				return false;
		} else if (!remarque.equals(other.remarque))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}

