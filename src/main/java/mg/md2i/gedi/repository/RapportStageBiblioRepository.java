package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.RapportStageBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportStageBiblioRepository extends JpaRepository<RapportStageBiblio, Integer> {
    List<RapportStageBiblio> findByActif(Integer actif);
    List<RapportStageBiblio> findByTitreContainingIgnoreCase(String titre);
}


