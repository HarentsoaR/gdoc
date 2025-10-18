package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DomaineBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomaineBiblioRepository extends JpaRepository<DomaineBiblio, Integer> {
    List<DomaineBiblio> findByActif(Integer actif);
    List<DomaineBiblio> findByTitreContainingIgnoreCase(String titre);
}


