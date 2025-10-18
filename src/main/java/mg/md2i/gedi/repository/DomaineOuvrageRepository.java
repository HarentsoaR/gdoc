package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DomaineOuvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomaineOuvrageRepository extends JpaRepository<DomaineOuvrage, Integer> {
    List<DomaineOuvrage> findByActif(Integer actif);
    List<DomaineOuvrage> findByCodeContainingIgnoreCase(String code);
    List<DomaineOuvrage> findByLibelleContainingIgnoreCase(String libelle);
}


