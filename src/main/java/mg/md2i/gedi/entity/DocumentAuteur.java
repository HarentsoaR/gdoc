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
 * Entite(documentAuteur) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name = "l_document_auteur")
public class DocumentAuteur implements Serializable {
	@Override
	public String toString() {
		return nomAuteur;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "document_auteur_id", unique = true, nullable = false)
	private Integer documentAuteurId;

	/*
	 * @Column(name="document_id", nullable =false) private Integer documentId;
	 * 
	 * @ManyToOne(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "document_id", nullable = false, insertable=false ,
	 * updatable=false) private Document document;
	 */

	@Column(name = "da_nom_auteur", nullable = false)
	private String nomAuteur;

	@Column(name = "da_remarque", nullable = false)
	private String remarque;

	@Column(name = "da_actif")
	private Integer actif = 1;

	@Column(name = "da_version")
	private Integer version = 1;

	public Integer getDocumentAuteurId() {
		return documentAuteurId;
	}

	public void setDocumentAuteurId(Integer documentAuteurId) {
		this.documentAuteurId = documentAuteurId;
	}

	/*
	 * public Integer getDocumentId(){ return documentId; } public void
	 * setDocumentId(Integer documentId) { this.documentId = documentId; } public
	 * Document getDocument() { return document; } public void setDocument(Document
	 * document) { this.document = document; }
	 */

	public String getNomAuteur() {
		return nomAuteur;
	}

	public void setNomAuteur(String nomAuteur) {
		this.nomAuteur = nomAuteur;
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

	@Override
	public int hashCode() {
		return Objects.hash(actif, documentAuteurId, nomAuteur, remarque, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentAuteur other = (DocumentAuteur) obj;
		return Objects.equals(actif, other.actif) && Objects.equals(documentAuteurId, other.documentAuteurId)
				&& Objects.equals(nomAuteur, other.nomAuteur) && Objects.equals(remarque, other.remarque)
				&& Objects.equals(version, other.version);
	}

}

