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
 * Entite(detailBulletin) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_detail_bulletin")
public class DetailBulletin implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detail_bulletin_id",unique=true, nullable = false)
	private Integer detailBulletinId;

	@Column(name="db_titre", nullable =false)
	private String titre;


	@Column(name="db_auteur", nullable =false)
	private String auteur;


	@Column(name="db_remarque", nullable =false)
	private String remarque;


	@Column(name="db_version")
	private Integer version=1;


	@Column(name="db_actif")
	private Integer actif=1;





	public Integer getDetailBulletinId(){
		return detailBulletinId;
	}
	public void setDetailBulletinId(Integer detailBulletinId) { 
		this.detailBulletinId = detailBulletinId;
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

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return  titre + "|" + auteur;
	}
}
