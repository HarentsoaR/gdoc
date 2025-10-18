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
 * Entite(typeBulletinInformation) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_type_bulletin_information")
public class TypeBulletinInformation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "type_bulletin_information_id",unique=true, nullable = false)
	private Integer typeBulletinInformationId;

	@Column(name="tbi_libelle", nullable =false)
	private String libelle;


	@Column(name="a_remarque", nullable =false)
	private String remarque;


	@Column(name="a_actif")
	private Integer actif=1;


	@Column(name="a_version")
	private Integer version=1;





	public Integer getTypeBulletinInformationId(){
		return typeBulletinInformationId;
	}
	public void setTypeBulletinInformationId(Integer typeBulletinInformationId) { 
		this.typeBulletinInformationId = typeBulletinInformationId;
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
		return libelle;
	}

	private static final long serialVersionUID = 1L;
	

}
