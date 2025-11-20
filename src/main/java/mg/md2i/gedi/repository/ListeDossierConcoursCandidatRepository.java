// File Path: src/main/java/mg/md2i/gedi/repository/ListeDossierConcoursCandidatRepository.java
package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Importez Optional

@Repository
public interface ListeDossierConcoursCandidatRepository extends JpaRepository<ListeDossierConcoursCandidat, Integer> {

    List<ListeDossierConcoursCandidat> findByActif(Integer actif);

    // CORRECTION : S'assure de ne récupérer que les documents actifs
    @Query("SELECT e FROM ListeDossierConcoursCandidat e WHERE e.actif = 1 AND e.candidatId = :candidatId")
    List<ListeDossierConcoursCandidat> findByCandidatId(@Param("candidatId") Integer candidatId);

    List<ListeDossierConcoursCandidat> findByDocumentConcoursId(Integer documentConcoursId);

    // NOUVELLE MÉTHODE : Trouve un document spécifique pour un candidat donné.
    // Utilise Optional pour gérer élégamment le cas où le document n'existe pas.
    Optional<ListeDossierConcoursCandidat> findByCandidatIdAndDocumentConcoursIdAndActif(Integer candidatId, Integer documentConcoursId, Integer actif);

    @Query("SELECT e FROM ListeDossierConcoursCandidat e " +
           "WHERE e.actif = 1 " +
           "AND (:docId IS NULL OR e.documentConcoursId = :docId) " +
           "AND (:candidatId IS NULL OR e.candidatId = :candidatId)")
    List<ListeDossierConcoursCandidat> findByFilters(
            @Param("docId") Integer documentConcoursId,
            @Param("candidatId") Integer candidatId);

    @Query("SELECT e FROM ListeDossierConcoursCandidat e JOIN e.candidat c " +
           "WHERE e.actif = 1 " +
           "AND (:docId IS NULL OR e.documentConcoursId = :docId) " +
           "AND (:concoursId IS NULL OR c.concoursId = :concoursId) " +
           "AND (:centreId IS NULL OR c.centreExamenId = :centreId) " +
           "AND (:nomCandidat IS NULL OR LOWER(c.nom) LIKE :nomCandidat OR LOWER(c.prenom) LIKE :nomCandidat)")
    List<ListeDossierConcoursCandidat> findWithAdvancedFilters(
            @Param("docId") Integer documentConcoursId,
            @Param("concoursId") Integer concoursId,
            @Param("centreId") Integer centreId,
            @Param("nomCandidat") String nomCandidat);

    long countByActif(Integer actif);

    long countByActifAndEtatDocument(Integer actif, Integer etatDocument);

    @Query("SELECT e.etatDocument, COUNT(e) FROM ListeDossierConcoursCandidat e WHERE e.actif = 1 GROUP BY e.etatDocument")
    List<Object[]> countByEtat();

    @Query("SELECT CONCAT(COALESCE(f.code,'N/A'),' | ',COALESCE(concours.avisConcours,''),' | ',COALESCE(concours.numeroArrete,'')), COUNT(e) " +
            "FROM ListeDossierConcoursCandidat e " +
            "LEFT JOIN e.candidat c " +
            "LEFT JOIN c.concours concours " +
            "LEFT JOIN concours.promotion promo " +
            "LEFT JOIN promo.filiere f " +
            "WHERE e.actif = 1 " +
            "GROUP BY concours.concoursId, f.code, concours.avisConcours, concours.numeroArrete " +
            "ORDER BY COUNT(e) DESC")
    List<Object[]> countByConcours();
}
