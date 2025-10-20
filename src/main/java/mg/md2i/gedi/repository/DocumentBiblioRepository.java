package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DocumentBiblio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentBiblioRepository extends JpaRepository<DocumentBiblio, Integer> {
    List<DocumentBiblio> findByActif(Integer actif);
    List<DocumentBiblio> findByTitreContainingIgnoreCase(String titre);
    List<DocumentBiblio> findByMotCleContainingIgnoreCase(String motCle);
    
    @Query("SELECT d FROM DocumentBiblio d " +
           "LEFT JOIN FETCH d.domaineOuvrage " +
           "LEFT JOIN FETCH d.categorieOuvrage " +
           "LEFT JOIN FETCH d.collectionOuvrage " +
           "LEFT JOIN FETCH d.editeur " +
           "LEFT JOIN FETCH d.origine " +
           "LEFT JOIN FETCH d.detailTypeDocumentBiblio dt " +
           "LEFT JOIN FETCH dt.typeDocumentBiblio " +
           "WHERE d.actif = 1")
    List<DocumentBiblio> findAllActiveWithRelations();
}


