package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Repertoire;
import mg.md2i.gedi.repository.RepertoireRepository;

import java.util.List;

public class RepertoireGestion {

    private static RepertoireRepository repo() {
        return ObjectFactory.getBean(RepertoireRepository.class);
    }

    public static List<Repertoire> findAll() { return repo().findAll(); }
    public static Repertoire findById(Integer id) { return repo().findById(id).orElse(null); }
    public static Repertoire save(Repertoire e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<Repertoire> searchByTitre(String q) { return repo().findByTitreContainingIgnoreCase(q); }
}


