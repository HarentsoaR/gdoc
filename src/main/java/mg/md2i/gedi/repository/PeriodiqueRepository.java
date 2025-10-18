package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Periodique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodiqueRepository extends JpaRepository<Periodique, Integer> {
    List<Periodique> findByActif(Integer actif);
    List<Periodique> findByTitreContainingIgnoreCase(String titre);
}


