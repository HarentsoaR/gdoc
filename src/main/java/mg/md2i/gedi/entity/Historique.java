package mg.md2i.gedi.entity;


import mg.md2i.enmg.tools.Utilities;
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
 * Entite(historique) avec mapping pour les tables de la BDD
 * http://www.md2i.eu 
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
@Table(name="t_historique")
public class Historique implements Serializable {
	public Historique() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "historique_id",unique=true, nullable = false)
	private Integer historiqueId;

	@Column(name="connexion_id", nullable =false)
	private Integer connexionId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "connexion_id", nullable = false, insertable=false , updatable=false)
	private Connexion1 connexion;

	@Column(name="ht_date", nullable =false)
	private Date date;


	@Column(name="ht_code_avant")
	private String codeAvant;


	@Column(name="ht_code_apres")
	private String codeApres;


	@Column(name="ht_avant")
	private String avant;


	@Column(name="ht_apres")
	private String apres;


	@Column(name="ht_table", nullable =false)
	private String table;


	@Column(name="ht_lien")
	private String lien;


	@Column(name="ht_ordinateur")
	private String ordinateur;


	@Column(name="ht_navigateur")
	private String navigateur;


	@Column(name="ht_adresse_ip")
	private String adresseIp;


	@Column(name="ht_active")
	private Integer active;


	@Column(name="ht_version")
	private Integer version=1;


	@Column(name="ht_operation", nullable =false)
	private String operation;

	public Integer getHistoriqueId(){
		return historiqueId;
	}
	public void setHistoriqueId(Integer historiqueId) { 
		this.historiqueId = historiqueId;
	}


	public Integer getConnexionId(){
		return connexionId;
	}
	public void setConnexionId(Integer connexionId) { 
		this.connexionId = connexionId;
	}
	public Connexion1 getConnexion1() {
		return connexion;
	}
	public void setConnexion1(Connexion1 connexion) {
		this.connexion = connexion;
	}


	public Date getDate(){
		return date;
	}
	public void setDate(Date date) { 
		this.date = date;
	}


	public String getCodeAvant(){
		return codeAvant;
	}
	public void setCodeAvant(String codeAvant) { 
		this.codeAvant = codeAvant;
	}


	public String getCodeApres(){
		return codeApres;
	}
	public void setCodeApres(String codeApres) { 
		this.codeApres = codeApres;
	}


	public String getAvant(){
		return avant;
	}
	public void setAvant(String avant) { 
		this.avant = avant;
	}


	public String getApres(){
		return apres;
	}
	public void setApres(String apres) { 
		this.apres = apres;
	}


	public String getTable(){
		return table;
	}
	public void setTable(String table) { 
		this.table = table;
	}


	public String getLien(){
		return lien;
	}
	public void setLien(String lien) { 
		this.lien = lien;
	}


	public String getOrdinateur(){
		return ordinateur;
	}
	public void setOrdinateur(String ordinateur) { 
		this.ordinateur = ordinateur;
	}


	public String getNavigateur(){
		return navigateur;
	}
	public void setNavigateur(String navigateur) { 
		this.navigateur = navigateur;
	}


	public String getAdresseIp(){
		return adresseIp;
	}
	public void setAdresseIp(String adresseIp) { 
		this.adresseIp = adresseIp;
	}


	public Integer getActive(){
		return active;
	}
	public void setActive(Integer active) { 
		this.active = active;
	}


	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer version) { 
		this.version = version;
	}


	public String getOperation(){
		return operation;
	}
	public void setOperation(String operation) { 
		this.operation = operation;
	}
	@Override
	public String toString() {
		return Utilities.createLabel(codeAvant, codeApres, avant, apres, table, lien, ordinateur, navigateur, adresseIp, operation);
	}

	private static final long serialVersionUID = 1L;

	public Historique(Integer connexionId, Date date, String codeAvant, String codeApres,
			String avant, String apres, String table, String lien, String ordinateur, String navigateur,
			String adresseIp, Integer active, Integer version, String operation) {
		this.connexionId = connexionId;
		this.date = date;
		this.codeAvant = codeAvant;
		this.codeApres = codeApres;
		this.avant = avant;
		this.apres = apres;
		this.table = table;
		this.lien = lien;
		this.ordinateur = ordinateur;
		this.navigateur = navigateur;
		this.adresseIp = adresseIp;
		this.active = active;
		this.version = version;
		this.operation = operation;
	}

}
