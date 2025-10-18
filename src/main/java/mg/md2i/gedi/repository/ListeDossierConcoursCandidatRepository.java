// File Path: src/main/java/mg/md2i/gedi/repository/ListeDossierConcoursCandidatRepository.java
package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeDossierConcoursCandidatRepository extends JpaRepository<ListeDossierConcoursCandidat, Integer> {

    // --- Existing methods (kept for compatibility) ---
    List<ListeDossierConcoursCandidat> findByActif(Integer actif);
    List<ListeDossierConcoursCandidat> findByCandidatId(Integer candidatId);
    List<ListeDossierConcoursCandidat> findByDocumentConcoursId(Integer documentConcoursId);

    @Query("SELECT e FROM ListeDossierConcoursCandidat e " +
           "WHERE e.actif = 1 " +
           "AND (:docId IS NULL OR e.documentConcoursId = :docId) " +
           "AND (:candidatId IS NULL OR e.candidatId = :candidatId)")
    List<ListeDossierConcoursCandidat> findByFilters(
            @Param("docId") Integer documentConcoursId,
            @Param("candidatId") Integer candidatId);

    /**
     * NOUVELLE MÉTHODE DE RECHERCHE AVANCÉE
     * Recherche les documents en joignant les informations du candidat pour filtrer
     * par concours, centre d'examen et nom/prénom.
     */
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
}