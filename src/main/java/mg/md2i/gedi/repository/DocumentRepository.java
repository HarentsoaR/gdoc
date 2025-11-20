package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByActif(Integer actif);
    List<Document> findByTitreContainingIgnoreCase(String titre);

    long countByActif(Integer actif);

    @Query("SELECT COALESCE(d.type, 'Autres'), SUM(COALESCE(d.taille,0)) FROM Document d WHERE d.actif = 1 GROUP BY d.type")
    List<Object[]> sumSizeByType();

    @Query("SELECT YEAR(d.dateUpload), MONTH(d.dateUpload), COUNT(d), SUM(COALESCE(d.taille,0)) " +
            "FROM Document d WHERE d.actif = 1 AND d.dateUpload >= :fromDate " +
            "GROUP BY YEAR(d.dateUpload), MONTH(d.dateUpload) ORDER BY YEAR(d.dateUpload), MONTH(d.dateUpload)")
    List<Object[]> countAndSizeByMonth(@Param("fromDate") Date fromDate);

    @Query("SELECT SUM(COALESCE(d.taille,0)) FROM Document d WHERE d.actif = 1")
    Long sumActiveDocumentSizes();

    @Query("SELECT d FROM Document d WHERE d.actif = :actif ORDER BY d.dateUpload DESC")
    List<Document> findByActifOrderByDateUploadDesc(@Param("actif") Integer actif, Pageable pageable);

    @Query("SELECT FUNCTION('date', d.dateUpload), COUNT(d) FROM Document d WHERE d.actif = 1 AND d.dateUpload >= :fromDate " +
            "GROUP BY FUNCTION('date', d.dateUpload) ORDER BY FUNCTION('date', d.dateUpload)")
    List<Object[]> countByDay(@Param("fromDate") Date fromDate);
}
