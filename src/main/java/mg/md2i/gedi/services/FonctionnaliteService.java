package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Fonctionnalite;

import java.util.List;

public interface FonctionnaliteService {
    List<Fonctionnalite> getAllFonctionnalites();
    Fonctionnalite getFonctionnaliteById(Integer id);
    void saveFonctionnalite(Fonctionnalite fonctionnalite);
    void deleteFonctionnalite(Integer id);
    List<Fonctionnalite> searchFonctionnalites(String searchQuery);

    Integer findFonctionnaliteIdByNomTable(String nomTable);
}

