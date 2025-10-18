package mg.md2i.gedi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Version;

import java.io.Serializable;
//	import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * Entite(listeDossierConcours) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="g_liste_dossier_concours")
public class ListeDossierConcours implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "liste_dossier_concours_id",unique=true, nullable = false)
	private Integer listeDossierConcoursId;

	@Column(name="concours_id", nullable =false)
	private Integer concoursId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "concours_id", nullable = false, insertable=false , updatable=false)
	private Concours concours;

	@Column(name="ldc_nom_dossier", nullable =false)
	private String nomDossier;


	@Column(name="document_concours_id", nullable =false)
	private Integer documentConcoursId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "document_concours_id", nullable = false, insertable=false , updatable=false)
	private DocumentConcours documentConcours;

	@Column(name="ldc_flag", nullable =false)
	private Integer flag;


	@Column(name="ldc_remarque", nullable =true)
	private String remarque;


	@Column(name="ldc_actif")
	private Integer actif=1;


	@Column(name="ldc_version")
	private Integer version=1;





	public Integer getListeDossierConcoursId(){
		return listeDossierConcoursId;
	}
	public void setListeDossierConcoursId(Integer listeDossierConcoursId) { 
		this.listeDossierConcoursId = listeDossierConcoursId;
	}




	public Integer getConcoursId(){
		return concoursId;
	}
	public void setConcoursId(Integer concoursId) { 
		this.concoursId = concoursId;
	}
	public Concours getConcours() {
		return concours;
	}
	public void setConcours(Concours concours) {
		this.concours = concours;
	}




	public String getNomDossier(){
		return nomDossier;
	}
	public void setNomDossier(String nomDossier) { 
		this.nomDossier = nomDossier;
	}




	public Integer getDocumentConcoursId(){
		return documentConcoursId;
	}
	public void setDocumentConcoursId(Integer documentConcoursId) { 
		this.documentConcoursId = documentConcoursId;
	}
	public DocumentConcours getDocumentConcours() {
		return documentConcours;
	}
	public void setDocumentConcours(DocumentConcours documentConcours) {
		this.documentConcours = documentConcours;
	}




	public Integer getFlag(){
		return flag;
	}
	public void setFlag(Integer flag) { 
		this.flag = flag;
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

	private static final long serialVersionUID = 1L;
	

}

