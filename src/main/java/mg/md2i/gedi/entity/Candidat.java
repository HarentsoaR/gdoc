package mg.md2i.gedi.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import mg.md2i.gedi.entityState.CandidatEtat;
import mg.md2i.enmg.tools.ConvertDate;

/**
 * Entite(candidat) avec mapping pour les tables de la BDD pour l'ENMG
 * http://www.md2i.eu en collaboration avec ENMG
 * 
 * @author toky@md2i.eu ou t.rakotonirina@gmail.com
 */

@Entity
//@Audited
@Table(name = "g_candidat")
public class Candidat extends CandidatEtat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "candidat_id", unique = true, nullable = false)
	private Integer candidatId;

	@Column(name = "concours_id", nullable = false)
	private Integer concoursId;

	@ManyToOne
	@JoinColumn(name = "concours_id", insertable = false, updatable = false)
	private Concours concours;

	@Column(name = "ca_numero_enregistrement", nullable = false)
	private String numeroEnregistrement;

	@Column(name = "ca_matricule_eleve", nullable = true)
	private String matriculeEleve;

	@Column(name = "ca_rang_concours", nullable = false)
	private String rangConcours;

	@Column(name = "ca_nom", nullable = false)
	private String nom;

	@Column(name = "ca_prenom", nullable = true)
	private String prenom;

	@Column(name = "ca_sexe", nullable = false)
	private Integer sexe;

	@Column(name = "ca_date_naissance", nullable = false)
	private Date dateNaissance;

	@Column(name = "ca_lieu_naissance", nullable = false)
	private String lieuNaissance;

	@Column(name = "ca_adresse_eleve", nullable = false)
	private String adresseEleve;

	@Column(name = "ca_contact_telephonique", nullable = true)
	private Integer contactTelephonique;

	@Column(name = "ca_mail", nullable = true)
	private String mail;

	@Column(name = "ca_statut_fonctionnaire", nullable = true)
	private String statutFonctionnaire;

	@Column(name = "ca_im_fonctionnaire", nullable = true)
	private String imFonctionnaire;

	@Column(name = "ca_derniere_fonction", nullable = true)
	private String derniereFonction;

	@Column(name = "ca_situation_matrimoniale", nullable = true)
	private Integer situationMatrimoniale;

	@Column(name = "ca_nom_conjoint", nullable = true)
	private String nomConjoint;

	@Column(name = "ca_prenom_conjoint", nullable = true)
	private String prenomConjoint;

	@Column(name = "ca_profession_conjoint", nullable = true)
	private String professionConjoint;

	@Column(name = "ca_nombre_enfant", nullable = true)
	private Integer nombreEnfant;

	@Column(name = "ca_nom_pere", nullable = true)
	private String nomPere;

	@Column(name = "ca_profession_pere", nullable = true)
	private String professionPere;

	@Column(name = "ca_nom_mere", nullable = true)
	private String nomMere;

	@Column(name = "ca_profession_mere", nullable = true)
	private String professionMere;

	@Column(name = "ca_date_depot_candidature", nullable = true)
	private Date dateDepotCandidature;

	@Column(name = "ca_depot_candidature", nullable = true)
	private String depotCandidature;

	@Column(name = "ca_parente", nullable = true)
	private String parente;

	@Column(name = "ca_liste_personnes_lien_parente_avec_degre_parente", nullable = true)
	private String listePersonnesLienParenteAvecDegreParente;

	@Column(name = "ca_personne_a_prevenir", nullable = true)
	private String personneAPrevenir;

	@Column(name = "ca_tel_personne_a_prevenir", nullable = true)
	private String telPersonneAPrevenir;

	@Column(name = "ca_matricule_candidat", nullable = true)
	private String matriculeCandidat;

	@Column(name = "ca_rang_resultat_concours")
	private Integer rangResultatConcours = 1;

	@Column(name = "ca_rang_centre", nullable = true)
	private String rangCentre;

	@Column(name = "ca_remarque", nullable = false)
	private String remarque;

	@Column(name = "ca_actif")
	private Integer actif = 1;

	@Column(name = "ca_version")
	private Integer version = 1;

	@Column(name = "centre_examen_id", nullable = false)
	private Integer centreExamenId;

	@ManyToOne
	@JoinColumn(name = "centre_examen_id", insertable = false, updatable = false)
	private CentreExamen centreExamen;

	@Column(name = "ca_num_inscription", nullable = true)
	private Integer numInscription;

	@Column(name = "flag", nullable = true)
	private int flag;

	@Column(name = "ca_reception_poste", nullable = true)
	private int receptionPoste;
	
	@Column(name = "etatAge", nullable = true)
	private int etatAge;

	@Transient
	private String dateNaissanceToString;

	@Transient
	private String sexeValeur;
	
	@Transient
	private String receptionValeur;

	public Integer getCandidatId() {
		return candidatId;
	}

	public void setCandidatId(Integer candidatId) {
		this.candidatId = candidatId;
	}

	
	public int getReceptionPoste() {
		return receptionPoste;
	}

	public void setReceptionPoste(int receptionPoste) {
		this.receptionPoste = receptionPoste;
		if (receptionPoste == 1)
			receptionValeur = "Par Poste";
		else
			receptionValeur = "ENMG";
		
	}

	public Integer getConcoursId() {
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

	public String getNumeroEnregistrement() {
		return numeroEnregistrement;
	}

	public void setNumeroEnregistrement(String numeroEnregistrement) {
		this.numeroEnregistrement = numeroEnregistrement;
	}

	public String getMatriculeEleve() {
		return matriculeEleve;
	}

	public void setMatriculeEleve(String matriculeEleve) {
		this.matriculeEleve = matriculeEleve;
	}

	public String getRangConcours() {
		return rangConcours;
	}

	public void setRangConcours(String rangConcours) {
		this.rangConcours = rangConcours;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Integer getSexe() {
		return sexe;
	}

	public void setSexe(Integer sexe) {
		this.sexe = sexe;
		if (sexe == 1)
			sexeValeur = "M";
		else
			sexeValeur = "F";
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public String getAdresseEleve() {
		return adresseEleve;
	}

	public void setAdresseEleve(String adresseEleve) {
		this.adresseEleve = adresseEleve;
	}

	public Integer getContactTelephonique() {
		return contactTelephonique;
	}

	public void setContactTelephonique(Integer contactTelephonique) {
		this.contactTelephonique = contactTelephonique;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getStatutFonctionnaire() {
		return statutFonctionnaire;
	}

	public void setStatutFonctionnaire(String statutFonctionnaire) {
		this.statutFonctionnaire = statutFonctionnaire;
	}

	public String getImFonctionnaire() {
		return imFonctionnaire;
	}

	public void setImFonctionnaire(String imFonctionnaire) {
		this.imFonctionnaire = imFonctionnaire;
	}

	public String getDerniereFonction() {
		return derniereFonction;
	}

	public void setDerniereFonction(String derniereFonction) {
		this.derniereFonction = derniereFonction;
	}

	public Integer getSituationMatrimoniale() {
		return situationMatrimoniale;
	}

	public void setSituationMatrimoniale(Integer situationMatrimoniale) {
		this.situationMatrimoniale = situationMatrimoniale;
	}

	public String getNomConjoint() {
		return nomConjoint;
	}

	public void setNomConjoint(String nomConjoint) {
		this.nomConjoint = nomConjoint;
	}

	public String getPrenomConjoint() {
		return prenomConjoint;
	}

	public void setPrenomConjoint(String prenomConjoint) {
		this.prenomConjoint = prenomConjoint;
	}

	public String getProfessionConjoint() {
		return professionConjoint;
	}

	public void setProfessionConjoint(String professionConjoint) {
		this.professionConjoint = professionConjoint;
	}

	public Integer getNombreEnfant() {
		return nombreEnfant;
	}

	public void setNombreEnfant(Integer nombreEnfant) {
		this.nombreEnfant = nombreEnfant;
	}

	public String getNomPere() {
		return nomPere;
	}

	public void setNomPere(String nomPere) {
		this.nomPere = nomPere;
	}

	public String getProfessionPere() {
		return professionPere;
	}

	public void setProfessionPere(String professionPere) {
		this.professionPere = professionPere;
	}

	public String getNomMere() {
		return nomMere;
	}

	public void setNomMere(String nomMere) {
		this.nomMere = nomMere;
	}

	public String getProfessionMere() {
		return professionMere;
	}

	public void setProfessionMere(String professionMere) {
		this.professionMere = professionMere;
	}

	public Date getDateDepotCandidature() {
		return dateDepotCandidature;
	}

	public void setDateDepotCandidature(Date dateDepotCandidature) {
		this.dateDepotCandidature = dateDepotCandidature;
	}

	public String getDepotCandidature() {
		return depotCandidature;
	}

	public void setDepotCandidature(String depotCandidature) {
		this.depotCandidature = depotCandidature;
	}

	public String getParente() {
		return parente;
	}

	public void setParente(String parente) {
		this.parente = parente;
	}

	public String getListePersonnesLienParenteAvecDegreParente() {
		return listePersonnesLienParenteAvecDegreParente;
	}

	public void setListePersonnesLienParenteAvecDegreParente(String listePersonnesLienParenteAvecDegreParente) {
		this.listePersonnesLienParenteAvecDegreParente = listePersonnesLienParenteAvecDegreParente;
	}

	public String getPersonneAPrevenir() {
		return personneAPrevenir;
	}

	public void setPersonneAPrevenir(String personneAPrevenir) {
		this.personneAPrevenir = personneAPrevenir;
	}

	public String getTelPersonneAPrevenir() {
		return telPersonneAPrevenir;
	}

	public void setTelPersonneAPrevenir(String telPersonneAPrevenir) {
		this.telPersonneAPrevenir = telPersonneAPrevenir;
	}

	public String getMatriculeCandidat() {
		return matriculeCandidat;
	}

	public void setMatriculeCandidat(String matriculeCandidat) {
		this.matriculeCandidat = matriculeCandidat;
	}

	public String getRangCentre() {
		return rangCentre;
	}

	public void setRangCentre(String rangCentre) {
		this.rangCentre = rangCentre;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public Integer getActif() {
		return actif;
	}

	public void setActif(Integer actif) {
		this.actif = actif;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getCentreExamenId() {
		return centreExamenId;
	}

	public void setCentreExamenId(Integer centreExamenId) {
		this.centreExamenId = centreExamenId;
	}

	public CentreExamen getCentreExamen() {
		return centreExamen;
	}
	
	public void setCentreExamen(CentreExamen centreExamen) {
		this.centreExamen = centreExamen;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return nom;
	}

	@Column(name = "centreConcoursId", nullable = true)
	int centreConcoursId;

	public int getCentreConcoursId() {
		return centreConcoursId;
	}

	public void setCentreConcoursId(int centreConcoursId) {
		this.centreConcoursId = centreConcoursId;
	}

	public int getEtatAge() {
		return etatAge;
	}

	public void setEtatAge(int etatAge) {
		this.etatAge = etatAge;
	}

	public String getDateNaissanceToString() {
		return ConvertDate.getDateFormatter(this.getDateNaissance());
	}

	public void setDateNaissanceToString(String dateNaissanceToString) {
		this.dateNaissanceToString = dateNaissanceToString;
	}

	public Integer getRangResultatConcours() {
		return rangResultatConcours;
	}

	public void setRangResultatConcours(Integer rangResultatConcours) {
		this.rangResultatConcours = rangResultatConcours;
	}

	public Integer getNumInscription() {
		return numInscription;
	}

	public void setNumInscription(Integer numInscription) {
		this.numInscription = numInscription;
	}

	public String getSexeValeur() {
		if (getSexe() == 1)
			this.sexeValeur = "M";
		else
			this.sexeValeur = "F";
		return sexeValeur;
	}

	public void setSexeValeur(String sexeValeur) {
		this.sexeValeur = sexeValeur;
	}

	public String getReceptionValeur() {
		if (getReceptionPoste() == 1)
			receptionValeur = "Par Poste";
		else
			receptionValeur = "ENMG";
		return receptionValeur;
	}

	public void setReceptionValeur(String receptionValeur) {
		this.receptionValeur = receptionValeur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actif, adresseEleve, candidatId, centreConcoursId, centreExamenId, concoursId,
				contactTelephonique, dateDepotCandidature, dateNaissance, dateNaissanceToString, depotCandidature,
				derniereFonction, etatAge, flag, imFonctionnaire, lieuNaissance,
				listePersonnesLienParenteAvecDegreParente, mail, matriculeCandidat, matriculeEleve, nom, nomConjoint,
				nomMere, nomPere, nombreEnfant, numInscription, numeroEnregistrement, parente, personneAPrevenir,
				prenom, prenomConjoint, professionConjoint, professionMere, professionPere, rangCentre, rangConcours,
				rangResultatConcours, remarque, sexe, sexeValeur, situationMatrimoniale, statutFonctionnaire,
				telPersonneAPrevenir, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidat other = (Candidat) obj;
		return Objects.equals(actif, other.actif) && Objects.equals(adresseEleve, other.adresseEleve)
				&& Objects.equals(candidatId, other.candidatId) && centreConcoursId == other.centreConcoursId
				&& Objects.equals(centreExamenId, other.centreExamenId) && Objects.equals(concoursId, other.concoursId)
				&& Objects.equals(contactTelephonique, other.contactTelephonique)
				&& Objects.equals(dateDepotCandidature, other.dateDepotCandidature)
				&& Objects.equals(dateNaissance, other.dateNaissance)
				&& Objects.equals(dateNaissanceToString, other.dateNaissanceToString)
				&& Objects.equals(depotCandidature, other.depotCandidature)
				&& Objects.equals(derniereFonction, other.derniereFonction) && etatAge == other.etatAge
				&& flag == other.flag && Objects.equals(imFonctionnaire, other.imFonctionnaire)
				&& Objects.equals(lieuNaissance, other.lieuNaissance)
				&& Objects.equals(listePersonnesLienParenteAvecDegreParente,
						other.listePersonnesLienParenteAvecDegreParente)
				&& Objects.equals(mail, other.mail) && Objects.equals(matriculeCandidat, other.matriculeCandidat)
				&& Objects.equals(matriculeEleve, other.matriculeEleve) && Objects.equals(nom, other.nom)
				&& Objects.equals(nomConjoint, other.nomConjoint) && Objects.equals(nomMere, other.nomMere)
				&& Objects.equals(nomPere, other.nomPere) && Objects.equals(nombreEnfant, other.nombreEnfant)
				&& Objects.equals(numInscription, other.numInscription)
				&& Objects.equals(numeroEnregistrement, other.numeroEnregistrement)
				&& Objects.equals(parente, other.parente) && Objects.equals(personneAPrevenir, other.personneAPrevenir)
				&& Objects.equals(prenom, other.prenom) && Objects.equals(prenomConjoint, other.prenomConjoint)
				&& Objects.equals(professionConjoint, other.professionConjoint)
				&& Objects.equals(professionMere, other.professionMere)
				&& Objects.equals(professionPere, other.professionPere) && Objects.equals(rangCentre, other.rangCentre)
				&& Objects.equals(rangConcours, other.rangConcours)
				&& Objects.equals(rangResultatConcours, other.rangResultatConcours)
				&& Objects.equals(remarque, other.remarque) && Objects.equals(sexe, other.sexe)
				&& Objects.equals(sexeValeur, other.sexeValeur)
				&& Objects.equals(situationMatrimoniale, other.situationMatrimoniale)
				&& Objects.equals(statutFonctionnaire, other.statutFonctionnaire)
				&& Objects.equals(telPersonneAPrevenir, other.telPersonneAPrevenir)
				&& Objects.equals(version, other.version);
	}
	

}
