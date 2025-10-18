package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.CentreExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentreExamenRepository extends JpaRepository<CentreExamen, Integer> {
    List<CentreExamen> findByActif(Integer actif);
    List<CentreExamen> findByLibelleContainingIgnoreCase(String libelle);
}
