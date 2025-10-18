package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.repository.DocumentRepository;
import mg.md2i.gedi.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository repository;

    @Override
    public List<Document> getAllActiveDocuments() {
        return repository.findByActif(1);
    }

    @Override
    public Document getDocumentById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void saveDocument(Document document) {
        repository.save(document);
    }

    @Override
    public void deleteDocument(Integer id) {
        Document doc = repository.findById(id).orElse(null);
        if (doc != null) {
            doc.setActif(0);
            repository.save(doc);
        }
    }

    @Override
    public List<Document> searchDocuments(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}
