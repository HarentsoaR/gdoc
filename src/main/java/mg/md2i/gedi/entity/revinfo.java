package mg.md2i.gedi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Entite(revinfo) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="revinfo")
public class revinfo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REV",unique=true, nullable = false)
	private Integer REV;

	@Column(name="id", nullable =false)
	private Integer id;


	@Column(name="timestamp", nullable =false)
	private Long timestamp;


	@Column(name="username", nullable =false)
	private String username;


	@Column(name="r_version")
	private Integer version=1;


	@Column(name="r_actif")
	private Integer actif=1;





	public Integer getREV(){
		return REV;
	}
	public void setREV(Integer REV) { 
		this.REV = REV;
	}




	public Integer getId(){
		return id;
	}
	public void setId(Integer id) { 
		this.id = id;
	}




	public Long getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(Long timestamp) { 
		this.timestamp = timestamp;
	}




	public String getUsername(){
		return username;
	}
	public void setUsername(String username) { 
		this.username = username;
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
