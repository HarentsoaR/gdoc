package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.ConcoursPhase;
import mg.md2i.gedi.services.ConcoursPhaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConcoursPhaseGestion {

    private static final Logger log = LoggerFactory.getLogger(ConcoursPhaseGestion.class);

    private static ConcoursPhaseService getService() {
        return ObjectFactory.getBean(ConcoursPhaseService.class);
    }

    public static List<ConcoursPhase> findAll() {
        log.info("[Gestion] ConcoursPhase: findAll active");
        return getService().getAllActive();
    }

    public static ConcoursPhase findById(Integer id) {
        return getService().getById(id);
    }

    public static void save(ConcoursPhase c) {
        getService().save(c);
    }

    public static void delete(Integer id) {
        getService().softDelete(id);
    }

    public static List<ConcoursPhase> search(String q) {
        return getService().search(q);
    }

    public static List<ConcoursPhase> findByConcours(Integer concoursId) {
        return getService().getByConcours(concoursId);
    }
}


