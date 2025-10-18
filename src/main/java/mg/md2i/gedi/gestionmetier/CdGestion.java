package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Cd;
import mg.md2i.gedi.services.CdService;

import java.util.List;

public class CdGestion {

    private static CdService getService() {
        return ObjectFactory.getBean(CdService.class);
    }

    public static List<Cd> findAll() { return getService().getAllActive(); }
    public static Cd findById(Integer id) { return getService().getById(id); }
    public static Cd save(Cd e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<Cd> search(String query) { return getService().search(query); }
}