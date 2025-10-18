package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.JournalBiblio;
import mg.md2i.gedi.services.JournalBiblioService;

import java.util.List;

public class JournalBiblioGestion {

    private static JournalBiblioService getService() {
        return ObjectFactory.getBean(JournalBiblioService.class);
    }

    public static List<JournalBiblio> findAll() { return getService().getAllActive(); }
    public static JournalBiblio findById(Integer id) { return getService().getById(id); }
    public static JournalBiblio save(JournalBiblio e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<JournalBiblio> searchByTitre(String q) { return getService().searchByTitre(q); }
}


