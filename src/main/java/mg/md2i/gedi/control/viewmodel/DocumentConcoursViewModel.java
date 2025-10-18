package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.gestionmetier.DocumentConcoursGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class DocumentConcoursViewModel {

    private List<DocumentConcours> docs;
    private String searchQuery;
    private DocumentConcours selected;

    @Init
    public void init() {
        docs = DocumentConcoursGestion.findAll();
    }

    public List<DocumentConcours> getDocs() { return docs; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
    public DocumentConcours getSelected() { return selected; }
    public void setSelected(DocumentConcours d) { this.selected = d; }

    @Command @NotifyChange("docs")
    public void refresh() { docs = DocumentConcoursGestion.findAll(); }

    @Command @NotifyChange("docs")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) refresh();
        else docs = DocumentConcoursGestion.search(searchQuery);
    }

    @Command @NotifyChange("docs")
    public void save(@BindingParam("entity") DocumentConcours d) {
        DocumentConcoursGestion.save(d);
        refresh();
    }

    @Command @NotifyChange("docs")
    public void delete(@BindingParam("id") Integer id) {
        DocumentConcoursGestion.delete(id);
        refresh();
    }
}


