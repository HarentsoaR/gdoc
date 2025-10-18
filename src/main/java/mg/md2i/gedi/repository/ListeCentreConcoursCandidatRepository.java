package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.ListeCentreConcoursCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeCentreConcoursCandidatRepository extends JpaRepository<ListeCentreConcoursCandidat, Integer> {
    List<ListeCentreConcoursCandidat> findByActif(Integer actif);
    List<ListeCentreConcoursCandidat> findByConcoursPhaseId(Integer concoursPhaseId);
    List<ListeCentreConcoursCandidat> findByCentreConcoursId(Integer centreConcoursId);
    List<ListeCentreConcoursCandidat> findByCandidatId(Integer candidatId);
}


