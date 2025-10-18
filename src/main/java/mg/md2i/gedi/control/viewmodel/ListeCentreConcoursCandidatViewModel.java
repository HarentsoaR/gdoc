package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.ListeCentreConcoursCandidat;
import mg.md2i.gedi.gestionmetier.ListeCentreConcoursCandidatGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class ListeCentreConcoursCandidatViewModel {

    private List<ListeCentreConcoursCandidat> items;
    private String searchQuery; // optional free text not used yet
    private ListeCentreConcoursCandidat selected;

    @Init
    public void init() {
        items = ListeCentreConcoursCandidatGestion.findAll();
    }

    public List<ListeCentreConcoursCandidat> getItems() { return items; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
    public ListeCentreConcoursCandidat getSelected() { return selected; }
    public void setSelected(ListeCentreConcoursCandidat e) { this.selected = e; }

    @Command @NotifyChange("items")
    public void refresh() { items = ListeCentreConcoursCandidatGestion.findAll(); }

    @Command @NotifyChange("items")
    public void save(@BindingParam("entity") ListeCentreConcoursCandidat e) {
        ListeCentreConcoursCandidatGestion.save(e);
        refresh();
    }

    @Command @NotifyChange("items")
    public void delete(@BindingParam("id") Integer id) {
        ListeCentreConcoursCandidatGestion.delete(id);
        refresh();
    }
}


