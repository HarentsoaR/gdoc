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
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(typeDocument) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_type_document")
public class TypeDocument implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3403015809762160166L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "type_document_id",unique=true, nullable = false)
	private Integer typeDocumentId;

	@Column(name="td_libelle", nullable =false)
	private String libelle;


	@Column(name="td_remarque", nullable =false)
	private String remarque;
	
	@Column(name="td_type_cote", nullable =false)
	private String typeCote;

	@Column(name="td_actif")
	private Integer actif;

	@Column(name="td_version")
	private Integer version;
	

	public Integer getTypeDocumentId() {
		return typeDocumentId;
	}

	public void setTypeDocumentId(Integer typeDocumentId) {
		this.typeDocumentId = typeDocumentId;
	}
	

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getRemarque() {
		return remarque;
	}


	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	
	public String getTypeCote() {
		return typeCote;
	}


	public void setTypeCote(String typeCote) {
		this.typeCote = typeCote;
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

	@Override
	public String toString() {
		return libelle;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(actif, libelle, remarque, typeCote,
				typeDocumentId, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeDocument other = (TypeDocument) obj;
		return Objects.equals(actif, other.actif)
				&& Objects.equals(libelle, other.libelle) && Objects.equals(remarque, other.remarque)
				&& Objects.equals(typeCote, other.typeCote) && Objects.equals(typeDocumentId, other.typeDocumentId)
				&& Objects.equals(version, other.version);
	}


}
