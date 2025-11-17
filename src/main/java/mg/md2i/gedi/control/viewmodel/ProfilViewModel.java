package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.gestionmetier.ProfilGestion;
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
    private boolean loaded = false;

    @Init
    public void init() {

        if (loaded) return;
        loaded = true;

        loadProfilsInternal();

        String mode = (String) Executions.getCurrent().getAttribute("profilPageMode");
        Profil p = ProfilSessionData.getProfilToEdit();
        ProfilSessionData.clear();

        if ("edit".equals(mode) && p != null) {
            currentProfil = p;
        } else if ("new".equals(mode)) {
            currentProfil = new Profil();
        }
    }

    private void loadProfilsInternal() {
        profils = ProfilGestion.findAllProfils();
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
}
