package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.entity.CentreExamen;
import mg.md2i.gedi.entity.Concours;
import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.CandidatGestion;
import mg.md2i.gedi.gestionmetier.CentreExamenGestion;
import mg.md2i.gedi.gestionmetier.ConcoursGestion;
import mg.md2i.gedi.gestionmetier.DocumentConcoursGestion;
import mg.md2i.gedi.gestionmetier.ListeDossierConcoursCandidatGestion;
import mg.md2i.gedi.session.UserSessionData;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CandidatViewModel {

    private List<Candidat> candidats;
    private String searchQuery;
    private Candidat selected;
    private Candidat currentCandidat;
    private List<Concours> concoursList;
    private List<CentreExamen> centreExamenList;
    private boolean isEditMode = false;
    private int creationStep = 1;
    private boolean fonctionnaire = false;
    private String nextRangLabel = "-";
    private final List<MatrimonialOption> matrimonialOptions = Arrays.asList(
            new MatrimonialOption(1, "Célibataire"),
            new MatrimonialOption(2, "Marié(e)"),
            new MatrimonialOption(3, "Divorcé(e)"),
            new MatrimonialOption(4, "Veuf / Veuve")
    );
    private List<DocumentConcours> requiredDocuments = new ArrayList<>();
    private boolean detailVisible = false;
    private Candidat detailCandidat;
    private DetailSummary detailSummary = new DetailSummary();
    private List<DocumentEntry> detailDocuments = new ArrayList<>();
    private List<String> missingDocuments = new ArrayList<>();

    @Init
    public void init() {
        candidats = CandidatGestion.findAll();
        concoursList = ConcoursGestion.findAll();
        centreExamenList = CentreExamenGestion.findAll();
        requiredDocuments = DocumentConcoursGestion.findAll();
        prepareNewForm();
        restoreSessionContext();
    }

    private void restoreSessionContext() {
        Object modeAttr = Executions.getCurrent().getAttribute("candidatPageMode");
        if (modeAttr == null) {
            return;
        }
        String mode = String.valueOf(modeAttr);
        if ("edit".equals(mode)) {
            Candidat candidat = UserSessionData.getCandidatToEdit();
            loadCandidatForEdit(candidat);
            UserSessionData.clearCandidat();
        } else if ("new".equals(mode)) {
            prepareNewForm();
        }
        Executions.getCurrent().removeAttribute("candidatPageMode");
    }

    private void prepareNewForm() {
        currentCandidat = new Candidat();
        currentCandidat.setDateDepotCandidature(new Date());
        currentCandidat.setActif(1);
        currentCandidat.setVersion(1);
        creationStep = 1;
        fonctionnaire = false;
        nextRangLabel = "-";
        isEditMode = false;
        resetDetailContext();
    }

    private void resetDetailContext() {
        detailVisible = false;
        detailCandidat = null;
        detailSummary = new DetailSummary();
        detailDocuments = new ArrayList<>();
        missingDocuments = new ArrayList<>();
    }

    private void loadCandidatForEdit(Candidat candidat) {
        if (candidat == null) {
            prepareNewForm();
            return;
        }
        if (candidat.getCandidatId() != null) {
            Candidat persisted = CandidatGestion.findById(candidat.getCandidatId());
            if (persisted != null) {
                currentCandidat = persisted;
            } else {
                currentCandidat = candidat;
            }
        } else {
            currentCandidat = candidat;
        }
        if (currentCandidat.getConcours() == null && currentCandidat.getConcoursId() != null) {
            currentCandidat.setConcours(ConcoursGestion.findById(currentCandidat.getConcoursId()));
        }
        if (currentCandidat.getCentreExamen() == null && currentCandidat.getCentreExamenId() != null) {
            currentCandidat.setCentreExamen(CentreExamenGestion.findById(currentCandidat.getCentreExamenId()));
        }
        creationStep = 1;
        fonctionnaire = currentCandidat.getStatutFonctionnaire() != null && !currentCandidat.getStatutFonctionnaire().trim().isEmpty();
        nextRangLabel = currentCandidat.getRangConcours() != null ? currentCandidat.getRangConcours() : "-";
        isEditMode = true;
    }

    private void hydrateDetailRelations(Candidat candidat) {
        if (candidat == null) {
            return;
        }
        if (candidat.getConcours() == null && candidat.getConcoursId() != null) {
            candidat.setConcours(ConcoursGestion.findById(candidat.getConcoursId()));
        }
        if (candidat.getCentreExamen() == null && candidat.getCentreExamenId() != null) {
            candidat.setCentreExamen(CentreExamenGestion.findById(candidat.getCentreExamenId()));
        }
    }

    // ===========================
    // Getters / Setters
    // ===========================
    public List<Candidat> getCandidats() { return candidats; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
    public Candidat getSelected() { return selected; }
    public void setSelected(Candidat e) { this.selected = e; }
    public Candidat getCurrentCandidat() { return currentCandidat; }
    public void setCurrentCandidat(Candidat c) { this.currentCandidat = c; }
    public List<Concours> getConcoursList() { return concoursList; }
    public List<CentreExamen> getCentreExamenList() { return centreExamenList; }
    public boolean getIsEditMode() { return isEditMode; }
    public int getCreationStep() { return creationStep; }
    public void setCreationStep(int creationStep) { this.creationStep = creationStep; }
    public boolean isFonctionnaire() { return fonctionnaire; }
    public void setFonctionnaire(boolean fonctionnaire) {
        this.fonctionnaire = fonctionnaire;
        if (!fonctionnaire && currentCandidat != null) {
            currentCandidat.setStatutFonctionnaire(null);
            currentCandidat.setImFonctionnaire(null);
            currentCandidat.setDerniereFonction(null);
        }
        BindUtils.postNotifyChange(null, null, this, "fonctionnaire");
        BindUtils.postNotifyChange(null, null, this, "currentCandidat");
    }
    public String getNextRangLabel() { return nextRangLabel; }
    public List<MatrimonialOption> getMatrimonialOptions() { return matrimonialOptions; }
    public boolean isDetailVisible() { return detailVisible; }
    public Candidat getDetailCandidat() { return detailCandidat; }
    public DetailSummary getDetailSummary() { return detailSummary; }
    public List<DocumentEntry> getDetailDocuments() { return detailDocuments; }
    public List<String> getMissingDocuments() { return missingDocuments; }
    public String getDetailDisplayName() {
        if (detailCandidat == null) {
            return "";
        }
        String nom = detailCandidat.getNom() != null ? detailCandidat.getNom() : "";
        String prenom = detailCandidat.getPrenom() != null ? detailCandidat.getPrenom() : "";
        return (nom + " " + prenom).trim();
    }
    public String getDetailConcoursLabel() {
        if (detailCandidat != null && detailCandidat.getConcours() != null) {
            return detailCandidat.getConcours().getDisplayInfo();
        }
        return "-";
    }
    public String getDetailCentreLabel() {
        if (detailCandidat != null && detailCandidat.getCentreExamen() != null) {
            return detailCandidat.getCentreExamen().getLibelle();
        }
        return "-";
    }
    public String getDetailContactLabel() {
        if (detailCandidat != null && detailCandidat.getContactTelephonique() != null) {
            return detailCandidat.getContactTelephonique().toString();
        }
        return "-";
    }
    public String getDetailEmailLabel() {
        if (detailCandidat != null && detailCandidat.getMail() != null && !detailCandidat.getMail().trim().isEmpty()) {
            return detailCandidat.getMail();
        }
        return "-";
    }
    public String getDetailRegistrationLabel() {
        if (detailCandidat == null) {
            return "-";
        }
        if (detailCandidat.getNumInscription() != null) {
            return String.valueOf(detailCandidat.getNumInscription());
        }
        if (detailCandidat.getNumeroEnregistrement() != null) {
            return detailCandidat.getNumeroEnregistrement();
        }
        return "-";
    }
    public MatrimonialOption getSelectedMatrimonialOption() {
        if (currentCandidat == null || currentCandidat.getSituationMatrimoniale() == null) return null;
        return matrimonialOptions.stream()
                .filter(opt -> opt.getCode().equals(currentCandidat.getSituationMatrimoniale()))
                .findFirst()
                .orElse(null);
    }
    public void setSelectedMatrimonialOption(MatrimonialOption option) {
        if (currentCandidat == null) return;
        currentCandidat.setSituationMatrimoniale(option != null ? option.getCode() : null);
        if (!isShowSpouseFields()) {
            currentCandidat.setNomConjoint(null);
            currentCandidat.setPrenomConjoint(null);
            currentCandidat.setProfessionConjoint(null);
        }
        BindUtils.postNotifyChange(null, null, this, "currentCandidat");
        BindUtils.postNotifyChange(null, null, this, "showSpouseFields");
        BindUtils.postNotifyChange(null, null, this, "selectedMatrimonialOption");
    }
    public boolean isShowSpouseFields() {
        if (currentCandidat == null || currentCandidat.getSituationMatrimoniale() == null) return false;
        return currentCandidat.getSituationMatrimoniale() != 1;
    }

    public String getSexeValueBinding() {
        if (currentCandidat == null || currentCandidat.getSexe() == null) {
            return null;
        }
        return String.valueOf(currentCandidat.getSexe());
    }

    public void setSexeValueBinding(String value) {
        if (currentCandidat == null) return;
        if (value == null || value.trim().isEmpty()) {
            currentCandidat.setSexe(null);
        } else {
            try {
                currentCandidat.setSexe(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                currentCandidat.setSexe(null);
            }
        }
    }


    // ===========================
    // Commandes principales
    // ===========================

    @Command @NotifyChange({"candidats", "detailVisible", "detailCandidat", "detailSummary", "detailDocuments", "missingDocuments"})
    public void refresh() {
        candidats = CandidatGestion.findAll();
        resetDetailContext();
    }

    @Command @NotifyChange({"candidats", "detailVisible", "detailCandidat", "detailSummary", "detailDocuments", "missingDocuments"})
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            refresh();
        } else {
            candidats = CandidatGestion.searchByNom(searchQuery);
            resetDetailContext();
        }
    }

    @Command
    public void goToCandidatStep(@BindingParam("step") int step) {
        this.creationStep = step;
        BindUtils.postNotifyChange(null, null, this, "creationStep");
    }

    @Command @NotifyChange({"candidats", "currentCandidat", "isEditMode", "creationStep", "fonctionnaire", "nextRangLabel"})
    public void newCandidat() {
        UserSessionData.clearCandidat();
        Executions.getCurrent().setAttribute("candidatPageMode", "new");
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal",
            new HashMap<String, Object>() {{
                put("view", "/documents/views/candidats/new.zul");
                put("label", "Nouveau Candidat");
            }});
    }

    @Command @NotifyChange({"candidats", "currentCandidat", "isEditMode", "creationStep", "fonctionnaire", "nextRangLabel"})
    public void editCandidat(@BindingParam("candidat") Candidat candidat) {
        UserSessionData.setCandidatToEdit(candidat);
        Executions.getCurrent().setAttribute("candidatPageMode", "edit");
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal",
            new HashMap<String, Object>() {{
                put("view", "/documents/views/candidats/edit.zul");
                put("label", "Modifier Candidat");
            }});
    }

    @Command("saveCandidat") @NotifyChange("candidats")
    public void save() {
        try {
            // Your original debug messages
            System.out.println("=== DEBUG SAVE CANDIDAT ===");
            System.out.println("Nom: " + currentCandidat.getNom());
            System.out.println("Concours: " + (currentCandidat.getConcours() != null ? currentCandidat.getConcours().getConcoursId() : "null"));
            System.out.println("CentreExamen: " + (currentCandidat.getCentreExamen() != null ? currentCandidat.getCentreExamen().getCentreExamenId() : "null"));
            System.out.println("===========================");

            // Your validation logic (unchanged)
            if (currentCandidat.getNom() == null || currentCandidat.getNom().trim().isEmpty()) {
                Messagebox.show("Le nom est obligatoire", "Erreur", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            if (currentCandidat.getSexe() == null) {
                Messagebox.show("Le sexe est obligatoire.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                creationStep = 1;
                BindUtils.postNotifyChange(null, null, this, "creationStep");
                return;
            }
            if (currentCandidat.getConcours() == null && currentCandidat.getConcoursId() == null) {
                Messagebox.show("Le concours est obligatoire.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            if (currentCandidat.getCentreExamen() == null && currentCandidat.getCentreExamenId() == null) {
                Messagebox.show("Le centre d'examen est obligatoire.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            
            // Your ID propagation logic (unchanged)
            if (currentCandidat.getConcours() != null) {
                currentCandidat.setConcoursId(currentCandidat.getConcours().getConcoursId());
            }
            if (currentCandidat.getCentreExamen() != null) {
                currentCandidat.setCentreExamenId(currentCandidat.getCentreExamen().getCentreExamenId());
            }

            // ================== THE FIX ==================
            // I have commented out the line that causes the error.
             CandidatGestion.save(currentCandidat); // <-- CHANGED
            
            // I added this log so you can see the data in your console and verify it's correct.
            System.out.println("--- DATABASE CALL DISABLED. Data to save: " + currentCandidat.toString()); // <-- ADDED FOR DEBUGGING

            // I changed the message to make it clear this is a test.
//            Messagebox.show("✅ TEST: Sauvegarde simulée avec succès", "Succès", Messagebox.OK, Messagebox.INFORMATION); // <-- CHANGED
            // =============================================

            // Your navigation logic (unchanged)
            BindUtils.postGlobalCommand(null, null, "navigateToGlobal",
                new HashMap<String, Object>() {{
                    put("view", "/documents/views/candidats/list.zul");
                    put("label", "Gestion des Candidats");
                }});

        } catch (Exception e) {
            e.printStackTrace();
            Messagebox.show("Erreur lors de la sauvegarde: " + e.getMessage(), "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command @NotifyChange({"candidats", "detailVisible", "detailCandidat", "detailSummary", "detailDocuments", "missingDocuments"})
    public void delete(@BindingParam("id") Integer id) {
        try {
            Messagebox.show("Êtes-vous sûr de vouloir supprimer ce candidat ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if ("onYes".equals(event.getName())) {
                        CandidatGestion.delete(id);
                        refresh();
                        resetDetailContext();
                        Messagebox.show("✅ Candidat supprimé avec succès", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                    }
                });
        } catch (Exception e) {
            Messagebox.show("Erreur lors de la suppression: " + e.getMessage(), "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command("cancelCandidat")
    public void cancel() {
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal",
            new HashMap<String, Object>() {{
                put("view", "/documents/views/candidats/list.zul");
                put("label", "Gestion des Candidats");
            }});
    }

    @Command
    @NotifyChange({"detailVisible", "detailCandidat", "detailSummary", "detailDocuments", "missingDocuments"})
    public void viewCandidat(@BindingParam("candidat") Candidat candidat) {
        if (candidat == null || candidat.getCandidatId() == null) {
            resetDetailContext();
            return;
        }
        Candidat persisted = CandidatGestion.findById(candidat.getCandidatId());
        detailCandidat = persisted != null ? persisted : candidat;
        hydrateDetailRelations(detailCandidat);

        List<ListeDossierConcoursCandidat> dossiers = ListeDossierConcoursCandidatGestion.findByCandidatId(detailCandidat.getCandidatId());
        detailDocuments = dossiers.stream()
                .map(DocumentEntry::new)
                .sorted((a, b) -> {
                    if (a.getDocumentLabel() == null && b.getDocumentLabel() == null) return 0;
                    if (a.getDocumentLabel() == null) return 1;
                    if (b.getDocumentLabel() == null) return -1;
                    return a.getDocumentLabel().compareToIgnoreCase(b.getDocumentLabel());
                })
                .collect(Collectors.toList());
        detailSummary = DetailSummary.from(dossiers);

        Set<Integer> providedDocIds = dossiers.stream()
                .map(ListeDossierConcoursCandidat::getDocumentConcoursId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        missingDocuments = requiredDocuments.stream()
                .filter(doc -> doc.getDocumentConcoursId() != null && !providedDocIds.contains(doc.getDocumentConcoursId()))
                .map(DocumentConcours::getLibelle)
                .collect(Collectors.toList());

        detailVisible = true;
    }

    @Command
    @NotifyChange({"detailVisible", "detailCandidat", "detailSummary", "detailDocuments", "missingDocuments"})
    public void closeDetail() {
        resetDetailContext();
    }

    @Command
    public void downloadDocument(@BindingParam("doc") DocumentEntry doc) {
        if (doc == null || doc.getPath() == null) {
            return;
        }
        try {
            File file = new File(doc.getPath());
            if (!file.exists()) {
                Clients.showNotification("Fichier introuvable", "warning", null, "top_center", 2000);
                return;
            }
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            Filedownload.save(file, contentType);
        } catch (Exception e) {
            Clients.showNotification("Impossible de télécharger le fichier.", "error", null, "top_center", 2500);
        }
    }

    // ===================================================
    // Compatibilité : méthodes appelées depuis DocumentViewModel
    // (ou autres) — elles acceptent une Combobox et mettent
    // à jour currentCandidat de façon sûre.
    // ===================================================

    @Command @NotifyChange({"currentCandidat", "nextRangLabel"})
    public void onConcoursChange(@BindingParam("self") Combobox combobox) {
        if (combobox == null) return;
        Comboitem item = combobox.getSelectedItem();
        if (item == null) return;

        Object val = item.getValue(); // peut être Concours ou Integer selon le zul
        if (val instanceof Concours) {
            Concours c = (Concours) val;
            currentCandidat.setConcours(c);
            currentCandidat.setConcoursId(c.getConcoursId());
        } else if (val instanceof Integer) {
            Integer id = (Integer) val;
            currentCandidat.setConcoursId(id);
            // essayer de charger l'objet si tu veux la préselection dans l'UI
            try {
                Concours c = ConcoursGestion.findById(id);
                currentCandidat.setConcours(c);
            } catch (Exception ex) {
                // silent fallback : on a au moins l'id
            }
        } else {
            // fallback : essayer d'interpréter label ou attribut
            Object attr = item.getAttribute("model");
            if (attr instanceof Concours) {
                Concours c = (Concours) attr;
                currentCandidat.setConcours(c);
                currentCandidat.setConcoursId(c.getConcoursId());
            }
        }
        updateNextRangLabel();
    }

    @Command @NotifyChange("currentCandidat")
    public void onCentreExamenChange(@BindingParam("self") Combobox combobox) {
        if (combobox == null) return;
        Comboitem item = combobox.getSelectedItem();
        if (item == null) return;

        Object val = item.getValue();
        if (val instanceof CentreExamen) {
            CentreExamen ce = (CentreExamen) val;
            currentCandidat.setCentreExamen(ce);
            currentCandidat.setCentreExamenId(ce.getCentreExamenId());
        } else if (val instanceof Integer) {
            Integer id = (Integer) val;
            currentCandidat.setCentreExamenId(id);
            try {
                CentreExamen ce = CentreExamenGestion.findById(id);
                currentCandidat.setCentreExamen(ce);
            } catch (Exception ex) {
                // fallback silencieux
            }
        } else {
            Object attr = item.getAttribute("model");
            if (attr instanceof CentreExamen) {
                CentreExamen ce = (CentreExamen) attr;
                currentCandidat.setCentreExamen(ce);
                currentCandidat.setCentreExamenId(ce.getCentreExamenId());
            }
        }
    }

    private void updateNextRangLabel() {
        if (currentCandidat == null || currentCandidat.getConcoursId() == null) {
            nextRangLabel = "-";
            if (currentCandidat != null) currentCandidat.setRangConcours(null);
            return;
        }
        int base = 0;
        List<Candidat> existing = CandidatGestion.findByConcours(currentCandidat.getConcoursId());
        if (existing != null) {
            base = existing.size();
        }
        int next = base + 1;
        Integer max = null;
        if (currentCandidat.getConcours() != null) {
            max = currentCandidat.getConcours().getNombrePoste();
        }
        if (currentCandidat.getRangConcours() == null || !currentCandidat.getRangConcours().equals(String.valueOf(next))) {
            currentCandidat.setRangConcours(String.valueOf(next));
        }
        nextRangLabel = max != null ? next + " / " + max : String.valueOf(next);
    }

    public static class DocumentEntry {
        private final ListeDossierConcoursCandidat dossier;
        private final DocumentValidationEtat etat;

        public DocumentEntry(ListeDossierConcoursCandidat dossier) {
            this.dossier = dossier;
            this.etat = DocumentValidationEtat.fromCode(dossier.getEtatDocument());
        }

        public String getDocumentLabel() {
            DocumentConcours doc = dossier.getDocumentConcours();
            return doc != null ? doc.getLibelle() : "-";
        }

        public String getEtatLabel() {
            return etat.getLabel();
        }

        public String getEtatSclass() {
            return "status-label " + etat.getChipSclass();
        }

        public String getRemarque() {
            return dossier.getRemarque();
        }

        public Integer getVersion() {
            return dossier.getVersion();
        }

        public boolean hasAttachment() {
            String path = dossier.getRemarqueFacultatif();
            return path != null && !path.trim().isEmpty();
        }

        public boolean getHasAttachment() {
            return hasAttachment();
        }

        public String getPath() {
            return dossier.getRemarqueFacultatif();
        }

        public String getFileName() {
            String path = dossier.getRemarqueFacultatif();
            if (path == null) return "-";
            int idx = path.lastIndexOf(File.separator);
            return idx >= 0 ? path.substring(idx + 1) : path;
        }
    }

    public static class DetailSummary {
        private long total;
        private long valides;
        private long enCours;
        private long rejetes;

        public static DetailSummary from(List<ListeDossierConcoursCandidat> dossiers) {
            DetailSummary summary = new DetailSummary();
            if (dossiers == null) {
                return summary;
            }
            summary.total = dossiers.size();
            summary.valides = dossiers.stream().filter(d -> Objects.equals(d.getEtatDocument(), DocumentValidationEtat.VALIDE.getCode())).count();
            summary.enCours = dossiers.stream().filter(d -> Objects.equals(d.getEtatDocument(), DocumentValidationEtat.EN_COURS.getCode())).count();
            summary.rejetes = dossiers.stream().filter(d -> Objects.equals(d.getEtatDocument(), DocumentValidationEtat.REJETE.getCode())).count();
            return summary;
        }

        public long getTotal() { return total; }
        public long getValides() { return valides; }
        public long getEnCours() { return enCours; }
        public long getRejetes() { return rejetes; }

        public String getProgressLabel() {
            if (total == 0) {
                return "0% validés";
            }
            int percent = (int)Math.round((double)valides / total * 100);
            return percent + "% validés";
        }

        public int getProgressPercent() {
            if (total == 0) {
                return 0;
            }
            return (int)Math.round((double)valides / total * 100);
        }

        public String getProgressStyle() {
            return "width:" + getProgressPercent() + "%";
        }
    }

    public static class MatrimonialOption {
        private final Integer code;
        private final String label;

        public MatrimonialOption(Integer code, String label) {
            this.code = code;
            this.label = label;
        }

        public Integer getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
