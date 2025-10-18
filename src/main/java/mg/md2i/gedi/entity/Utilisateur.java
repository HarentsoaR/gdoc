package mg.md2i.gedi.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entite(utilisateur) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author randriamaholimanana1@gmail.com
 */

@Entity
//@Audited
@Table(name="t_utilisateur")
public class Utilisateur implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "utilisateur_id",unique=true, nullable = false)
	private Integer utilisateurId;

	@Column(name="profil_id", nullable =true)
	private Integer profilId;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "profil_id", nullable = false, insertable=false , updatable=false)
	private Profil profil;

	@Column(name="ur_nom", nullable =false)
	private String nom;


	@Column(name="ur_prenom", nullable =true)
	private String prenom;


	@Column(name="ur_adresse", nullable =true)
	private String adresse;


	@Column(name="ur_service", nullable =true)
	private String service;


	@Column(name="ur_login", nullable =false)
	private String login;


	@Column(name="ur_password", nullable =false)
	private String password;


	@Column(name="ur_mail", nullable =false)
	private String mail;


	@Column(name="ur_version")
	private Integer version=1;


	@Column(name="ur_actif")
	private Integer actif=1;


	@Column(name="ur_numero", nullable =false)
	private Double numero;


	@Column(name="ur_fonction", nullable =true)
	private String fonction;


	@Column(name="ur_type", nullable =true)
	private String type;
	
	@Column(name="ur_cle_personne", nullable =true)
	private Integer clePersonne;


	public Integer getUtilisateurId(){
		return utilisateurId;
	}
	public void setUtilisateurId(Integer utilisateurId) { 
		this.utilisateurId = utilisateurId;
	}




	public Integer getProfilId(){
		return profilId;
	}
	public void setProfilId(Integer profilId) { 
		this.profilId = profilId;
	}
	public Profil getProfil() {
		return profil;
	}
	public void setProfil(Profil profil) {
		this.profil = profil;
	}




	public String getNom(){
		return nom;
	}
	public void setNom(String nom) { 
		this.nom = nom;
	}




	public String getPrenom(){
		return prenom;
	}
	public void setPrenom(String prenom) { 
		this.prenom = prenom;
	}




	public String getAdresse(){
		return adresse;
	}
	public void setAdresse(String adresse) { 
		this.adresse = adresse;
	}




	public String getService(){
		return service;
	}
	public void setService(String service) { 
		this.service = service;
	}




	public String getLogin(){
		return login;
	}
	public void setLogin(String login) { 
		this.login = login;
	}




	public String getPassword(){
		return password;
	}
	public void setPassword(String password) { 
		this.password = password;
	}




	public String getMail(){
		return mail;
	}
	public void setMail(String mail) { 
		this.mail = mail;
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




	public Double getNumero(){
		return numero;
	}
	public void setNumero(Double numero) { 
		this.numero = numero;
	}




	public String getFonction(){
		return fonction;
	}
	public void setFonction(String fonction) { 
		this.fonction = fonction;
	}

	private static final long serialVersionUID = 1L;


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getClePersonne() {
		return clePersonne;
	}
	public void setClePersonne(Integer clePersonne) {
		this.clePersonne = clePersonne;
	}
	

}

