package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.gestionmetier.ProfilGestion;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class ProfilViewModel {

    private List<Profil> profils;
    private Profil selectedProfil;
    private String searchQuery;

    @Init
    public void init() {
        profils = ProfilGestion.findAllProfils();
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public Profil getSelectedProfil() {
        return selectedProfil;
    }

    public void setSelectedProfil(Profil selectedProfil) {
        this.selectedProfil = selectedProfil;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Command @NotifyChange("profils")
    public void refresh() {
        profils = ProfilGestion.findAllProfils();
    }

    @Command @NotifyChange("profils")
    public void search() {
        if (searchQuery == null || searchQuery.isEmpty()) {
            profils = ProfilGestion.findAllProfils();
        } else {
            profils = ProfilGestion.searchProfils(searchQuery);
        }
    }

    @Command @NotifyChange("profils")
    public void save(@BindingParam("profil") Profil profil) {
        ProfilGestion.saveProfil(profil);
        profils = ProfilGestion.findAllProfils();
    }

    @Command @NotifyChange("profils")
    public void delete(@BindingParam("profil") Profil profil) {
        ProfilGestion.deleteProfil(profil.getProfilId());
        profils = ProfilGestion.findAllProfils();
    }
}
