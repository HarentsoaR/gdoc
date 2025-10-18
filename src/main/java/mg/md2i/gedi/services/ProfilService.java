package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Profil;

import java.util.List;

public interface ProfilService {

    List<Profil> getAllProfils();
    Profil getProfilById(Integer id);
    void saveProfil(Profil profil);
    void deleteProfil(Integer id);
    List<Profil> searchProfils(String query);
}
