package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Filiere;
import mg.md2i.gedi.gestionmetier.FiliereGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.*;

public class FiliereViewModel {

    private static final Logger log = LoggerFactory.getLogger(FiliereViewModel.class);

    private List<Filiere> filiereList;
    private Filiere currentFiliere;
    private String searchQuery;
    private List<String> sysIdOptions;

    @Init
    public void init(@ExecutionArgParam("filiereToManage") Filiere filiereToManage) {
        sysIdOptions = Arrays.asList("MAGISTRAT", "GREFFIER", "TOUS");

        if (filiereToManage != null) {
            this.currentFiliere = filiereToManage;

            // reload if it's an existing filiere
            if (currentFiliere.getFiliereId() != null) {
                this.currentFiliere = FiliereGestion.findById(currentFiliere.getFiliereId());
                log.info("[FiliereVM] edit mode for id={} => {}", currentFiliere.getFiliereId(), currentFiliere);
            } else {
                log.info("[FiliereVM] new mode => empty filiere initialized");
            }
        } else {
            log.info("[FiliereVM] list mode => load all");
            loadFiliereList();
        }

        // final safeguard: always have a non-null instance for binding
        if (this.currentFiliere == null) {
            this.currentFiliere = new Filiere();
            this.currentFiliere.setSysId("MAGISTRAT");
            log.info("[FiliereVM] safety init => new empty currentFiliere created");
        }
    }

    @GlobalCommand
    @NotifyChange("filiereList")
    public void refreshFiliereList() {
        loadFiliereList();
    }

    @Command
    public void addFiliere() {
        Filiere newFiliere = new Filiere();
        newFiliere.setSysId("MAGISTRAT");

        Map<String, Object> args = new HashMap<>();
        args.put("filiereToManage", newFiliere);

        navigateTo("/documents/views/filiere/new.zul", "Administration", "Nouvelle Filière", args);
    }

    @Command
    public void edit(@BindingParam("filiereToEdit") Filiere filiere) {
        Map<String, Object> args = new HashMap<>();
        args.put("filiereToManage", filiere);

        Window window = (Window) Executions.createComponents("/documents/views/filiere/edit.zul", null, args);
        window.doModal();
    }

    @Command
    public void saveFiliere(@ContextParam(ContextType.VIEW) Component view) {
        if (currentFiliere == null) {
            Messagebox.show("Erreur : Aucune filière à sauvegarder.", "Erreur", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        FiliereGestion.save(currentFiliere);
        Messagebox.show("Filière sauvegardée avec succès!", "Succès", Messagebox.OK, Messagebox.INFORMATION);

        if (view instanceof Window) {
            BindUtils.postGlobalCommand(null, null, "refreshFiliereList", null);
            view.detach();
        } else {
            navigateToList();
        }
    }

    @Command
    public void cancel(@ContextParam(ContextType.VIEW) Component view) {
        if (view instanceof Window) {
            view.detach();
        } else {
            navigateToList();
        }
    }

    @Command
    @NotifyChange("filiereList")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) loadFiliereList();
        else filiereList = FiliereGestion.search(searchQuery);
    }

    @Command
    public void deleteFiliere(@BindingParam("id") Integer id) {
        Messagebox.show("Supprimer cette filière ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        FiliereGestion.delete(id);
                        refreshFiliereList();
                        Messagebox.show("Filière supprimée avec succès.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                    }
                });
    }

    private void navigateTo(String view, String section, String page, Map<String, Object> args) {
        Map<String, Object> globalArgs = new HashMap<>();
        globalArgs.put("view", view);
        globalArgs.put("section", section);
        globalArgs.put("page", page);
        if (args != null) globalArgs.putAll(args);
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal", globalArgs);
    }

    private void navigateToList() {
        navigateTo("/documents/views/filiere/list.zul", "Administration", "Filières", null);
    }

    private void loadFiliereList() {
        filiereList = FiliereGestion.findAll();
    }

    public List<Filiere> getFiliereList() { return filiereList; }
    public Filiere getCurrentFiliere() { return currentFiliere; }
    public void setCurrentFiliere(Filiere currentFiliere) { this.currentFiliere = currentFiliere; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public List<String> getSysIdOptions() { return sysIdOptions; }
}
