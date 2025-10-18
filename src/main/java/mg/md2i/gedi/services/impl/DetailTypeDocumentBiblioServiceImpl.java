package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.DetailTypeDocumentBiblio;
import mg.md2i.gedi.repository.DetailTypeDocumentBiblioRepository;
import mg.md2i.gedi.services.DetailTypeDocumentBiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DetailTypeDocumentBiblioServiceImpl implements DetailTypeDocumentBiblioService {

    @Autowired
    private DetailTypeDocumentBiblioRepository repository;

    @Override
    public List<DetailTypeDocumentBiblio> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public DetailTypeDocumentBiblio getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DetailTypeDocumentBiblio save(DetailTypeDocumentBiblio entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<DetailTypeDocumentBiblio> findByTypeDocumentBiblioId(Integer typeDocumentBiblioId) {
        return repository.findByTypeDocumentBiblioId(typeDocumentBiblioId);
    }

    @Override
    public List<DetailTypeDocumentBiblio> findByDocumentId(Integer documentId) {
        return repository.findByDocumentId(documentId);
    }
}


