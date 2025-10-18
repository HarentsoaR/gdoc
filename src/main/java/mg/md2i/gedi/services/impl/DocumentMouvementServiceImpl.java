package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.DocumentMouvement;
import mg.md2i.gedi.repository.DocumentMouvementRepository;
import mg.md2i.gedi.services.DocumentMouvementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentMouvementServiceImpl implements DocumentMouvementService {

    @Autowired
    private DocumentMouvementRepository repository;

    @Override
    public List<DocumentMouvement> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public DocumentMouvement getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DocumentMouvement save(DocumentMouvement entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }
}


