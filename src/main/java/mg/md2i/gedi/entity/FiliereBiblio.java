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
 * Entite(filiereBiblio) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_filiere_biblio")
public class FiliereBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "filiere_biblio_id",unique=true, nullable = false)
	private Integer filiereBiblioId;

	@Column(name="fi_titre", nullable =false)
	private String titre;


	@Column(name="fi_code", nullable =false)
	private String code;


	@Column(name="fi_remarque", nullable =false)
	private String remarque;


	@Column(name="fi_version")
	private Integer version=1;


	@Column(name="fi_actif")
	private Integer actif=1;





	public Integer getFiliereBiblioId(){
		return filiereBiblioId;
	}
	public void setFiliereBiblioId(Integer filiereBiblioId) { 
		this.filiereBiblioId = filiereBiblioId;
	}




	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
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
		return code+"|"+titre;
	}

}
