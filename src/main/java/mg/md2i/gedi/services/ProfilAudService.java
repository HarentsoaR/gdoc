package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ProfilAud;
import mg.md2i.gedi.entity.ProfilAudId;
import mg.md2i.gedi.repository.ProfilAudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfilAudService {

    private final ProfilAudRepository repository;

    public ProfilAudService(ProfilAudRepository repository) {
        this.repository = repository;
    }

    public List<ProfilAud> findAll() {
        return repository.findAll();
    }

    public List<ProfilAud> findAllActive() {
        return repository.findByActif(1);
    }

    public ProfilAud findById(ProfilAudId key) {
        if (key == null) {
            return null;
        }
        Optional<ProfilAud> found = repository.findById(key);
        return found.orElse(null);
    }
}
