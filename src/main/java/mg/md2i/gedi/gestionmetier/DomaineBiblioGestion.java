package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DomaineBiblio;
import mg.md2i.gedi.repository.DomaineBiblioRepository;

import java.util.List;

public class DomaineBiblioGestion {

    private static DomaineBiblioRepository repo() {
        return ObjectFactory.getBean(DomaineBiblioRepository.class);
    }

    public static List<DomaineBiblio> findAll() { return repo().findAll(); }
    public static DomaineBiblio findById(Integer id) { return repo().findById(id).orElse(null); }
    public static DomaineBiblio save(DomaineBiblio e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<DomaineBiblio> searchByTitre(String q) { return repo().findByTitreContainingIgnoreCase(q); }
}


