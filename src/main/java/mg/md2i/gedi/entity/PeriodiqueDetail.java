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
 * Entite(periodiqueDetail) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_periodique_detail")
public class PeriodiqueDetail implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "periodique_detail_id",unique=true, nullable = false)
	private Integer periodiqueDetailId;

	@Column(name="prd_auteur", nullable =false)
	private String auteur;


	@Column(name="prd_page", nullable =false)
	private Integer page;


	@Column(name="prd_remarque", nullable =false)
	private String remarque;


	@Column(name="prd_version")
	private Integer version=1;


	@Column(name="prd_actif")
	private Integer actif=1;





	public Integer getPeriodiqueDetailId(){
		return periodiqueDetailId;
	}
	public void setPeriodiqueDetailId(Integer periodiqueDetailId) { 
		this.periodiqueDetailId = periodiqueDetailId;
	}




	public String getAuteur(){
		return auteur;
	}
	public void setAuteur(String auteur) { 
		this.auteur = auteur;
	}




	public Integer getPage(){
		return page;
	}
	public void setPage(Integer page) { 
		this.page = page;
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
	public String toString(){
		return auteur+"|"+page;
	}

}
