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
 * Entite(domaineBiblio) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_domaine_biblio")
public class DomaineBiblio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "domaine_biblio_id",unique=true, nullable = false)
	private Integer domaineBiblioId;

	@Column(name="do_titre", nullable =false)
	private String titre;


	@Column(name="do_remarque", nullable =false)
	private String remarque;


	@Column(name="do_version")
	private Integer version=1;


	@Column(name="do_actif")
	private Integer actif=1;





	public Integer getDomaineBiblioId(){
		return domaineBiblioId;
	}
	public void setDomaineBiblioId(Integer domaineBiblioId) { 
		this.domaineBiblioId = domaineBiblioId;
	}




	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
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
		return  titre ;
	}

}
