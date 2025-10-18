package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.JournalOfficiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JournalOfficielRepository extends JpaRepository<JournalOfficiel, Integer> {
    List<JournalOfficiel> findByActif(Integer actif);
    List<JournalOfficiel> findByNumero(Integer numero);
    List<JournalOfficiel> findByDateBetween(Date start, Date end);
}


