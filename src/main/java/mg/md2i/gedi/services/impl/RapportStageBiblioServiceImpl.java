package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.RapportStageBiblio;
import mg.md2i.gedi.repository.RapportStageBiblioRepository;
import mg.md2i.gedi.services.RapportStageBiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RapportStageBiblioServiceImpl implements RapportStageBiblioService {

    @Autowired
    private RapportStageBiblioRepository repository;

    @Override
    public List<RapportStageBiblio> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public RapportStageBiblio getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public RapportStageBiblio save(RapportStageBiblio entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<RapportStageBiblio> search(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


