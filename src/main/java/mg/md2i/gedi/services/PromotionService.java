package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Promotion;
import mg.md2i.gedi.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    public List<Promotion> getAllActive() {
        return repository.findByActif(1);
    }

    public Promotion getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Promotion promotion) {
        repository.save(promotion);
    }

    public void softDelete(Integer id) {
        Promotion p = getById(id);
        if (p != null) {
            p.setActif(0);
            repository.save(p);
        }
    }

    public List<Promotion> search(String query) {
        return repository.findByLibelleContainingIgnoreCaseOrNumeroPromotionContainingIgnoreCase(query, query);
    }
}
