package mg.md2i.gedi.entity;


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
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(editeur) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_editeur")
public class Editeur implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "editeur_id",unique=true, nullable = false)
	private Integer editeurId;

	@Column(name="ed_editeur", nullable =false)
	private String editeur;


	@Column(name="ed_remarque", nullable =false)
	private String remarque;


	@Column(name="ed_actif")
	private Integer actif=1;


	@Column(name="ed_version")
	private Integer version=1;
	
	@Column(name="ed_num_edition",nullable=true)
	private String numeroEdition;
	
	@Column(name="ed_annee",nullable=true)
	private String anneeEdition;
	
	@Column(name="ed_lieu_edition",nullable=true)
	private String lieuEdition;
	
	public String getNumeroEdition() {
	return numeroEdition;
	}
	public void setNumeroEdition(String numeroEdition) {
		this.numeroEdition = numeroEdition;
	}
	public String getAnneeEdition() {
		return anneeEdition;
	}
	public String getLieuEdition() {
		return lieuEdition;
	}
	public void setAnneeEdition(String anneeEdition) {
		this.anneeEdition = anneeEdition;
	}
	public void setLieuEdition(String lieuEdition) {
		this.lieuEdition = lieuEdition;
	}




	public Integer getEditeurId(){
		return editeurId;
	}
	public void setEditeurId(Integer editeurId) { 
		this.editeurId = editeurId;
	}




	public String getEditeur(){
		return editeur;
	}
	public void setEditeur(String editeur) { 
		this.editeur = editeur;
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
		return editeur +" | "+ numeroEdition + " | " + anneeEdition + " | " + StringUtils.defaultString(lieuEdition, "");
	}
	

}

