package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DetailTypeDocumentBiblio;
import mg.md2i.gedi.services.DetailTypeDocumentBiblioService;

import java.util.List;

public class DetailTypeDocumentBiblioGestion {

    private static DetailTypeDocumentBiblioService getService() {
        return ObjectFactory.getBean(DetailTypeDocumentBiblioService.class);
    }

    public static List<DetailTypeDocumentBiblio> findAll() { return getService().getAllActive(); }
    public static DetailTypeDocumentBiblio findById(Integer id) { return getService().getById(id); }
    public static DetailTypeDocumentBiblio save(DetailTypeDocumentBiblio e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<DetailTypeDocumentBiblio> findByType(Integer typeId) { return getService().findByTypeDocumentBiblioId(typeId); }
    public static List<DetailTypeDocumentBiblio> findByDocument(Integer docId) { return getService().findByDocumentId(docId); }
}


