package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.TypeDocumentBiblio;
import mg.md2i.gedi.repository.TypeDocumentBiblioRepository;
import mg.md2i.gedi.services.TypeDocumentBiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeDocumentBiblioServiceImpl implements TypeDocumentBiblioService {

    @Autowired
    private TypeDocumentBiblioRepository repository;

    @Override
    public List<TypeDocumentBiblio> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public TypeDocumentBiblio getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TypeDocumentBiblio save(TypeDocumentBiblio entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<TypeDocumentBiblio> searchByLibelle(String query) {
        return repository.findByLibelleContainingIgnoreCase(query);
    }
}


