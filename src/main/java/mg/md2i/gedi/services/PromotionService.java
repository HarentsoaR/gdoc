package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Promotion;
import mg.md2i.gedi.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public boolean existsActiveDuplicate(Promotion promotion) {
        if (promotion == null
                || promotion.getFiliereId() == null
                || promotion.getAnneeConcours() == null
                || promotion.getNumeroPromotion() == null
                || promotion.getNumeroPromotion().trim().isEmpty()) {
            return false;
        }
        Optional<Promotion> found = repository.findFirstByActifAndFiliereIdAndAnneeConcoursAndNumeroPromotionIgnoreCase(
                1,
                promotion.getFiliereId(),
                promotion.getAnneeConcours(),
                promotion.getNumeroPromotion().trim());
        return found.isPresent()
                && (promotion.getPromotionId() == null
                || !found.get().getPromotionId().equals(promotion.getPromotionId()));
    }
}
