package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DetailTypeDocumentBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailTypeDocumentBiblioRepository extends JpaRepository<DetailTypeDocumentBiblio, Integer> {
    List<DetailTypeDocumentBiblio> findByActif(Integer actif);
    List<DetailTypeDocumentBiblio> findByTypeDocumentBiblioId(Integer typeDocumentBiblioId);
    List<DetailTypeDocumentBiblio> findByDocumentId(Integer documentId);
}


