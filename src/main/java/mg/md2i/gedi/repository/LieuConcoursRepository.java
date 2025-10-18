package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.LieuConcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LieuConcoursRepository extends JpaRepository<LieuConcours, Integer> {
    List<LieuConcours> findByActif(Integer actif);
    List<LieuConcours> findByCentreContainingIgnoreCase(String centre);
    List<LieuConcours> findByPromotionId(Integer promotionId);
    List<LieuConcours> findByCentreExamenId(Integer centreExamenId);
}


