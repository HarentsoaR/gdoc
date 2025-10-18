package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.CategorieOuvrage;
import mg.md2i.gedi.repository.CategorieOuvrageRepository;

import java.util.List;

public class CategorieOuvrageGestion {

    private static CategorieOuvrageRepository repo() {
        return ObjectFactory.getBean(CategorieOuvrageRepository.class);
    }

    public static List<CategorieOuvrage> findAll() { return repo().findAll(); }
    public static CategorieOuvrage findById(Integer id) { return repo().findById(id).orElse(null); }
    public static CategorieOuvrage save(CategorieOuvrage e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<CategorieOuvrage> searchByLibelle(String q) { return repo().findByLibelleContainingIgnoreCase(q); }
}