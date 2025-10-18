package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.ListeCentreConcoursCandidat;
import mg.md2i.gedi.repository.ListeCentreConcoursCandidatRepository;
import mg.md2i.gedi.services.ListeCentreConcoursCandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListeCentreConcoursCandidatServiceImpl implements ListeCentreConcoursCandidatService {

    @Autowired
    private ListeCentreConcoursCandidatRepository repository;

    @Override
    public List<ListeCentreConcoursCandidat> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public ListeCentreConcoursCandidat getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(ListeCentreConcoursCandidat entity) {
        repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        ListeCentreConcoursCandidat e = repository.findById(id).orElse(null);
        if (e != null) {
            e.setActif(0);
            repository.save(e);
        }
    }

    @Override
    public List<ListeCentreConcoursCandidat> getByConcoursPhase(Integer concoursPhaseId) {
        return repository.findByConcoursPhaseId(concoursPhaseId);
    }

    @Override
    public List<ListeCentreConcoursCandidat> getByCentreConcours(Integer centreConcoursId) {
        return repository.findByCentreConcoursId(centreConcoursId);
    }

    @Override
    public List<ListeCentreConcoursCandidat> getByCandidat(Integer candidatId) {
        return repository.findByCandidatId(candidatId);
    }
}


