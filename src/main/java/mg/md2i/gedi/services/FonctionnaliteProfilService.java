package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.FonctionnaliteProfil;

import java.util.List;

public interface FonctionnaliteProfilService {
    List<FonctionnaliteProfil> getFonctionnaliteProfilsByProfilId(Integer profilId);
    FonctionnaliteProfil getFonctionnaliteProfilByProfilIdAndFonctionnaliteId(Integer profilId, Integer fonctionnaliteId);
    void saveFonctionnaliteProfil(FonctionnaliteProfil fonctionnaliteProfil);
    void deleteFonctionnaliteProfil(Integer id);
    void deleteAllFonctionnaliteProfilsByProfilId(Integer profilId);
}
