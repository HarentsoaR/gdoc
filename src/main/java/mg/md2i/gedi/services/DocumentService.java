package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Document;
import java.util.List;

public interface DocumentService {
    List<Document> getAllActiveDocuments();
    Document getDocumentById(Integer id);
    void saveDocument(Document document);
    void deleteDocument(Integer id);
    List<Document> searchDocuments(String query);
}
