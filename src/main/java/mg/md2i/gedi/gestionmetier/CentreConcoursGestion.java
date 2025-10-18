package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.CentreConcours;
import mg.md2i.gedi.services.CentreConcoursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CentreConcoursGestion {

    private static final Logger log = LoggerFactory.getLogger(CentreConcoursGestion.class);

    private static CentreConcoursService getService() {
        return ObjectFactory.getBean(CentreConcoursService.class);
    }

    public static List<CentreConcours> findAll() {
        log.info("[Gestion] CentreConcours: findAll active");
        return getService().getAllActive();
    }

    public static CentreConcours findById(Integer id) {
        return getService().getById(id);
    }

    public static void save(CentreConcours c) {
        getService().save(c);
    }

    public static void delete(Integer id) {
        getService().softDelete(id);
    }

    public static List<CentreConcours> search(String q) {
        return getService().search(q);
    }

    public static List<CentreConcours> findByPromotion(Integer promotionId) {
        return getService().getByPromotion(promotionId);
    }
}


