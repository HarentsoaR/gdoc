package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.TypeDocumentBiblio;
import mg.md2i.gedi.repository.TypeDocumentBiblioRepository;

import java.util.List;

public class TypeDocumentBiblioGestion {

    private static TypeDocumentBiblioRepository repo() {
        return ObjectFactory.getBean(TypeDocumentBiblioRepository.class);
    }

    public static List<TypeDocumentBiblio> findAll() { return repo().findAll(); }
    public static TypeDocumentBiblio findById(Integer id) { return repo().findById(id).orElse(null); }
    public static TypeDocumentBiblio save(TypeDocumentBiblio e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<TypeDocumentBiblio> searchByLibelle(String q) { return repo().findByLibelleContainingIgnoreCase(q); }
}