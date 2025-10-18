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
 * Entite(ministere) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_ministere")
public class Ministere implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ministere_id",unique=true, nullable = false)
	private Integer ministereId;

	@Column(name="min_nom", nullable =false)
	private String nom;


	@Column(name="min_code", nullable =false)
	private String code;


	@Column(name="min_instistution", nullable =false)
	private String instistution;


	@Column(name="min_remarque", nullable =false)
	private String remarque;


	@Column(name="min_version")
	private Integer version=1;


	@Column(name="min_actif")
	private Integer actif=1;





	public Integer getMinistereId(){
		return ministereId;
	}
	public void setMinistereId(Integer ministereId) { 
		this.ministereId = ministereId;
	}




	public String getNom(){
		return nom;
	}
	public void setNom(String nom) { 
		this.nom = nom;
	}




	public String getCode(){
		return code;
	}
	public void setCode(String code) { 
		this.code = code;
	}




	public String getInstistution(){
		return instistution;
	}
	public void setInstistution(String instistution) { 
		this.instistution = instistution;
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
	

}
