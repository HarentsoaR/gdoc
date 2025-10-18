package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.BulletinInformation;
import mg.md2i.gedi.repository.BulletinInformationRepository;
import mg.md2i.gedi.services.BulletinInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BulletinInformationServiceImpl implements BulletinInformationService {

    @Autowired
    private BulletinInformationRepository repository;

    @Override
    public List<BulletinInformation> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public BulletinInformation getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public BulletinInformation save(BulletinInformation entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<BulletinInformation> search(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


