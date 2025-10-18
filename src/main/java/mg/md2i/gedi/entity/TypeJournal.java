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
 * Entite(typeJournal) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_type_journal")
public class TypeJournal implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "type_journal_id",unique=true, nullable = false)
	private Integer typeJournalId;

	@Column(name="jt_type_journal", nullable =false)
	private String typeJournal;


	@Column(name="jt_remarque", nullable =false)
	private String remarque;


	@Column(name="jt_version")
	private Integer version=1;


	@Column(name="jt_actif")
	private Integer actif=1;





	public Integer getTypeJournalId(){
		return typeJournalId;
	}
	public void setTypeJournalId(Integer typeJournalId) { 
		this.typeJournalId = typeJournalId;
	}




	public String getTypeJournal(){
		return typeJournal;
	}
	public void setTypeJournal(String typeJournal) { 
		this.typeJournal = typeJournal;
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
		return typeJournal;
	}

}

