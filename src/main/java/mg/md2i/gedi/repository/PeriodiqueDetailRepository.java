package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.PeriodiqueDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodiqueDetailRepository extends JpaRepository<PeriodiqueDetail, Integer> {
    List<PeriodiqueDetail> findByActif(Integer actif);
    List<PeriodiqueDetail> findByAuteurContainingIgnoreCase(String auteur);
}


