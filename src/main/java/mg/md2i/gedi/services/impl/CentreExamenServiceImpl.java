package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.CentreExamen;
import mg.md2i.gedi.repository.CentreExamenRepository;
import mg.md2i.gedi.services.CentreExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentreExamenServiceImpl implements CentreExamenService {

    @Autowired
    private CentreExamenRepository repository;

    @Override
    public List<CentreExamen> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public CentreExamen getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(CentreExamen centreExamen) {
        repository.save(centreExamen);
    }

    @Override
    public void softDelete(Integer id) {
        CentreExamen ce = repository.findById(id).orElse(null);
        if (ce != null) {
            ce.setActif(0);
            repository.save(ce);
        }
    }

    @Override
    public List<CentreExamen> search(String query) {
        return repository.findByLibelleContainingIgnoreCase(query);
    }
}
