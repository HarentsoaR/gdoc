package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.DocumentBiblio;
import mg.md2i.gedi.repository.DocumentBiblioRepository;
import mg.md2i.gedi.services.DocumentBiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentBiblioServiceImpl implements DocumentBiblioService {

    @Autowired
    private DocumentBiblioRepository repository;

    @Override
    public List<DocumentBiblio> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public DocumentBiblio getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DocumentBiblio save(DocumentBiblio entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<DocumentBiblio> searchByTitre(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }

    @Override
    public List<DocumentBiblio> searchByMotCle(String query) {
        return repository.findByMotCleContainingIgnoreCase(query);
    }
}


