package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Origine;
import mg.md2i.gedi.repository.OrigineRepository;

import java.util.List;

public class OrigineGestion {

    private static OrigineRepository repo() {
        return ObjectFactory.getBean(OrigineRepository.class);
    }

    public static List<Origine> findAll() { return repo().findAll(); }
    public static Origine findById(Integer id) { return repo().findById(id).orElse(null); }
    public static Origine save(Origine e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<Origine> searchByLibelle(String q) { return repo().findByOrigineContainingIgnoreCase(q); }
}