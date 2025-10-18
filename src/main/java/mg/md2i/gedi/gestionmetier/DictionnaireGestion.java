package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Dictionnaire;
import mg.md2i.gedi.services.DictionnaireService;

import java.util.List;

public class DictionnaireGestion {

    private static DictionnaireService getService() {
        return ObjectFactory.getBean(DictionnaireService.class);
    }

    public static List<Dictionnaire> findAll() { return getService().getAllActive(); }
    public static Dictionnaire findById(Integer id) { return getService().getById(id); }
    public static Dictionnaire save(Dictionnaire e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<Dictionnaire> searchByTitre(String q) { return getService().searchByTitre(q); }
}


