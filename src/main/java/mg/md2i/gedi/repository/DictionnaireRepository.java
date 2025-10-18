package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Dictionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionnaireRepository extends JpaRepository<Dictionnaire, Integer> {
    List<Dictionnaire> findByActif(Integer actif);
    List<Dictionnaire> findByTitreContainingIgnoreCase(String titre);
}


