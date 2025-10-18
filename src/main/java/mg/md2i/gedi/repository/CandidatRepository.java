package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Integer> {

    /**
     * FIX #1: Charge les candidats avec TOUTES leurs relations nécessaires pour l'affichage.
     * On ajoute les jointures pour charger Concours -> Promotion -> Filiere.
     */
    @Query("SELECT c FROM Candidat c " +
           "LEFT JOIN FETCH c.concours con " +
           "LEFT JOIN FETCH c.centreExamen " +
           "LEFT JOIN FETCH con.promotion promo " + // <-- AJOUTÉ
           "LEFT JOIN FETCH promo.filiere " +       // <-- AJOUTÉ
           "WHERE c.actif = 1 ORDER BY c.nom")
    List<Candidat> findAllWithRelations();

    /**
     * FIX #2: Fait la même chose pour la recherche pour éviter l'erreur après une recherche.
     */
    @Query("SELECT c FROM Candidat c " +
           "LEFT JOIN FETCH c.concours con " +
           "LEFT JOIN FETCH c.centreExamen " +
           "LEFT JOIN FETCH con.promotion promo " + // <-- AJOUTÉ
           "LEFT JOIN FETCH promo.filiere " +       // <-- AJOUTÉ
           "WHERE c.actif = 1 AND lower(c.nom) LIKE lower(concat('%', :query, '%')) " +
           "ORDER BY c.nom")
    List<Candidat> searchByNomWithRelations(@Param("query") String query);

    // Les autres méthodes restent inchangées.
    List<Candidat> findByActif(int actif);
    List<Candidat> findByNomContainingIgnoreCase(String nom);
    List<Candidat> findByConcoursId(Integer concoursId);
}