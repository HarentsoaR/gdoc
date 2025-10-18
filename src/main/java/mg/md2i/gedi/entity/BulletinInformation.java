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
 * Entite(bulletinInformation) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu 
 * en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name="l_bulletin_information")
public class BulletinInformation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bulletin_info_id",unique=true, nullable = false)
	private Integer bulletinInformationId;

	@Column(name="ministere_id", nullable =false)
	private Integer ministereId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ministere_id", nullable = false, insertable=false , updatable=false)
	private Ministere ministere;

	@Column(name="origine_id", nullable =false)
	private Integer origineId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "origine_id", nullable = false, insertable=false , updatable=false)
	private Origine origine;

	
	public Integer getOrigineId() {
		return origineId;
	}
	public void setOrigineId(Integer origineId) {
		this.origineId = origineId;
	}
	public Origine getOrigine() {
		return origine;
	}
	public void setOrigine(Origine origine) {
		this.origine = origine;
	}

	@Column(name="bi_titre", nullable =false)
	private String titre;


	@Column(name="bi_code", nullable =false)
	private String code;


	@Column(name="bi_numero", nullable =false)
	private String numero;


	@Column(name="bi_annee", nullable =false)
	private String annee;


	@Column(name="bi_mois", nullable =false)
	private String mois;

	@Column(name="bi_date_entree", nullable =true)
	private Date dateEntree;

	@Column(name="detail_bulletin_id", nullable =false)
	private Integer detailBulletinId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_bulletin_id", nullable = false, insertable=false , updatable=false)
	private DetailBulletin detailBulletin;

	@Column(name="bi_remarque", nullable =false)
	private String remarque;


	@Column(name="bi_version")
	private Integer version=1;


	@Column(name="bi_actif")
	private Integer actif=1;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_type_document_biblio_id", nullable = false, insertable=false , updatable=false)
	private DetailTypeDocumentBiblio detailTypeDocumentBiblio;
	
	@Column(name="detail_type_document_biblio_id")
	private Integer detailTypeDocumentBiblioId;
	
	@Column(name="type_bulletin_information_id", nullable =false)
	private Integer typeBulletinInformationId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_bulletin_information_id", nullable = false, insertable=false , updatable=false)
	private TypeBulletinInformation typeBulletinInformation;
	
	
	
	
	public Integer getTypeBulletinInformationId() {
		return typeBulletinInformationId;
	}
	public void setTypeBulletinInformationId(Integer typeBulletinInformationId) {
		this.typeBulletinInformationId = typeBulletinInformationId;
	}
	public TypeBulletinInformation getTypeBuTlletinInformation() {
		return typeBulletinInformation;
	}
	public void setTypeBuTlletinInformation(
			TypeBulletinInformation typeBuTlletinInformation) {
		this.typeBulletinInformation = typeBuTlletinInformation;
	}
	public Integer getDetailTypeDocumentBiblioId() {
		return detailTypeDocumentBiblioId;
	}
	public void setDetailTypeDocumentBiblioId(Integer detailTypeDocumentBiblioId) {
		this.detailTypeDocumentBiblioId = detailTypeDocumentBiblioId;
	}




	public Integer getBulletinInfoId(){
		return bulletinInformationId;
	}
	public void setBulletinInfoId(Integer bulletinInformationId) { 
		this.bulletinInformationId = bulletinInformationId;
	}




	public Integer getMinistereId(){
		return ministereId;
	}
	public void setMinistereId(Integer ministereId) { 
		this.ministereId = ministereId;
	}
	public Ministere getMinistere() {
		return ministere;
	}
	public void setMinistere(Ministere ministere) {
		this.ministere = ministere;
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




	public String getNumero(){
		return numero;
	}
	public void setNumero(String numero) { 
		this.numero = numero;
	}




	public String getAnnee(){
		return annee;
	}
	public void setAnnee(String annee) { 
		this.annee = annee;
	}




	public String getMois(){
		return mois;
	}
	public void setMois(String mois) { 
		this.mois = mois;
	}




	public Integer getDetailBulletinId(){
		return detailBulletinId;
	}
	public void setDetailBulletinId(Integer detailBulletinId) { 
		this.detailBulletinId = detailBulletinId;
	}
	public DetailBulletin getDetailBulletin() {
		return detailBulletin;
	}
	public void setDetailBulletin(DetailBulletin detailBulletin) {
		this.detailBulletin = detailBulletin;
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

	public Date getDateEntree() {
		return dateEntree;
	}
	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}
	public DetailTypeDocumentBiblio getDetailTypeDocumentBiblio() {
		return detailTypeDocumentBiblio;
	}
	public void setDetailTypeDocumentBiblio(DetailTypeDocumentBiblio detailTypeDocumentBiblio) {
		this.detailTypeDocumentBiblio = detailTypeDocumentBiblio;
	}
	public Integer getBulletinInformationId() {
		return bulletinInformationId;
	}
	public void setBulletinInformationId(Integer bulletinInformationId) {
		this.bulletinInformationId = bulletinInformationId;
	}
	@Override
	public String toString() {
		return "BulletinInformation [titre=" + titre + "]";
	}
	

}

