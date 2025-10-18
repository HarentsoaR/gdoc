package mg.md2i.gedi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entite(filiere) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="a_filiere")
public class Filiere implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "filiere_id",unique=true, nullable = false)
	private Integer filiereId;

	@Column(name="f_libelle", nullable =false)
	private String libelle;


	@Column(name="f_code", nullable =false)
	private String code;


	@Column(name="f_remarque", nullable =false)
	private String remarque;
	
	@Column(name="sysid", nullable =false)
	private String sysId;

	@Column(name="f_actif")
	private Integer actif=1;


	@Column(name="f_version")
	private Integer version=1;





	public Integer getFiliereId(){
		return filiereId;
	}
	public void setFiliereId(Integer filiereId) { 
		this.filiereId = filiereId;
	}




	public String getLibelle(){
		return libelle;
	}
	public void setLibelle(String libelle) { 
		this.libelle = libelle;
	}




	public String getCode(){
		return code;
	}
	public void setCode(String code) { 
		this.code = code;
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
		return code+" | "+libelle;
	}



	private static final long serialVersionUID = 1L;



	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result
				+ ((filiereId == null) ? 0 : filiereId.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filiere other = (Filiere) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (filiereId == null) {
			if (other.filiereId != null)
				return false;
		} else if (!filiereId.equals(other.filiereId))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		return true;
	}





	

}

