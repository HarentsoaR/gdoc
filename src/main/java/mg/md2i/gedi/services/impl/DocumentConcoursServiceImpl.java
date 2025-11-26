package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.repository.DocumentConcoursRepository;
import mg.md2i.gedi.services.DocumentConcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentConcoursServiceImpl implements DocumentConcoursService {

    @Autowired
    private DocumentConcoursRepository repository;

    @Override
    public List<DocumentConcours> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public List<DocumentConcours> getAll() {
        return repository.findAll();
    }

    @Override
    public DocumentConcours getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(DocumentConcours dc) {
        repository.save(dc);
    }

    @Override
    public void softDelete(Integer id) {
        DocumentConcours dc = repository.findById(id).orElse(null);
        if (dc != null) {
            dc.setActif(0);
            repository.save(dc);
        }
    }

    @Override
    public List<DocumentConcours> search(String query) {
        return repository.findByLibelleContainingIgnoreCase(query);
    }
}

