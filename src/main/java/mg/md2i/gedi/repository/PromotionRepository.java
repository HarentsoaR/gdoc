package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    List<Promotion> findByActif(Integer actif);

    List<Promotion> findByLibelleContainingIgnoreCaseOrNumeroPromotionContainingIgnoreCase(String libelle, String numero);

    Optional<Promotion> findFirstByActifAndFiliereIdAndAnneeConcoursAndNumeroPromotionIgnoreCase(Integer actif, Integer filiereId, Integer anneeConcours, String numeroPromotion);
}
