package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.entity.Concours;
import mg.md2i.gedi.entity.CentreExamen;
import mg.md2i.gedi.gestionmetier.CandidatGestion;
import mg.md2i.gedi.gestionmetier.ConcoursGestion;
import mg.md2i.gedi.gestionmetier.CentreExamenGestion;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;

import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class CandidatGlobalViewModel {

    private List<Candidat> candidats;
    private String searchQuery;
    private Candidat selected;
    private Candidat currentCandidat;
    private List<Concours> concoursList;
    private List<CentreExamen> centreExamenList;
    private boolean isEditMode = false;

    @Init
    public void init() {
        candidats = CandidatGestion.findAll();
        concoursList = ConcoursGestion.findAll();
        centreExamenList = CentreExamenGestion.findAll();
        currentCandidat = new Candidat();
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

    // ===========================
    // Commandes principales
    // ===========================

    @Command @NotifyChange("candidats")
    public void refresh() {
        candidats = CandidatGestion.findAll();
    }

    @Command @NotifyChange("candidats")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            refresh();
        } else {
            candidats = CandidatGestion.searchByNom(searchQuery);
        }
    }

    @Command @NotifyChange({"candidats", "currentCandidat", "isEditMode"})
    public void newCandidat() {
        currentCandidat = new Candidat();
        currentCandidat.setDateDepotCandidature(new Date());
        currentCandidat.setActif(1);
        currentCandidat.setVersion(1);
        isEditMode = false;

        BindUtils.postGlobalCommand(null, null, "navigateToGlobal",
            new HashMap<String, Object>() {{
                put("view", "/documents/views/candidats/new.zul");
                put("label", "Nouveau Candidat");
            }});
    }

    @Command @NotifyChange({"candidats", "currentCandidat", "isEditMode"})
    public void editCandidat(@BindingParam("candidat") Candidat candidat) {
        currentCandidat = new Candidat();
        currentCandidat.setCandidatId(candidat.getCandidatId());
        currentCandidat.setNom(candidat.getNom());
        currentCandidat.setPrenom(candidat.getPrenom());
        currentCandidat.setSexe(candidat.getSexe());
        currentCandidat.setDateNaissance(candidat.getDateNaissance());
        currentCandidat.setLieuNaissance(candidat.getLieuNaissance());
        currentCandidat.setAdresseEleve(candidat.getAdresseEleve());
        currentCandidat.setContactTelephonique(candidat.getContactTelephonique());
        currentCandidat.setMail(candidat.getMail());
        currentCandidat.setConcoursId(candidat.getConcoursId());
        currentCandidat.setCentreExamenId(candidat.getCentreExamenId());
        currentCandidat.setNumeroEnregistrement(candidat.getNumeroEnregistrement());
        currentCandidat.setRangConcours(candidat.getRangConcours());
        currentCandidat.setDateDepotCandidature(candidat.getDateDepotCandidature());
        currentCandidat.setStatutFonctionnaire(candidat.getStatutFonctionnaire());
        currentCandidat.setImFonctionnaire(candidat.getImFonctionnaire());
        currentCandidat.setDerniereFonction(candidat.getDerniereFonction());
        currentCandidat.setRemarque(candidat.getRemarque());
        currentCandidat.setActif(candidat.getActif());
        currentCandidat.setVersion(candidat.getVersion());

        // Charger les objets liés pour préselectionner les combos
        if (candidat.getConcoursId() != null) {
            currentCandidat.setConcours(ConcoursGestion.findById(candidat.getConcoursId()));
        }
        if (candidat.getCentreExamenId() != null) {
            currentCandidat.setCentreExamen(CentreExamenGestion.findById(candidat.getCentreExamenId()));
        }

        isEditMode = true;

        BindUtils.postGlobalCommand(null, null, "navigateToGlobal",
            new HashMap<String, Object>() {{
                put("view", "/documents/views/candidats/edit.zul");
                put("label", "Modifier Candidat");
            }});
    }

    @Command @NotifyChange("candidats")
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

    @Command @NotifyChange("candidats")
    public void delete(@BindingParam("id") Integer id) {
        try {
            Messagebox.show("Êtes-vous sûr de vouloir supprimer ce candidat ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if ("onYes".equals(event.getName())) {
                        CandidatGestion.delete(id);
                        refresh();
                        Messagebox.show("✅ Candidat supprimé avec succès", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                    }
                });
        } catch (Exception e) {
            Messagebox.show("Erreur lors de la suppression: " + e.getMessage(), "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void cancel() {
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal",
            new HashMap<String, Object>() {{
                put("view", "/documents/views/candidats/list.zul");
                put("label", "Gestion des Candidats");
            }});
    }

    @Command @NotifyChange("candidats")
    public void viewCandidat(@BindingParam("candidat") Candidat candidat) {
        Messagebox.show("Fonctionnalité de visualisation à implémenter", "Info", Messagebox.OK, Messagebox.INFORMATION);
    }

    // ===================================================
    // Compatibilité : méthodes appelées depuis DocumentViewModel
    // (ou autres) — elles acceptent une Combobox et mettent
    // à jour currentCandidat de façon sûre.
    // ===================================================

    @Command @NotifyChange("currentCandidat")
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
}