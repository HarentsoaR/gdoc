package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.CollectionOuvrage;
import mg.md2i.gedi.repository.CollectionOuvrageRepository;

import java.util.List;

public class CollectionOuvrageGestion {

    private static CollectionOuvrageRepository repo() {
        return ObjectFactory.getBean(CollectionOuvrageRepository.class);
    }

    public static List<CollectionOuvrage> findAll() { return repo().findAll(); }
    public static CollectionOuvrage findById(Integer id) { return repo().findById(id).orElse(null); }
    public static CollectionOuvrage save(CollectionOuvrage e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<CollectionOuvrage> searchByLibelle(String q) { return repo().findByNomContainingIgnoreCase(q); }
}