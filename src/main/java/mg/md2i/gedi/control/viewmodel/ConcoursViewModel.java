package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Concours;
import mg.md2i.gedi.gestionmetier.ConcoursGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class ConcoursViewModel {

    private List<Concours> concours;
    private String searchQuery;
    private Concours selected;

    @Init
    public void init() {
        concours = ConcoursGestion.findAll();
    }

    public List<Concours> getConcours() { return concours; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
    public Concours getSelected() { return selected; }
    public void setSelected(Concours c) { this.selected = c; }

    @Command @NotifyChange("concours")
    public void refresh() { concours = ConcoursGestion.findAll(); }

    @Command @NotifyChange("concours")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) refresh();
        else concours = ConcoursGestion.search(searchQuery);
    }

    @Command @NotifyChange("concours")
    public void save(@BindingParam("entity") Concours c) {
        ConcoursGestion.save(c);
        refresh();
    }

    @Command @NotifyChange("concours")
    public void delete(@BindingParam("id") Integer id) {
        ConcoursGestion.delete(id);
        refresh();
    }
}


