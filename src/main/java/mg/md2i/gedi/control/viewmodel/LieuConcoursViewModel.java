package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.LieuConcours;
import mg.md2i.gedi.gestionmetier.LieuConcoursGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class LieuConcoursViewModel {

	private List<LieuConcours> lieux;
	private String searchQuery;
	private LieuConcours selected;

	@Init
	public void init() {
		lieux = LieuConcoursGestion.findAll();
	}

	public List<LieuConcours> getLieux() { return lieux; }
	public String getSearchQuery() { return searchQuery; }
	public void setSearchQuery(String q) { this.searchQuery = q; }
	public LieuConcours getSelected() { return selected; }
	public void setSelected(LieuConcours e) { this.selected = e; }

	@Command @NotifyChange("lieux")
	public void refresh() { lieux = LieuConcoursGestion.findAll(); }

	@Command @NotifyChange("lieux")
	public void search() {
		if (searchQuery == null || searchQuery.trim().isEmpty()) refresh();
		else lieux = LieuConcoursGestion.search(searchQuery);
	}

	@Command @NotifyChange("lieux")
	public void save(@BindingParam("entity") LieuConcours e) {
		LieuConcoursGestion.save(e);
		refresh();
	}

	@Command @NotifyChange("lieux")
	public void delete(@BindingParam("id") Integer id) {
		LieuConcoursGestion.delete(id);
		refresh();
	}
}
