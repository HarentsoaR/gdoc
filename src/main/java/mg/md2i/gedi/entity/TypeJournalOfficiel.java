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
 * Entite(typeJournalOfficiel) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_type_journal_officiel")
public class TypeJournalOfficiel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "type_jo_id",unique=true, nullable = false)
	private Integer typeJournalOfficielId;

	@Column(name="tj_titre", nullable =false)
	private String titre;


	@Override
	public String toString() {
		return titre + ", " + numero + " du " + date + " p." + page + " - " + origine;
	}

	@Column(name="tj_numero", nullable =false)
	private Integer numero;


	@Column(name="tj_date", nullable =false)
	private Date date;


	@Column(name="tj_page", nullable =false)
	private Integer page;


	@Column(name="tj_origine", nullable =false)
	private String origine;


	@Column(name="tj_remarque", nullable =false)
	private String remarque;


	@Column(name="tj_version")
	private Integer version=1;


	@Column(name="tj_actif")
	private Integer actif=1;





	public Integer getTypeJoId(){
		return typeJournalOfficielId;
	}
	public void setTypeJoId(Integer typeJoId) { 
		this.typeJournalOfficielId = typeJoId;
	}




	public String getTitre(){
		return titre;
	}
	public void setTitre(String titre) { 
		this.titre = titre;
	}




	public Integer getNumero(){
		return numero;
	}
	public void setNumero(Integer numero) { 
		this.numero = numero;
	}




	public Date getDate(){
		return date;
	}
	public void setDate(Date date) { 
		this.date = date;
	}




	public Integer getPage(){
		return page;
	}
	public void setPage(Integer page) { 
		this.page = page;
	}




	public String getOrigine(){
		return origine;
	}
	public void setOrigine(String origine) { 
		this.origine = origine;
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

