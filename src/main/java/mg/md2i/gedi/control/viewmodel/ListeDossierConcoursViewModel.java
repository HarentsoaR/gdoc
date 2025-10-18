package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.ListeDossierConcours;
import mg.md2i.gedi.gestionmetier.ListeDossierConcoursGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class ListeDossierConcoursViewModel {

    private List<ListeDossierConcours> items;
    private String searchQuery;
    private ListeDossierConcours selected;

    @Init
    public void init() {
        items = ListeDossierConcoursGestion.findAll();
    }

    public List<ListeDossierConcours> getItems() { return items; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
    public ListeDossierConcours getSelected() { return selected; }
    public void setSelected(ListeDossierConcours e) { this.selected = e; }

    @Command @NotifyChange("items")
    public void refresh() { items = ListeDossierConcoursGestion.findAll(); }

    @Command @NotifyChange("items")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) refresh();
        else items = ListeDossierConcoursGestion.searchByNom(searchQuery);
    }

    @Command @NotifyChange("items")
    public void save(@BindingParam("entity") ListeDossierConcours e) {
        ListeDossierConcoursGestion.save(e);
        refresh();
    }

    @Command @NotifyChange("items")
    public void delete(@BindingParam("id") Integer id) {
        ListeDossierConcoursGestion.delete(id);
        refresh();
    }
}


