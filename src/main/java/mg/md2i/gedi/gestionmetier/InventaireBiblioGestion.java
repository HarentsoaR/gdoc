package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.InventaireBiblio;
import mg.md2i.gedi.services.InventaireBiblioService;

import java.util.List;

public class InventaireBiblioGestion {

    private static InventaireBiblioService getService() {
        return ObjectFactory.getBean(InventaireBiblioService.class);
    }

    public static List<InventaireBiblio> findAll() { return getService().getAllActive(); }
    public static InventaireBiblio findById(Integer id) { return getService().getById(id); }
    public static InventaireBiblio save(InventaireBiblio e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
}