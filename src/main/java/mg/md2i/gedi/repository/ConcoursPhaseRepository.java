package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.ConcoursPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcoursPhaseRepository extends JpaRepository<ConcoursPhase, Integer> {
    List<ConcoursPhase> findByActif(Integer actif);
    List<ConcoursPhase> findByLibelleContainingIgnoreCase(String libelle);
    List<ConcoursPhase> findByConcoursId(Integer concoursId);
}


