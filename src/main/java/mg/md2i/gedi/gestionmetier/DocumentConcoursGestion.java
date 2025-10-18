package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.services.DocumentConcoursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DocumentConcoursGestion {

    private static final Logger log = LoggerFactory.getLogger(DocumentConcoursGestion.class);

    private static DocumentConcoursService getService() {
        return ObjectFactory.getBean(DocumentConcoursService.class);
    }

    public static List<DocumentConcours> findAll() {
        log.info("[Gestion] DocumentConcours: findAll active");
        return getService().getAllActive();
    }

    public static DocumentConcours findById(Integer id) {
        return getService().getById(id);
    }

    public static void save(DocumentConcours d) {
        getService().save(d);
    }

    public static void delete(Integer id) {
        getService().softDelete(id);
    }

    public static List<DocumentConcours> search(String q) {
        return getService().search(q);
    }
}


