package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.CentreConcours;
import mg.md2i.gedi.gestionmetier.CentreConcoursGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class CentreConcoursViewModel {

    private List<CentreConcours> centres;
    private String searchQuery;
    private CentreConcours selected;

    @Init
    public void init() {
        centres = CentreConcoursGestion.findAll();
    }

    public List<CentreConcours> getCentres() { return centres; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
    public CentreConcours getSelected() { return selected; }
    public void setSelected(CentreConcours c) { this.selected = c; }

    @Command @NotifyChange("centres")
    public void refresh() { centres = CentreConcoursGestion.findAll(); }

    @Command @NotifyChange("centres")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) refresh();
        else centres = CentreConcoursGestion.search(searchQuery);
    }

    @Command @NotifyChange("centres")
    public void save(@BindingParam("entity") CentreConcours c) {
        CentreConcoursGestion.save(c);
        refresh();
    }

    @Command @NotifyChange("centres")
    public void delete(@BindingParam("id") Integer id) {
        CentreConcoursGestion.delete(id);
        refresh();
    }
}


