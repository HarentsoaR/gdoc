package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Integer> {

    List<Filiere> findByActif(Integer actif);

    List<Filiere> findByLibelleContainingIgnoreCaseOrCodeContainingIgnoreCase(String libelle, String code);
}
