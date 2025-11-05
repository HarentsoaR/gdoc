package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Filiere;
import mg.md2i.gedi.repository.FiliereRepository;
import mg.md2i.gedi.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiliereServiceImpl implements FiliereService {

    @Autowired
    private FiliereRepository repository;

    @Override
    public List<Filiere> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public Filiere getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Filiere filiere) {
        if (filiere.getFiliereId() != null) {
            // Existing record → merge updated fields
            Filiere existing = repository.findById(filiere.getFiliereId()).orElse(null);
            if (existing != null) {
                existing.setLibelle(filiere.getLibelle());
                existing.setCode(filiere.getCode());
                existing.setSysId(filiere.getSysId());
                existing.setRemarque(filiere.getRemarque());
                existing.setActif(filiere.getActif());
                existing.setVersion(filiere.getVersion());
                repository.save(existing);
                return;
            }
        }
        // New record
        repository.save(filiere);
    }

    @Override
    public void softDelete(Integer id) {
        Filiere f = getById(id);
        if (f != null) {
            f.setActif(0);
            repository.save(f);
        }
    }

    @Override
    public List<Filiere> search(String query) {
        return repository.findByLibelleContainingIgnoreCaseOrCodeContainingIgnoreCase(query, query);
    }
}
