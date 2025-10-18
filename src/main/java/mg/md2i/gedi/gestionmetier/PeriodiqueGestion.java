package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Periodique;
import mg.md2i.gedi.services.PeriodiqueService;

import java.util.List;

public class PeriodiqueGestion {

    private static PeriodiqueService getService() {
        return ObjectFactory.getBean(PeriodiqueService.class);
    }

    public static List<Periodique> findAll() { return getService().getAllActive(); }
    public static Periodique findById(Integer id) { return getService().getById(id); }
    public static Periodique save(Periodique e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<Periodique> searchByTitre(String q) { return getService().searchByTitre(q); }
}


