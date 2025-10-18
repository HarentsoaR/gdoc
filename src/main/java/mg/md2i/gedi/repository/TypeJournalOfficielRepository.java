package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.TypeJournalOfficiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeJournalOfficielRepository extends JpaRepository<TypeJournalOfficiel, Integer> {
    List<TypeJournalOfficiel> findByTitreContainingIgnoreCase(String titre);
}


