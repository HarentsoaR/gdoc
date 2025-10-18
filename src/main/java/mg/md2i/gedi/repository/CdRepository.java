package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Cd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdRepository extends JpaRepository<Cd, Integer> {
    List<Cd> findByActif(Integer actif);
    List<Cd> findByTitreContainingIgnoreCase(String titre);
}


