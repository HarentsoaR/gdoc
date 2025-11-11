package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.services.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionMetierProfil {

    @Autowired
    private ProfilService profilService;

    public List<Profil> getAllProfils() {
        return profilService.getAllProfils();
    }

    public Profil getProfilById(Integer id) {
        return profilService.getProfilById(id);
    }

    public void saveProfil(Profil profil) {
        profilService.saveProfil(profil);
    }

    public void deleteProfil(Integer id) {
        profilService.deleteProfil(id);
    }

    public List<Profil> searchProfils(String query) {
        return profilService.searchProfils(query);
    }

    // You can add more specific business logic methods here for Profil
    // For example, methods related to role-based access control, validation, etc.
}
