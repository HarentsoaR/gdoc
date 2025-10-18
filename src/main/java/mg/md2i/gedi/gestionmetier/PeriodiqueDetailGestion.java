package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.PeriodiqueDetail;
import mg.md2i.gedi.services.PeriodiqueDetailService;

import java.util.List;

public class PeriodiqueDetailGestion {

    private static PeriodiqueDetailService getService() {
        return ObjectFactory.getBean(PeriodiqueDetailService.class);
    }

    public static List<PeriodiqueDetail> findAll() { return getService().getAllActive(); }
    public static PeriodiqueDetail findById(Integer id) { return getService().getById(id); }
    public static PeriodiqueDetail save(PeriodiqueDetail e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
}