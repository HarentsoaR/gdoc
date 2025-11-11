package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {

    // You can add custom query methods here if needed
    List<Services> findByLibelleContainingIgnoreCase(String libelle);
    List<Services> findByActif(Integer actif);
}
