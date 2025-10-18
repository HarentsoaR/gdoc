package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.TypeBulletinInformation;
import mg.md2i.gedi.repository.TypeBulletinInformationRepository;
import mg.md2i.gedi.services.TypeBulletinInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeBulletinInformationServiceImpl implements TypeBulletinInformationService {

    @Autowired
    private TypeBulletinInformationRepository repository;

    @Override
    public List<TypeBulletinInformation> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public TypeBulletinInformation getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TypeBulletinInformation save(TypeBulletinInformation entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<TypeBulletinInformation> searchByLibelle(String query) {
        return repository.findByLibelleContainingIgnoreCase(query);
    }
}


