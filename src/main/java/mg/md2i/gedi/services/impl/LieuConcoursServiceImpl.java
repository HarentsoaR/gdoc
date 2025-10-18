package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.LieuConcours;
import mg.md2i.gedi.repository.LieuConcoursRepository;
import mg.md2i.gedi.services.LieuConcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LieuConcoursServiceImpl implements LieuConcoursService {

    @Autowired
    private LieuConcoursRepository repository;

    @Override
    public List<LieuConcours> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public LieuConcours getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(LieuConcours lieuConcours) {
        repository.save(lieuConcours);
    }

    @Override
    public void softDelete(Integer id) {
        LieuConcours lc = repository.findById(id).orElse(null);
        if (lc != null) {
            lc.setActif(0);
            repository.save(lc);
        }
    }

    @Override
    public List<LieuConcours> search(String query) {
        return repository.findByCentreContainingIgnoreCase(query);
    }

    @Override
    public List<LieuConcours> getByPromotion(Integer promotionId) {
        return repository.findByPromotionId(promotionId);
    }

    @Override
    public List<LieuConcours> getByCentreExamen(Integer centreExamenId) {
        return repository.findByCentreExamenId(centreExamenId);
    }
}


