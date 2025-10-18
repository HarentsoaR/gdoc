package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.CategorieOuvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieOuvrageRepository extends JpaRepository<CategorieOuvrage, Integer> {
    List<CategorieOuvrage> findByActif(Integer actif);
    List<CategorieOuvrage> findByLibelleContainingIgnoreCase(String libelle);
    List<CategorieOuvrage> findByCodeContainingIgnoreCase(String code);
}


