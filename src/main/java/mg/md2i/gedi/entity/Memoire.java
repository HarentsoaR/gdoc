package mg.md2i.gedi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(memoire) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_memoire")
public class Memoire implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "memoire_id",unique=true, nullable = false)
	private Integer memoireId;

	@Column(name="mm_encadreur_ped", nullable =false)
	private String encadreurPed;


	@Column(name="mm_encadreur_prof", nullable =false)
	private String encadreurProf;


	@Column(name="mm_remarque", nullable =false)
	private String remarque;


	@Column(name="mm_version")
	private Integer version=1;


	@Column(name="mm_actif")
	private Integer actif=1;

	public Integer getMemoireId(){
		return memoireId;
	}
	public void setMemoireId(Integer memoireId) { 
		this.memoireId = memoireId;
	}

	public String getEncadreurPed(){
		return encadreurPed;
	}
	public void setEncadreurPed(String encadreurPed) { 
		this.encadreurPed = encadreurPed;
	}




	public String getEncadreurProf(){
		return encadreurProf;
	}
	public void setEncadreurProf(String encadreurProf) { 
		this.encadreurProf = encadreurProf;
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
		return "Memoire encadr� par" + encadreurPed +"|"+ encadreurProf;
	}
	

}

