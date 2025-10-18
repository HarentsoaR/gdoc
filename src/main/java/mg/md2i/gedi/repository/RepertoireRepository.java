package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Integer> {
    List<Repertoire> findByActif(Integer actif);
    List<Repertoire> findByTitreContainingIgnoreCase(String titre);
}


