package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Dictionnaire;
import mg.md2i.gedi.repository.DictionnaireRepository;
import mg.md2i.gedi.services.DictionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionnaireServiceImpl implements DictionnaireService {

    @Autowired
    private DictionnaireRepository repository;

    @Override
    public List<Dictionnaire> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public Dictionnaire getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Dictionnaire save(Dictionnaire entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<Dictionnaire> searchByTitre(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


