package mg.md2i.gedi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entite(fonctionnalite) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="t_fonctionnalite")
public class Fonctionnalite implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fonctionnalite_id",unique=true, nullable = false)
	private Integer fonctionnaliteId;

	@Column(name="fn_libelle", nullable =false)
	private String libelle;


	@Column(name="fn_gauche", nullable =false)
	private Integer gauche;


	@Column(name="fn_droite", nullable =false)
	private Integer droite;


	@Column(name="fn_niveau", nullable =false)
	private Integer niveau;


	@Column(name="fn_actif")
	private Integer actif=1;


	@Column(name="fn_nom_table", nullable =false)
	private String nomTable;


	@Column(name="fn_remarque", nullable =false)
	private String remarque;


	@Column(name="fn_sclass", nullable =true)
	private String sclass;


	@Column(name="fn_version")
	private Integer version=1;





	public Integer getFonctionnaliteId(){
		return fonctionnaliteId;
	}
	public void setFonctionnaliteId(Integer fonctionnaliteId) { 
		this.fonctionnaliteId = fonctionnaliteId;
	}




	public String getLibelle(){
		return libelle;
	}
	public void setLibelle(String libelle) { 
		this.libelle = libelle;
	}




	public Integer getGauche(){
		return gauche;
	}
	public void setGauche(Integer gauche) { 
		this.gauche = gauche;
	}




	public Integer getDroite(){
		return droite;
	}
	public void setDroite(Integer droite) { 
		this.droite = droite;
	}




	public Integer getNiveau(){
		return niveau;
	}
	public void setNiveau(Integer niveau) { 
		this.niveau = niveau;
	}




	public Integer getActif(){
		return actif;
	}
	public void setActif(Integer actif) { 
		this.actif = actif;
	}




	public String getNomTable(){
		return nomTable;
	}
	public void setNomTable(String nomTable) { 
		this.nomTable = nomTable;
	}




	public String getRemarque(){
		return remarque;
	}
	public void setRemarque(String remarque) { 
		this.remarque = remarque;
	}




	public String getSclass(){
		return sclass;
	}
	public void setSclass(String sclass) { 
		this.sclass = sclass;
	}




	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer version) { 
		this.version = version;
	}

	private static final long serialVersionUID = 1L;
	

	@Column(name="fn_controlbar", nullable =false)
	private int controlbar;





	public int getControlbar() {
		return controlbar;
	}
	public void setControlbar(int controlbar) {
		this.controlbar = controlbar;
	}
	
	
	

}
