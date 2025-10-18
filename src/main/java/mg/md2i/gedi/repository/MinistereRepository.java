package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Ministere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinistereRepository extends JpaRepository<Ministere, Integer> {
    List<Ministere> findByActif(Integer actif);
    List<Ministere> findByNomContainingIgnoreCase(String nom);
}


