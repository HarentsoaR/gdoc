package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.FiliereBiblio;
import mg.md2i.gedi.repository.FiliereBiblioRepository;

import java.util.List;

public class FiliereBiblioGestion {

    private static FiliereBiblioRepository repo() {
        return ObjectFactory.getBean(FiliereBiblioRepository.class);
    }

    public static List<FiliereBiblio> findAll() { return repo().findAll(); }
    public static FiliereBiblio findById(Integer id) { return repo().findById(id).orElse(null); }
    public static FiliereBiblio save(FiliereBiblio e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<FiliereBiblio> searchByCode(String q) { return repo().findByCodeContainingIgnoreCase(q); }
    public static List<FiliereBiblio> searchByTitre(String q) { return repo().findByTitreContainingIgnoreCase(q); }
}


