package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Concours;
import mg.md2i.gedi.entity.Filiere;
import mg.md2i.gedi.entity.Promotion;
import mg.md2i.gedi.gestionmetier.ConcoursGestion;
import mg.md2i.gedi.gestionmetier.FiliereGestion;
import mg.md2i.gedi.gestionmetier.PromotionGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConcoursViewModel {

    private static final Logger log = LoggerFactory.getLogger(ConcoursViewModel.class);

    private List<Concours> concoursList;
    private Concours currentConcours;
    private String searchQuery;

    private List<Filiere> filiereList;
    private Filiere selectedFiliere;
    private List<Promotion> allPromotions;
    private List<Promotion> availablePromotions;

    private List<String> avisList;
    private List<String> numeroArreteList;

    @Init
    public void init(@ExecutionArgParam("concoursToManage") Concours concoursToManage) {
        filiereList = FiliereGestion.findAll();
        allPromotions = PromotionGestion.findAll();
        avisList = ConcoursGestion.findAllAvis();
        numeroArreteList = ConcoursGestion.findAllNumeros();

        if (concoursToManage != null) {
            this.currentConcours = concoursToManage;

            if (currentConcours.getConcoursId() != null) {
                log.info("[ConcoursVM] Edit mode for id={}", currentConcours.getConcoursId());
                this.currentConcours = ConcoursGestion.findById(currentConcours.getConcoursId());
                if (this.currentConcours.getPromotion() != null) {
                    this.selectedFiliere = this.currentConcours.getPromotion().getFiliere();
                    filterPromotionsByFiliere();
                }
            } else {
                log.info("[ConcoursVM] New mode initialized");
                this.availablePromotions = Collections.emptyList();
                this.currentConcours.setListePublier(false); // Default value for publication status
                this.currentConcours.setStatut(true);         // Default value for concours status (Active)
            }
        } else {
            log.info("[ConcoursVM] List mode => load all");
            loadConcoursList();
        }

        if (this.currentConcours == null) {
            this.currentConcours = new Concours();
            this.currentConcours.setListePublier(false); // Default value for safety init
            this.currentConcours.setStatut(true);         // Default value for safety init (Active)
            log.info("[ConcoursVM] Safety init => new empty currentConcours created");
        }
    }

    @GlobalCommand
    @NotifyChange("concoursList")
    public void refreshConcoursList() {
        loadConcoursList();
    }

    @Command
    public void addConcours() {
        Map<String, Object> args = new HashMap<>();
        args.put("concoursToManage", new Concours());
        navigateTo("/documents/views/concours/new.zul", "Concours", "Nouveau Concours", args);
    }

    @Command
    public void edit(@BindingParam("concours") Concours concoursToEdit) {
        Map<String, Object> args = new HashMap<>();
        args.put("concoursToManage", concoursToEdit);
        Window window = (Window) Executions.createComponents("/documents/views/concours/edit.zul", null, args);
        window.doModal();
    }

    @Command
    public void saveConcours(@ContextParam(ContextType.VIEW) Component view) {
        if (currentConcours.getPromotion() != null) {
            currentConcours.setPromotionId(currentConcours.getPromotion().getPromotionId());
        }

        ConcoursGestion.save(currentConcours);
        Messagebox.show("Concours sauvegardé avec succès!", "Succès", Messagebox.OK, Messagebox.INFORMATION);

        if (view instanceof Window) {
            BindUtils.postGlobalCommand(null, null, "refreshConcoursList", null);
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
    @NotifyChange("concoursList")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadConcoursList();
        } else {
            concoursList = ConcoursGestion.search(searchQuery);
        }
    }

    @Command
    public void deleteConcours(@BindingParam("id") Integer id) {
        Messagebox.show("Êtes-vous sûr de vouloir supprimer ce concours ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        ConcoursGestion.delete(id);
                        refreshConcoursList();
                        Messagebox.show("Concours supprimé avec succès.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                    }
                });
    }

    @Command
    @NotifyChange({"availablePromotions", "currentConcours"})
    public void onSelectFiliere() {
        currentConcours.setPromotion(null);
        filterPromotionsByFiliere();
    }

    private void filterPromotionsByFiliere() {
        if (selectedFiliere != null && allPromotions != null) {
            availablePromotions = allPromotions.stream()
                    .filter(p -> selectedFiliere.getFiliereId().equals(p.getFiliereId()))
                    .collect(Collectors.toList());
        } else {
            availablePromotions = Collections.emptyList();
        }
    }

    private void loadConcoursList() {
        concoursList = ConcoursGestion.findAll();
    }

    private void navigateTo(String view, String section, String page, Map<String, Object> args) {
        Map<String, Object> globalArgs = new HashMap<>();
        globalArgs.put("view", view);
        globalArgs.put("section", section);
        globalArgs.put("page", page);
        if (args != null) {
            globalArgs.putAll(args);
        }
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal", globalArgs);
    }

    private void navigateToList() {
        navigateTo("/documents/views/concours/list.zul", "Concours", "Liste des Concours", null);
    }

    public List<Concours> getConcoursList() { return concoursList; }
    public Concours getCurrentConcours() { return currentConcours; }
    public void setCurrentConcours(Concours currentConcours) { this.currentConcours = currentConcours; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public List<Filiere> getFiliereList() { return filiereList; }
    public Filiere getSelectedFiliere() { return selectedFiliere; }
    public void setSelectedFiliere(Filiere selectedFiliere) { this.selectedFiliere = selectedFiliere; }
    public List<Promotion> getAvailablePromotions() { return availablePromotions; }
    public List<String> getAvisList() { return avisList; }
    public List<String> getNumeroArreteList() { return numeroArreteList; }
}