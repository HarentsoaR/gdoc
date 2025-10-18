package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.TypeDocument;
import mg.md2i.gedi.repository.TypeDocumentRepository;
import mg.md2i.gedi.services.TypeDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeDocumentServiceImpl implements TypeDocumentService {

    @Autowired
    private TypeDocumentRepository repository;

    @Override
    public List<TypeDocument> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public TypeDocument getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TypeDocument save(TypeDocument entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<TypeDocument> searchByLibelle(String query) {
        return repository.findByLibelleContainingIgnoreCase(query);
    }
}


