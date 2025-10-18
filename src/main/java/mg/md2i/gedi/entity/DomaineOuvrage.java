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
import java.util.Objects;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(domaineOuvrage) avec mapping pour les tables de la BDD
 * http://www.md2i.eu 
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
@Table(name="l_domaine_ouvrage")
public class DomaineOuvrage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "domaine_ouvrage_id",unique=true, nullable = false)
	private Integer domaineOuvrageId;

	@Column(name="do_code", nullable =false)
	private String code;
	

	@Column(name="do_libelle", nullable =false)
	private String libelle;


	@Column(name="do_remarque")
	private String remarque;


	@Column(name="do_actif")
	private Integer actif=1;


	@Column(name="do_version")
	private Integer version=1;




	public Integer getDomaineOuvrageId(){
		return domaineOuvrageId;
	}
	public void setDomaineOuvrageId(Integer domaineOuvrageId) { 
		this.domaineOuvrageId = domaineOuvrageId;
	}


	public String getCode(){
		return code;
	}
	public void setCode(String code) { 
		this.code = code;
	}


	public String getLibelle(){
		return libelle;
	}
	public void setLibelle(String libelle) { 
		this.libelle = libelle;
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
	@Override
	public String toString() {
		return Utilities.createLabel(code, libelle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(actif, code, domaineOuvrageId, libelle, remarque, version);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomaineOuvrage other = (DomaineOuvrage) obj;
		return Objects.equals(actif, other.actif) && Objects.equals(code, other.code)
				&& Objects.equals(domaineOuvrageId, other.domaineOuvrageId) && Objects.equals(libelle, other.libelle)
				&& Objects.equals(remarque, other.remarque) && Objects.equals(version, other.version);
	}

	private static final long serialVersionUID = 1L;




}
