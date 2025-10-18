package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DomaineOuvrage;
import mg.md2i.gedi.repository.DomaineOuvrageRepository;

import java.util.List;

public class DomaineOuvrageGestion {

    private static DomaineOuvrageRepository repo() {
        return ObjectFactory.getBean(DomaineOuvrageRepository.class);
    }

    public static List<DomaineOuvrage> findAll() { return repo().findAll(); }
    public static DomaineOuvrage findById(Integer id) { return repo().findById(id).orElse(null); }
    public static DomaineOuvrage save(DomaineOuvrage e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<DomaineOuvrage> searchByLibelle(String q) { return repo().findByLibelleContainingIgnoreCase(q); }
}