package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.ConcoursPhase;
import mg.md2i.gedi.gestionmetier.ConcoursPhaseGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class ConcoursPhaseViewModel {

    private List<ConcoursPhase> phases;
    private String searchQuery;
    private ConcoursPhase selected;

    @Init
    public void init() {
        phases = ConcoursPhaseGestion.findAll();
    }

    public List<ConcoursPhase> getPhases() { return phases; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
    public ConcoursPhase getSelected() { return selected; }
    public void setSelected(ConcoursPhase c) { this.selected = c; }

    @Command @NotifyChange("phases")
    public void refresh() { phases = ConcoursPhaseGestion.findAll(); }

    @Command @NotifyChange("phases")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) refresh();
        else phases = ConcoursPhaseGestion.search(searchQuery);
    }

    @Command @NotifyChange("phases")
    public void save(@BindingParam("entity") ConcoursPhase c) {
        ConcoursPhaseGestion.save(c);
        refresh();
    }

    @Command @NotifyChange("phases")
    public void delete(@BindingParam("id") Integer id) {
        ConcoursPhaseGestion.delete(id);
        refresh();
    }
}


