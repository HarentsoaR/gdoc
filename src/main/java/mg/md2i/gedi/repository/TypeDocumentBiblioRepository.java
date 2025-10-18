package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.TypeDocumentBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDocumentBiblioRepository extends JpaRepository<TypeDocumentBiblio, Integer> {
    List<TypeDocumentBiblio> findByActif(Integer actif);
    List<TypeDocumentBiblio> findByLibelleContainingIgnoreCase(String libelle);
}


