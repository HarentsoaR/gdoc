package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Filiere;
import mg.md2i.gedi.entity.Promotion;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionViewModel {

    private static final Logger log = LoggerFactory.getLogger(PromotionViewModel.class);

    private List<Promotion> promotionList;
    private List<Filiere> filiereList;
    private String searchQuery;
    private Promotion currentPromotion;

    @Init
    public void init(@ExecutionArgParam("promotionToManage") Promotion promotionToManage) {
        // This list is needed for both the list view filter and the form
        this.filiereList = FiliereGestion.findAll();

        if (promotionToManage != null) {
            // This block runs for both new.zul (add) and edit.zul (edit)
            this.currentPromotion = promotionToManage;

            // For safety, reload an existing entity to get all its data
            if (currentPromotion.getPromotionId() != null) {
                this.currentPromotion = PromotionGestion.findById(currentPromotion.getPromotionId());
                log.info("[PromotionVM] Edit mode for id={} => {}", currentPromotion.getPromotionId(), currentPromotion);
            } else {
                log.info("[PromotionVM] New mode => empty promotion initialized");
            }
        } else {
            // This block only runs for list.zul
            log.info("[PromotionVM] List mode => load all promotions");
            loadPromotionList();
        }

        // ** THE FIX **
        // Final safeguard: ensure currentPromotion is never null to prevent binding errors.
        // This is crucial for the list view where no promotionToManage is passed.
        if (this.currentPromotion == null) {
            this.currentPromotion = new Promotion();
            log.info("[PromotionVM] Safety init => new empty currentPromotion created for list view context");
        }
    }

    @GlobalCommand
    @NotifyChange("promotionList")
    public void refreshPromotionList() {
        loadPromotionList();
    }

    // --- COMMANDS ---

    @Command
    public void addPromotion() {
        Promotion newPromotion = new Promotion();
        // Pre-select the first filiere as a default value
        if (filiereList != null && !filiereList.isEmpty()) {
            newPromotion.setFiliere(filiereList.get(0));
        }
        Map<String, Object> args = new HashMap<>();
        args.put("promotionToManage", newPromotion);
        navigateTo("/documents/views/promotion/new.zul", "Administration", "Nouvelle Promotion", args);
    }

    @Command
    public void edit(@BindingParam("promotionToEdit") Promotion promotion) {
        Map<String, Object> args = new HashMap<>();
        args.put("promotionToManage", promotion);
        Window window = (Window) Executions.createComponents("/documents/views/promotion/edit.zul", null, args);
        window.doModal();
    }

    @Command
    public void savePromotion(@ContextParam(ContextType.VIEW) Component view) {
        if (currentPromotion == null) {
            Messagebox.show("Erreur : Aucune promotion à sauvegarder.", "Erreur", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        // Ensure the foreign key ID is set from the selected object before saving
        if (currentPromotion.getFiliere() != null) {
            currentPromotion.setFiliereId(currentPromotion.getFiliere().getFiliereId());
        }

        normalizePromotionLibelle();

        if (PromotionGestion.existsActiveDuplicate(currentPromotion)) {
            Messagebox.show("Une promotion avec ce numéro, cette filière et cette année existe déjà.", "Duplication",
                    Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        PromotionGestion.save(this.currentPromotion);
        Messagebox.show("Promotion sauvegardée avec succès!", "Succès", Messagebox.OK, Messagebox.INFORMATION);

        if (view instanceof Window) { // We are in the EDIT modal
            BindUtils.postGlobalCommand(null, null, "refreshPromotionList", null);
            view.detach();
        } else { // We are on the NEW page
            navigateToList();
        }
    }

    private void normalizePromotionLibelle() {
        String numero = currentPromotion.getNumeroPromotion() != null ? currentPromotion.getNumeroPromotion().trim() : "";
        if (!numero.isEmpty()) {
            currentPromotion.setNumeroPromotion(numero.toUpperCase());
            currentPromotion.setLibelle(numero.toUpperCase() + " EME PROMOTION");
        }
    }
    
    @Command
    public void cancel(@ContextParam(ContextType.VIEW) Component view) {
        if (view instanceof Window) { // We are in the EDIT modal
            view.detach();
        } else { // We are on the NEW page
            navigateToList();
        }
    }

    @Command
    @NotifyChange("promotionList")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadPromotionList();
        } else {
            promotionList = PromotionGestion.search(searchQuery);
        }
    }
    
    @Command
    public void deletePromotion(@BindingParam("id") Integer id) {
        Messagebox.show("Êtes-vous sûr de vouloir supprimer cette promotion ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        PromotionGestion.delete(id);
                        refreshPromotionList(); // This correctly triggers the @GlobalCommand
                        Messagebox.show("Promotion supprimée avec succès.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                    }
                });
    }

    // --- PRIVATE HELPERS ---

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
        navigateTo("/documents/views/promotion/list.zul", "Administration", "Promotions", null);
    }

    private void loadPromotionList() {
        // Assuming you want all promotions, not just active ones, for the list.
        // If you only want active ones, PromotionGestion.findAllActive() is correct.
        this.promotionList = PromotionGestion.findAll(); 
    }

    // --- GETTERS & SETTERS ---
    public List<Promotion> getPromotionList() { return promotionList; }
    public List<Filiere> getFiliereList() { return filiereList; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public Promotion getCurrentPromotion() { return currentPromotion; }
    public void setCurrentPromotion(Promotion currentPromotion) { this.currentPromotion = currentPromotion; }
}
