package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.revinfo;
import mg.md2i.gedi.repository.RevinfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevinfoService {

    private final RevinfoRepository repository;

    public RevinfoService(RevinfoRepository repository) {
        this.repository = repository;
    }

    public List<revinfo> findAll() {
        return repository.findAll();
    }

    public List<revinfo> findAllActive() {
        return repository.findByActif(1);
    }

    public revinfo findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
