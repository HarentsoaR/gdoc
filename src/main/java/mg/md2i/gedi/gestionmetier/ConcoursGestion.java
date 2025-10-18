package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Concours;
import mg.md2i.gedi.services.ConcoursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConcoursGestion {

    private static final Logger log = LoggerFactory.getLogger(ConcoursGestion.class);

    private static ConcoursService getService() {
        return ObjectFactory.getBean(ConcoursService.class);
    }

    public static List<Concours> findAll() {
        log.info("[Gestion] Concours: findAll active");
        return getService().getAllActive();
    }

    public static Concours findById(Integer id) {
        return getService().getById(id);
    }

    public static void save(Concours c) {
        getService().save(c);
    }

    public static void delete(Integer id) {
        getService().softDelete(id);
    }

    public static List<Concours> search(String q) {
        return getService().search(q);
    }
}


