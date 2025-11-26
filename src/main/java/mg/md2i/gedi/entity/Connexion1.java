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
 * Entite(connexion1) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="t_connexion1")
public class Connexion1 implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "connexion_id",unique=true, nullable = false)
	private Integer connexionId;

	@Column(name="utilisateur_id", nullable =false)
	private Integer utilisateurId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "utilisateur_id", nullable = false, insertable=false , updatable=false)
	private Utilisateur utilisateur;

	@Column(name="cx_date_debut", nullable =false)
	private Date dateDebut;


	@Column(name="cx_adresse_ip", nullable =false)
	private String adresseIp;


	@Column(name="cx_navigateur", nullable =false)
	private String navigateur;


	@Column(name="cx_ordinateur", nullable =false)
	private String ordinateur;


	@Column(name="cx_mac", nullable =false)
	private String mac;


	@Column(name="cx_version")
	private Integer version=1;


	@Column(name="cx_actif")
	private Integer actif=1;





	public Integer getConnexionId(){
		return connexionId;
	}
	public void setConnexionId(Integer connexionId) { 
		this.connexionId = connexionId;
	}




	public Integer getUtilisateurId(){
		return utilisateurId;
	}
	public void setUtilisateurId(Integer utilisateurId) { 
		this.utilisateurId = utilisateurId;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}




	public Date getDateDebut(){
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) { 
		this.dateDebut = dateDebut;
	}




	public String getAdresseIp(){
		return adresseIp;
	}
	public void setAdresseIp(String adresseIp) { 
		this.adresseIp = adresseIp;
	}




	public String getNavigateur(){
		return navigateur;
	}
	public void setNavigateur(String navigateur) { 
		this.navigateur = navigateur;
	}




	public String getOrdinateur(){
		return ordinateur;
	}
	public void setOrdinateur(String ordinateur) { 
		this.ordinateur = ordinateur;
	}




	public String getMac(){
		return mac;
	}
	public void setMac(String mac) { 
		this.mac = mac;
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
	

}

