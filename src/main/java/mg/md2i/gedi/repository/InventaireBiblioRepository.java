package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.InventaireBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventaireBiblioRepository extends JpaRepository<InventaireBiblio, Integer> {
    List<InventaireBiblio> findByActif(Integer actif);
    List<InventaireBiblio> findByTitreContainingIgnoreCase(String titre);
}


