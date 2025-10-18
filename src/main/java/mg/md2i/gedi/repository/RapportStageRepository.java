package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.RapportStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportStageRepository extends JpaRepository<RapportStage, Integer> {
    List<RapportStage> findByActif(Integer actif);
    List<RapportStage> findByTitreContainingIgnoreCase(String titre);
}


