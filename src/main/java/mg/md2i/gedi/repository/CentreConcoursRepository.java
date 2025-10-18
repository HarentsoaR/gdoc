package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.CentreConcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentreConcoursRepository extends JpaRepository<CentreConcours, Integer> {
    List<CentreConcours> findByActif(Integer actif);
    List<CentreConcours> findByCentreContainingIgnoreCase(String centre);
    List<CentreConcours> findByPromotionId(Integer promotionId);
}


