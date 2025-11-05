package mg.md2i.gedi.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "g_document")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id", unique = true, nullable = false)
    private Integer documentId;
	
	    @Column(name = "do_titre", nullable = false)
    private String titre;

    @Column(name = "do_resume")
    private String resume;

    @Column(name = "do_path", nullable = false)
    private String path;

    @Column(name = "do_type", nullable = false)
    private String type;

    @Column(name = "do_taille")
    private Long taille;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "do_date_upload")
    private Date dateUpload = new Date();

    @Column(name = "do_actif")
    private Integer actif = 1;

    @Column(name = "do_version")
    private Integer version = 1;

    @Column(name = "do_remarque")
    private String remarque;

    /**
     * UI-only field to display a friendly location/path. Not persisted.
     */
    @Transient
    private String emplacement;

    public String getTitre() {
        return titre;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public String getResume() {
        return resume;
    }

    public String getPath() {
        return path;
    }

    public Long getTaille() {
        return taille;
    }

    public Integer getActif() {
        return actif;
    }

    public Integer getVersion() {
        return version;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setActif(int actif) {
        this.actif = actif;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public void setType(String type) {
		this.type = type;
	}

    public String getType() {
        return this.type;
    }
    
    public void setTaille(long taille) {
		this.taille = taille;
	}
	
	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }
}
