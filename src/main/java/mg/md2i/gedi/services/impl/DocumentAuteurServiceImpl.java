package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.DocumentAuteur;
import mg.md2i.gedi.repository.DocumentAuteurRepository;
import mg.md2i.gedi.services.DocumentAuteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentAuteurServiceImpl implements DocumentAuteurService {

    @Autowired
    private DocumentAuteurRepository repository;

    @Override
    public List<DocumentAuteur> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public DocumentAuteur getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DocumentAuteur getById(Long id) {
        return repository.findById(id.intValue()).orElse(null);
    }

    @Override
    public DocumentAuteur save(DocumentAuteur entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public void softDelete(Long id) {
        softDelete(id.intValue());
    }
}


