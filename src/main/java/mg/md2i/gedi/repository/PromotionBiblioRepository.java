package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.PromotionBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PromotionBiblioRepository extends JpaRepository<PromotionBiblio, Integer> {
    List<PromotionBiblio> findByActif(Integer actif);
    List<PromotionBiblio> findByNum(Integer num);
    List<PromotionBiblio> findByAnnee(Date annee);
}


