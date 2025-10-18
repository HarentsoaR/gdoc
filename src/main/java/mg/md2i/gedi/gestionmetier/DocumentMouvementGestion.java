package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DocumentMouvement;
import mg.md2i.gedi.services.DocumentMouvementService;

import java.util.List;

public class DocumentMouvementGestion {

    private static DocumentMouvementService getService() {
        return ObjectFactory.getBean(DocumentMouvementService.class);
    }

    public static List<DocumentMouvement> findAll() { return getService().getAllActive(); }
    public static DocumentMouvement findById(Integer id) { return getService().getById(id); }
    public static DocumentMouvement save(DocumentMouvement e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
}