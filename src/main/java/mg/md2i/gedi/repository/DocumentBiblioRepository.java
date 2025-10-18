package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DocumentBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentBiblioRepository extends JpaRepository<DocumentBiblio, Integer> {
    List<DocumentBiblio> findByActif(Integer actif);
    List<DocumentBiblio> findByTitreContainingIgnoreCase(String titre);
    List<DocumentBiblio> findByMotCleContainingIgnoreCase(String motCle);
}


