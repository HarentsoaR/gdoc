package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.revinfo;
import mg.md2i.gedi.services.RevinfoService;

import java.util.List;

public class RevinfoGestion {

    private static RevinfoService getService() {
        return ObjectFactory.getBean(RevinfoService.class);
    }

    public static List<revinfo> findAll() {
        return getService().findAll();
    }

    public static List<revinfo> findAllActive() {
        return getService().findAllActive();
    }

    public static revinfo findById(Integer id) {
        return getService().findById(id);
    }
}
