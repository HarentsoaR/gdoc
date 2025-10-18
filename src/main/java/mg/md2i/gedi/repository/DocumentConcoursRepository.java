package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DocumentConcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentConcoursRepository extends JpaRepository<DocumentConcours, Integer> {
    List<DocumentConcours> findByActif(Integer actif);
    List<DocumentConcours> findByLibelleContainingIgnoreCase(String libelle);
}


