package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.PeriodiqueDetail;
import mg.md2i.gedi.repository.PeriodiqueDetailRepository;
import mg.md2i.gedi.services.PeriodiqueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PeriodiqueDetailServiceImpl implements PeriodiqueDetailService {

    @Autowired
    private PeriodiqueDetailRepository repository;

    @Override
    public List<PeriodiqueDetail> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public PeriodiqueDetail getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PeriodiqueDetail save(PeriodiqueDetail entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }
}


