package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.ConcoursPhase;
import mg.md2i.gedi.repository.ConcoursPhaseRepository;
import mg.md2i.gedi.services.ConcoursPhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcoursPhaseServiceImpl implements ConcoursPhaseService {

    @Autowired
    private ConcoursPhaseRepository repository;

    @Override
    public List<ConcoursPhase> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public ConcoursPhase getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(ConcoursPhase concoursPhase) {
        repository.save(concoursPhase);
    }

    @Override
    public void softDelete(Integer id) {
        ConcoursPhase cp = repository.findById(id).orElse(null);
        if (cp != null) {
            cp.setActif(0);
            repository.save(cp);
        }
    }

    @Override
    public List<ConcoursPhase> search(String query) {
        return repository.findByLibelleContainingIgnoreCase(query);
    }

    @Override
    public List<ConcoursPhase> getByConcours(Integer concoursId) {
        return repository.findByConcoursId(concoursId);
    }
}


