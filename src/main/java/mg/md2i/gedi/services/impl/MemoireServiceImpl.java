package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Memoire;
import mg.md2i.gedi.repository.MemoireRepository;
import mg.md2i.gedi.services.MemoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemoireServiceImpl implements MemoireService {

    @Autowired
    private MemoireRepository repository;

    @Override
    public List<Memoire> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public Memoire getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Memoire save(Memoire entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<Memoire> search(String query) {
        // No specific field to search except maybe encadreur or remarque; return all active as fallback
        return getAllActive();
    }
}


