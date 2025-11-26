package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.entity.Filiere;
import mg.md2i.gedi.gestionmetier.FiliereGestion;
import mg.md2i.gedi.gestionmetier.ProfilGestion;
import mg.md2i.gedi.entity.Services;
import mg.md2i.gedi.gestionmetier.ServiceGestion;
import mg.md2i.gedi.session.ProfilSessionData;
import org.zkoss.bind.annotation.*;
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import java.util.*;

public class ProfilViewModel {

    private List<Profil> profils = new ArrayList<>();
    private Profil currentProfil = new Profil();
    private String searchQuery = "";
    private List<Filiere> filieres = new ArrayList<>();
    private Filiere selectedFiliere;
    private List<Services> services = new ArrayList<>();
    private Services selectedService;
    private boolean loaded = false;

    @Init
    public void init() {

        if (loaded) return;
        loaded = true;

        loadProfilsInternal();
        loadFilieres();
        loadServices();

        String mode = (String) Executions.getCurrent().getAttribute("profilPageMode");
        Profil p = ProfilSessionData.getProfilToEdit();
        ProfilSessionData.clear();

        if ("edit".equals(mode) && p != null) {
            // reload to ensure associations are present (filiere)
            currentProfil = ProfilGestion.findById(p.getProfilId());
        } else if ("new".equals(mode)) {
            currentProfil = new Profil();
        }

        syncSelectedFiliere();
        syncSelectedService();
        applySelectedFiliereToProfil();
        applySelectedServiceToProfil();
    }

    private void loadProfilsInternal() {
        profils = ProfilGestion.findAllProfils();
    }

    private void loadFilieres() {
        filieres = FiliereGestion.findAll();
    }

    private void loadServices() {
        services = ServiceGestion.findAllServices();
    }

    private void syncSelectedFiliere() {
        if (currentProfil != null && currentProfil.getFiliereId() != null) {
            selectedFiliere = filieres.stream()
                    .filter(f -> currentProfil.getFiliereId().equals(f.getFiliereId()))
                    .findFirst()
                    .orElse(null);
        }
        if (selectedFiliere == null && !filieres.isEmpty()) {
            selectedFiliere = filieres.get(0);
        }
    }

    private void syncSelectedService() {
        if (currentProfil != null && currentProfil.getServiceId() != null) {
            selectedService = services.stream()
                    .filter(s -> currentProfil.getServiceId().equals(s.getServiceId()))
                    .findFirst()
                    .orElse(null);
        }
        if (selectedService == null && !services.isEmpty()) {
            selectedService = services.get(0);
        }
    }

    private void applySelectedFiliereToProfil() {
        if (currentProfil == null || selectedFiliere == null) return;
        currentProfil.setFiliereId(selectedFiliere.getFiliereId());
        currentProfil.setFiliere(selectedFiliere.getSysId());
        currentProfil.setFiliereObj(selectedFiliere);
        currentProfil.setSysid(clampSysId(selectedFiliere.getSysId()));
    }

    private void applySelectedServiceToProfil() {
        if (currentProfil == null) return;
        if (selectedService != null) {
            currentProfil.setServiceId(selectedService.getServiceId());
        } else {
            currentProfil.setServiceId(null);
        }
    }

    private String clampSysId(String value) {
        if (value == null) return null;
        String cleaned = value.trim().toUpperCase();
        if (cleaned.isEmpty()) return null;
        return cleaned.length() > 5 ? cleaned.substring(0, 5) : cleaned;
    }

    @Command
    @NotifyChange("profils")
    public void loadProfils() {
        loadProfilsInternal();
    }

    @Command
    @NotifyChange("profils")
    public void searchProfils() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadProfilsInternal();
        } else {
            profils = ProfilGestion.searchProfils(searchQuery);
        }
    }

    @Command
    public void openNewProfil() {
        Executions.getCurrent().setAttribute("profilPageMode", "new");
        ProfilSessionData.clear();

        Map<String,Object> args = new HashMap<>();
        args.put("view", "/admin/views/profils/new.zul");
        args.put("section", "Profils");
        args.put("page", "Nouveau");

        BindUtils.postGlobalCommand(null, null, "navigateToAdmin", args);
    }

    @Command
    public void openEditProfil(@BindingParam("profil") Profil profil) {

        ProfilSessionData.setProfilToEdit(profil);
        Executions.getCurrent().setAttribute("profilPageMode", "edit");

        Map<String,Object> args = new HashMap<>();
        args.put("view", "/admin/views/profils/edit.zul");
        args.put("section", "Profils");
        args.put("page", "Modifier");

        BindUtils.postGlobalCommand(null, null, "navigateToAdmin", args);
    }

    @Command
    public void saveProfil() {

        if (currentProfil.getLibelle() == null || currentProfil.getLibelle().trim().isEmpty()) {
            Messagebox.show("Le libellé est requis.");
            return;
        }

        if (selectedFiliere == null) {
            Messagebox.show("Veuillez sélectionner une filière.");
            return;
        }

        applySelectedFiliereToProfil();
        applySelectedServiceToProfil();

        ProfilGestion.saveProfil(currentProfil);

        Map<String,Object> args = new HashMap<>();
        args.put("view", "/admin/views/profils/list.zul");
        args.put("section", "Profils");
        args.put("page", "Liste");

        BindUtils.postGlobalCommand(null, null, "navigateToAdmin", args);
    }

    @Command
    @NotifyChange("profils")
    public void deleteProfil(@BindingParam("profil") Profil profil) {

        Messagebox.show("Supprimer ce profil ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        ProfilGestion.deleteProfil(profil.getProfilId());
                        loadProfilsInternal();
                    }
                });
    }

    public List<Profil> getProfils() { return profils; }
    public Profil getCurrentProfil() { return currentProfil; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String s) { searchQuery = s; }
    public List<Filiere> getFilieres() { return filieres; }
    public Filiere getSelectedFiliere() { return selectedFiliere; }
    public void setSelectedFiliere(Filiere f) { this.selectedFiliere = f; }
    public List<Services> getServices() { return services; }
    public Services getSelectedService() { return selectedService; }
    public void setSelectedService(Services s) { this.selectedService = s; }
}
