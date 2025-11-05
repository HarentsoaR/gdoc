package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Concours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcoursRepository extends JpaRepository<Concours, Integer> {
    List<Concours> findByActif(Integer actif);
    List<Concours> findByAvisConcoursContainingIgnoreCase(String avisConcours);
    List<Concours> findByNumeroArreteContainingIgnoreCase(String numeroArrete);

    @Query("SELECT DISTINCT c.avisConcours FROM Concours c ORDER BY c.avisConcours")
    List<String> findDistinctAvisConcours();

    @Query("SELECT DISTINCT c.numeroArrete FROM Concours c ORDER BY c.numeroArrete")
    List<String> findDistinctNumeroArrete();
}


