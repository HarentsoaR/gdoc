package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Concours;
import mg.md2i.gedi.repository.ConcoursRepository;
import mg.md2i.gedi.services.ConcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcoursServiceImpl implements ConcoursService {

    @Autowired
    private ConcoursRepository repository;

    @Override
    public List<Concours> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public Concours getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Concours concours) {
        repository.save(concours);
    }

    @Override
    public void softDelete(Integer id) {
        Concours c = repository.findById(id).orElse(null);
        if (c != null) {
            c.setActif(0);
            repository.save(c);
        }
    }

    @Override
    public List<Concours> search(String query) {
        return repository.findByAvisConcoursContainingIgnoreCase(query);
    }

    @Override
    public List<String> getDistinctAvis() {
        return repository.findDistinctAvisConcours();
    }

    @Override
    public List<String> getDistinctNumeros() {
        return repository.findDistinctNumeroArrete();
    }
}


