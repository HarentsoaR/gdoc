package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.TypeJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeJournalRepository extends JpaRepository<TypeJournal, Integer> {
    List<TypeJournal> findByActif(Integer actif);
    List<TypeJournal> findByTypeJournalContainingIgnoreCase(String typeJournal);
}


