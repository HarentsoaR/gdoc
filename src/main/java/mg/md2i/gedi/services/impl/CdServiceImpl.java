package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Cd;
import mg.md2i.gedi.repository.CdRepository;
import mg.md2i.gedi.services.CdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CdServiceImpl implements CdService {

    @Autowired
    private CdRepository repository;

    @Override
    public List<Cd> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public Cd getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Cd save(Cd entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<Cd> search(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


