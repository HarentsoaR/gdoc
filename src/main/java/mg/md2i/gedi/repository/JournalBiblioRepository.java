package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.JournalBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalBiblioRepository extends JpaRepository<JournalBiblio, Integer> {
    List<JournalBiblio> findByActif(Integer actif);
    List<JournalBiblio> findByTitreContainingIgnoreCase(String titre);
}


