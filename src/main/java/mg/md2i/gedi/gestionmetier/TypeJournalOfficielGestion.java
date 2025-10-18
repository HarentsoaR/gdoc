package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.TypeJournalOfficiel;
import mg.md2i.gedi.services.TypeJournalOfficielService;

import java.util.List;

public class TypeJournalOfficielGestion {

    private static TypeJournalOfficielService getService() {
        return ObjectFactory.getBean(TypeJournalOfficielService.class);
    }

    public static List<TypeJournalOfficiel> findAll() { return getService().getAll(); }
    public static TypeJournalOfficiel findById(Integer id) { return getService().getById(id); }
    public static TypeJournalOfficiel save(TypeJournalOfficiel e) { return getService().save(e); }
    public static void delete(Integer id) { getService().delete(id); }
    public static List<TypeJournalOfficiel> searchByTitre(String q) { return getService().searchByTitre(q); }
}


