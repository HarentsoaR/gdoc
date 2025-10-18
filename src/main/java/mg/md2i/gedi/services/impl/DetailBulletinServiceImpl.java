package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.DetailBulletin;
import mg.md2i.gedi.repository.DetailBulletinRepository;
import mg.md2i.gedi.services.DetailBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DetailBulletinServiceImpl implements DetailBulletinService {

    @Autowired
    private DetailBulletinRepository repository;

    @Override
    public List<DetailBulletin> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public DetailBulletin getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DetailBulletin save(DetailBulletin entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }
}


