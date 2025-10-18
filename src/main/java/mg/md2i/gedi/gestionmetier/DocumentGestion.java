package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.services.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DocumentGestion {

    private static final Logger log = LoggerFactory.getLogger(DocumentGestion.class);

    private static DocumentService getService() {
        return ObjectFactory.getBean(DocumentService.class);
    }

    public static List<Document> findAllDocuments() {
        log.info("[Gestion] getAllActiveDocuments...");
        List<Document> result = getService().getAllActiveDocuments();
        log.info("✅ Documents trouvés : {}", (result != null ? result.size() : "NULL"));
        return result;
    }

    public static Document findById(Integer id) {
        return getService().getDocumentById(id);
    }

    public static void save(Document doc) {
        getService().saveDocument(doc);
    }

    public static void delete(Integer id) {
        getService().deleteDocument(id);
    }

    public static List<Document> search(String query) {
        return getService().searchDocuments(query);
    }
}
