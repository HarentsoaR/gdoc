package mg.md2i.gedi.entity;

import mg.md2i.enmg.tools.Utilities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(collectionOuvrage) avec mapping pour les tables de la BDD
 * http://www.md2i.eu 
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
@Table(name="l_collection_ouvrage")
public class CollectionOuvrage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "collection_ouvrage_id",unique=true, nullable = false)
	private Integer collectionOuvrageId;

	@Column(name="cou_nom", nullable =false)
	private String nom;


	@Column(name="cou_actif")
	private Integer actif=1;


	@Column(name="cou_version")
	private Integer version=1;


	@Column(name="cou_remarque")
	private String remarque;

//	@Column(name="cou_code", nullable =false)
//	private String code;

	@Column(name="type_document_biblio_id", nullable =false)
	private Integer typeDocumentBiblioId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_document_biblio_id", insertable=false , updatable=false)
	private TypeDocumentBiblio typeDocumentBiblio;


	public Integer getCollectionOuvrageId(){
		return collectionOuvrageId;
	}
	public void setCollectionOuvrageId(Integer collectionOuvrageId) { 
		this.collectionOuvrageId = collectionOuvrageId;
	}


	public String getNom(){
		return nom;
	}
	public void setNom(String nom) { 
		this.nom = nom;
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


	public String getRemarque(){
		return remarque;
	}
	public void setRemarque(String remarque) { 
		this.remarque = remarque;
	}

	@Override
 	public String toString() {
		return Utilities.createLabel(nom,typeDocumentBiblio.getLibelle());
	}	

	private static final long serialVersionUID = 1L;



/*	public String getCode() {
		
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
*/	
	@Override
	public int hashCode() {
		return Objects.hash(actif, collectionOuvrageId, nom, remarque, version, typeDocumentBiblio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectionOuvrage other = (CollectionOuvrage) obj;
		return Objects.equals(actif, other.actif)
				&& Objects.equals(collectionOuvrageId, other.collectionOuvrageId) && Objects.equals(nom, other.nom)
				&& Objects.equals(remarque, other.remarque) && Objects.equals(version, other.version) 
				&& Objects.equals(typeDocumentBiblio, other.typeDocumentBiblio);
	}
	
	public void setTypeDocumentBiblioId(Integer typeDocumentBiblioId) {
		this.typeDocumentBiblioId = typeDocumentBiblioId;
	}
	public TypeDocumentBiblio getTypeDocumentBiblio() {
		return typeDocumentBiblio;
	}
	
	
	
	

}

