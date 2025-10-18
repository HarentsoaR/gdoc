package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DocumentAuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentAuteurRepository extends JpaRepository<DocumentAuteur, Integer> {
    List<DocumentAuteur> findByActif(Integer actif);
    List<DocumentAuteur> findByNomAuteurContainingIgnoreCase(String nomAuteur);
}


