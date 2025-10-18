package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Memoire;
import mg.md2i.gedi.services.MemoireService;

import java.util.List;

public class MemoireGestion {

    private static MemoireService getService() {
        return ObjectFactory.getBean(MemoireService.class);
    }

    public static List<Memoire> findAll() { return getService().getAllActive(); }
    public static Memoire findById(Integer id) { return getService().getById(id); }
    public static Memoire save(Memoire e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<Memoire> search(String query) { return getService().search(query); }
}