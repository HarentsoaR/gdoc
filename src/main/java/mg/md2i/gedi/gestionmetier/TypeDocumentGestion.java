package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.TypeDocument;
import mg.md2i.gedi.services.TypeDocumentService;

import java.util.List;

public class TypeDocumentGestion {

    private static TypeDocumentService getService() {
        return ObjectFactory.getBean(TypeDocumentService.class);
    }

    public static List<TypeDocument> findAll() { return getService().getAllActive(); }
    public static TypeDocument findById(Integer id) { return getService().getById(id); }
    public static TypeDocument save(TypeDocument e) { return getService().save(e); }
    public static void delete(Integer id) { getService().delete(id); }
    public static List<TypeDocument> searchByLibelle(String q) { return getService().searchByLibelle(q); }
}


