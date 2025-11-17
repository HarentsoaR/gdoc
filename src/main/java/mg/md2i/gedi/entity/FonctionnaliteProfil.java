package mg.md2i.gedi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entite(fonctionnaliteProfil) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="t_fonctionnalite_profil")
public class FonctionnaliteProfil implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fonctionnalite_profil_id",unique=true, nullable = false)
	private Integer fonctionnaliteProfilId;

	@Column(name="fonctionnalite_id", nullable =false)
	private Integer fonctionnaliteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fonctionnalite_id", nullable = false, insertable=false , updatable=false)
    private Fonctionnalite fonctionnalite;

	@Column(name="profil_id", nullable =false)
	private Integer profilId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profil_id", nullable = false, insertable=false , updatable=false)
    private Profil profil;

	@Column(name="fp_lire", nullable =true)
	private Integer lire;


	@Column(name="fp_modifier", nullable =true)
	private Integer modifier;
	
	@Column(name="fp_nouveau", nullable =true)
	private Integer nouveau;


	@Column(name="fp_supprimer", nullable =true)
	private Integer supprimer;
	
	@Column(name="fp_exporter", nullable =true)
	private Integer exporter;

	@Column(name="fp_dupliquer", nullable =true)
	private Integer dupliquer;



	@Column(name="fp_remarque", nullable =true)
	private String remarque;


	@Column(name="fp_version")
	private Integer version=1;


	@Column(name="fp_actif")
	private Integer actif=1;





	public Integer getFonctionnaliteProfilId(){
		return fonctionnaliteProfilId;
	}
	public void setFonctionnaliteProfilId(Integer fonctionnaliteProfilId) { 
		this.fonctionnaliteProfilId = fonctionnaliteProfilId;
	}




	public Integer getFonctionnaliteId(){
		return fonctionnaliteId;
	}
	public void setFonctionnaliteId(Integer fonctionnaliteId) { 
		this.fonctionnaliteId = fonctionnaliteId;
	}
	public Fonctionnalite getFonctionnalite() {
		return fonctionnalite;
	}
	public void setFonctionnalite(Fonctionnalite fonctionnalite) {
		this.fonctionnalite = fonctionnalite;
	}




	public Integer getProfilId(){
		return profilId;
	}
	public void setProfilId(Integer profilId) { 
		this.profilId = profilId;
	}
	public Profil getProfil() {
		return profil;
	}
	public void setProfil(Profil profil) {
		this.profil = profil;
	}




	public Integer getLire(){
		return lire;
	}
	public void setLire(Integer lire) { 
		this.lire = lire;
	}




	public Integer getModifier(){
		return modifier;
	}
	public void setModifier(Integer modifier) { 
		this.modifier = modifier;
	}




	public Integer getSupprimer(){
		return supprimer;
	}
	public void setSupprimer(Integer supprimer) { 
		this.supprimer = supprimer;
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





	public Integer getNouveau() {
		return nouveau;
	}
	public void setNouveau(Integer nouveau) {
		this.nouveau = nouveau;
	}
	public Integer getExporter() {
		return exporter;
	}
	public void setExporter(Integer exporter) {
		this.exporter = exporter;
	}
	public Integer getDupliquer() {
		return dupliquer;
	}
	public void setDupliquer(Integer dupliquer) {
		this.dupliquer = dupliquer;
	}
	

}
