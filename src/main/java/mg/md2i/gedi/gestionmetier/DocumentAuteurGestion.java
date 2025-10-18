package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DocumentAuteur;
import mg.md2i.gedi.services.DocumentAuteurService;

import java.util.List;

public class DocumentAuteurGestion {

    private static DocumentAuteurService getService() {
        return ObjectFactory.getBean(DocumentAuteurService.class);
    }

    public static List<DocumentAuteur> findAll() { return getService().getAllActive(); }
    public static DocumentAuteur findById(Long id) { return getService().getById(id); }
    public static DocumentAuteur save(DocumentAuteur e) { return getService().save(e); }
    public static void delete(Long id) { getService().softDelete(id); }
}