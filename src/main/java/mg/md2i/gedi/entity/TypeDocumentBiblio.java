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
 * Entite(typeDocumentBiblio) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
// @Audited
@Table(name = "l_type_document_biblio")
public class TypeDocumentBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "type_document_biblio_id", unique = true, nullable = false)
	private Integer typeDocumentBiblioId;

	@Column(name = "tdb_nom_table", nullable = false)
	private String nomTable;

	@Column(name = "tdb_libelle", nullable = false)
	private String libelle;

	@Column(name = "tdb_actif")
	private Integer actif = 1;

	@Column(name = "tdb_version")
	private Integer version = 1;

	@Column(name = "tdb_remarque", nullable = false)
	private String remarque;
	
	@Column(name = "tdb_typeCollection")
	private boolean typeCollection;

	public Integer getTypeDocumentBiblioId() {
		return typeDocumentBiblioId;
	}

	public void setTypeDocumentBiblioId(Integer typeDocumentBiblioId) {
		this.typeDocumentBiblioId = typeDocumentBiblioId;
	}

	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
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

	@Override
	public String toString() {
		return libelle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((nomTable == null) ? 0 : nomTable.hashCode());
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
		TypeDocumentBiblio other = (TypeDocumentBiblio) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (nomTable == null) {
			if (other.nomTable != null)
				return false;
		} else if (!nomTable.equals(other.nomTable))
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

}
