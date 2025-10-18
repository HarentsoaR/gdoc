package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.FiliereBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiliereBiblioRepository extends JpaRepository<FiliereBiblio, Integer> {
    List<FiliereBiblio> findByActif(Integer actif);
    List<FiliereBiblio> findByCodeContainingIgnoreCase(String code);
    List<FiliereBiblio> findByTitreContainingIgnoreCase(String titre);
}


