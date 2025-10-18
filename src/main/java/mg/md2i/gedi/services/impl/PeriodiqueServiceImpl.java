package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Periodique;
import mg.md2i.gedi.repository.PeriodiqueRepository;
import mg.md2i.gedi.services.PeriodiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PeriodiqueServiceImpl implements PeriodiqueService {

    @Autowired
    private PeriodiqueRepository repository;

    @Override
    public List<Periodique> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public Periodique getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Periodique save(Periodique entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<Periodique> searchByTitre(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


