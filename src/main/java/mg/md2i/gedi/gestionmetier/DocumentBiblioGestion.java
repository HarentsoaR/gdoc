package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DocumentBiblio;
import mg.md2i.gedi.services.DocumentBiblioService;

import java.util.List;

public class DocumentBiblioGestion {

    private static DocumentBiblioService getService() {
        return ObjectFactory.getBean(DocumentBiblioService.class);
    }

    public static List<DocumentBiblio> findAll() { return getService().getAllActive(); }
    public static DocumentBiblio findById(Integer id) { return getService().getById(id); }
    public static DocumentBiblio save(DocumentBiblio e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<DocumentBiblio> searchByTitre(String q) { return getService().searchByTitre(q); }
    public static List<DocumentBiblio> searchByMotCle(String q) { return getService().searchByMotCle(q); }
}


