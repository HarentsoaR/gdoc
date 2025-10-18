package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.CentreConcours;
import mg.md2i.gedi.repository.CentreConcoursRepository;
import mg.md2i.gedi.services.CentreConcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentreConcoursServiceImpl implements CentreConcoursService {

    @Autowired
    private CentreConcoursRepository repository;

    @Override
    public List<CentreConcours> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public CentreConcours getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(CentreConcours centreConcours) {
        repository.save(centreConcours);
    }

    @Override
    public void softDelete(Integer id) {
        CentreConcours c = repository.findById(id).orElse(null);
        if (c != null) {
            c.setActif(0);
            repository.save(c);
        }
    }

    @Override
    public List<CentreConcours> search(String query) {
        return repository.findByCentreContainingIgnoreCase(query);
    }

    @Override
    public List<CentreConcours> getByPromotion(Integer promotionId) {
        return repository.findByPromotionId(promotionId);
    }
}


