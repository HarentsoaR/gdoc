package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Ministere;
import mg.md2i.gedi.repository.MinistereRepository;

import java.util.List;

public class MinistereGestion {

    private static MinistereRepository repo() {
        return ObjectFactory.getBean(MinistereRepository.class);
    }

    public static List<Ministere> findAll() { return repo().findAll(); }
    public static Ministere findById(Integer id) { return repo().findById(id).orElse(null); }
    public static Ministere save(Ministere e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<Ministere> searchByNom(String q) { return repo().findByNomContainingIgnoreCase(q); }
}


