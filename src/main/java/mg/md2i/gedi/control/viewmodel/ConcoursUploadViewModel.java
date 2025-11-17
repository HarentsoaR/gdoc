package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import mg.md2i.gedi.entity.ListeDossierConcours;
import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.ListeDossierConcoursCandidatGestion;
import mg.md2i.gedi.gestionmetier.ListeDossierConcoursGestion;
import mg.md2i.gedi.gestionmetier.DocumentConcoursGestion;
import mg.md2i.gedi.gestionmetier.CandidatGestion;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Messagebox;

import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

/**
 * ViewModel pour l'upload des documents de concours côté candidat.
 * Sauvegarde le fichier sur le disque (/Users/mac/gedi_storage) et enregistre le lien
 * dans la table g_liste_dossier_concours_candidat via son champ remarqueFacultatif.
 */
public class ConcoursUploadViewModel {

    private List<DocumentConcours> documentsConcours;
    private List<ListeDossierConcours> dossiersConcours;
    private List<Candidat> candidats;

    private DocumentConcours selectedDocumentConcours;
    private ListeDossierConcours selectedListeDossierConcours;
    private Candidat selectedCandidat; // fourni par le contexte appelant
    private String pendingFileName;
    private Media pendingMedia;

    public List<DocumentConcours> getDocumentsConcours() { return documentsConcours; }
    public List<ListeDossierConcours> getDossiersConcours() { return dossiersConcours; }
    public List<Candidat> getCandidats() { return candidats; }
    public DocumentConcours getSelectedDocumentConcours() { return selectedDocumentConcours; }
    public void setSelectedDocumentConcours(DocumentConcours d) { this.selectedDocumentConcours = d; }
    public ListeDossierConcours getSelectedListeDossierConcours() { return selectedListeDossierConcours; }
    public void setSelectedListeDossierConcours(ListeDossierConcours e) { this.selectedListeDossierConcours = e; }
    public Candidat getSelectedCandidat() { return selectedCandidat; }
    public void setSelectedCandidat(Candidat c) { this.selectedCandidat = c; }
    public String getPendingFileName() { return pendingFileName; }

    @Init
    public void init(@ExecutionArgParam("candidatId") Integer candidatId,
                     @ExecutionArgParam("concoursId") Integer concoursId) {
        this.documentsConcours = DocumentConcoursGestion.findAll();
        if (concoursId != null) {
            this.dossiersConcours = ListeDossierConcoursGestion.findByConcours(concoursId);
        } else {
            this.dossiersConcours = ListeDossierConcoursGestion.findAll();
        }
//        this.candidats = CandidatGestion.findAll();
        if (candidatId != null) {
            // Preselect candidate by id
            for (Candidat c : candidats) { if (c.getCandidatId().equals(candidatId)) { this.selectedCandidat = c; break; } }
        }
    }

    @Command
    @NotifyChange("pendingFileName")
    public void prepareUpload(@BindingParam("file") Media media) {
        if (media != null) {
            this.pendingMedia = media;
            this.pendingFileName = media.getName();
        }
    }

    @Command
    public void upload() {
        Media media = this.pendingMedia;
        if (media == null || selectedCandidat == null || selectedDocumentConcours == null || selectedListeDossierConcours == null) {
            Messagebox.show("Veuillez renseigner tous les champs.", "Erreur", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        try {
            String originalName = media.getName();
            String ext = "";
            int dot = originalName.lastIndexOf('.');
            if (dot > 0 && dot < originalName.length() - 1) {
                ext = originalName.substring(dot);
            }

            String baseDir = "/Users/mac/gedi_storage";
            Path uploadDir = Paths.get(baseDir, "concours",
                    String.valueOf(selectedDocumentConcours.getDocumentConcoursId()),
                    String.valueOf(selectedCandidat.getCandidatId()));
            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

            // Build human-readable filename: <type>_<prenom>_<concours>.ext
            String typeSlug = sanitizeSlug(selectedDocumentConcours.getLibelle());
            String prenom = selectedCandidat.getPrenom() != null ? selectedCandidat.getPrenom() : selectedCandidat.getNom();
            String nameSlug = sanitizeSlug(prenom);
            String concoursPart = (selectedCandidat.getConcours() != null)
                    ? selectedCandidat.getConcours().toString()
                    : String.valueOf(selectedCandidat.getConcoursId());
            String concoursSlug = sanitizeKeepPipes(concoursPart);

            String baseName = typeSlug + "_" + nameSlug + "_" + concoursSlug;
            String storedFileName = baseName + ext;
            Path targetPath = uploadDir.resolve(storedFileName);
            int idx = 1;
            while (Files.exists(targetPath)) {
                storedFileName = baseName + "(" + (idx++) + ")" + ext;
                targetPath = uploadDir.resolve(storedFileName);
            }

            if (media.isBinary()) {
                Files.write(targetPath, media.getByteData());
            } else {
                try (InputStream in = media.getStreamData()) {
                    Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            // Enregistrer le lien dans la table g_liste_dossier_concours_candidat
            ListeDossierConcoursCandidat entity = new ListeDossierConcoursCandidat();
            entity.setCandidatId(selectedCandidat.getCandidatId());
            entity.setDocumentConcoursId(selectedDocumentConcours.getDocumentConcoursId());
            entity.setEtatDocument(DocumentValidationEtat.EN_COURS.getCode());
            entity.setRemarque("En attente de validation");
            entity.setRemarqueFacultatif(targetPath.toString());
            entity.setActif(1);
            entity.setVersion(1);
            ListeDossierConcoursCandidatGestion.save(entity);

            Messagebox.show("✅ Document de concours importé avec succès", "Succès", Messagebox.OK, Messagebox.INFORMATION);
            this.pendingMedia = null;
            this.pendingFileName = null;
        } catch (Exception ex) {
            Messagebox.show("Erreur lors de l'upload du fichier.", "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private String sanitizeSlug(String s) {
        if (s == null) return "";
        String slug = s.trim().toLowerCase();
        slug = slug.replaceAll("[\\s]+", "_");
        slug = slug.replaceAll("[^a-z0-9_]+", "");
        return slug;
    }

    private String sanitizeKeepPipes(String s) {
        if (s == null) return "";
        String t = s.trim();
        // Allow letters, numbers and pipe separators
        t = t.replaceAll("[^A-Za-z0-9|]+", "");
        return t;
    }
}
