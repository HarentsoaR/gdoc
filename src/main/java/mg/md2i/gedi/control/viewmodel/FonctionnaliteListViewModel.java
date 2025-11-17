package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Fonctionnalite;
import mg.md2i.gedi.gestionmetier.FonctionnaliteGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FonctionnaliteListViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(FonctionnaliteListViewModel.class);

    private List<Fonctionnalite> fonctionnalites = new ArrayList<>();
    private String searchQuery = "";

    @Init
    public void init() {
        loadFonctionnalitesInternal();
    }

    @GlobalCommand
    @NotifyChange("fonctionnalites")
    public void refreshFonctionnaliteList() {
        loadFonctionnalitesInternal();
    }

    @Command
    @NotifyChange("fonctionnalites")
    public void loadFonctionnalites() {
        this.searchQuery = "";
        loadFonctionnalitesInternal();
    }

    @Command
    @NotifyChange("fonctionnalites")
    public void searchFonctionnalites() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadFonctionnalitesInternal();
        } else {
            fonctionnalites = FonctionnaliteGestion.searchFonctionnalites(searchQuery);
        }
    }

    @Command
    public void openNewFonctionnaliteModal() {
        Window window = (Window) Executions.createComponents("/admin/views/fonctionnalites/edit.zul", null, null);
        window.doModal();
    }

    @Command
    public void openEditFonctionnaliteModal(@BindingParam("fonctionnalite") Fonctionnalite fonctionnalite) {
        Map<String, Object> args = new HashMap<>();
        args.put("fonctionnaliteToEdit", fonctionnalite);
        Window window = (Window) Executions.createComponents("/admin/views/fonctionnalites/edit.zul", null, args);
        window.doModal();
    }

    @Command
    public void deleteFonctionnalite(@BindingParam("fonctionnaliteId") Integer fonctionnaliteId) {
        if (fonctionnaliteId == null) return;
        Messagebox.show("Supprimer cette fonctionnalité ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if (Messagebox.ON_YES.equals(event.getName())) {
                        try {
                            FonctionnaliteGestion.deleteFonctionnalite(fonctionnaliteId);
                            loadFonctionnalitesInternal();
                            refreshFonctionnaliteList();
                            Messagebox.show("Fonctionnalité supprimée avec succès", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                        } catch (Exception e) {
                            LOG.error("Erreur de suppression de la fonctionnalité", e);
                            Messagebox.show("Échec de la suppression.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                        }
                    }
                });
    }

    private void loadFonctionnalitesInternal() {
        try {
            fonctionnalites = FonctionnaliteGestion.findAllFonctionnalites();
        } catch (Exception e) {
            LOG.error("Impossible de charger les fonctionnalités", e);
            fonctionnalites = new ArrayList<>();
        }
    }

    public List<Fonctionnalite> getFonctionnalites() { return fonctionnalites; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
}