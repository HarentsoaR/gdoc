package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.RapportStage;
import mg.md2i.gedi.repository.RapportStageRepository;
import mg.md2i.gedi.services.RapportStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RapportStageServiceImpl implements RapportStageService {

    @Autowired
    private RapportStageRepository repository;

    @Override
    public List<RapportStage> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public RapportStage getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public RapportStage save(RapportStage entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<RapportStage> search(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


