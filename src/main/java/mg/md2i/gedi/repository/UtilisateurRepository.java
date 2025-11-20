package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByLogin(String login);
    
    List<Utilisateur> findByNomContainingOrPrenomContainingOrMailContaining(
            String nom, String prenom, String mail);
        
     	@Query("SELECT u FROM Utilisateur u WHERE u.actif = 1")
        List<Utilisateur> findActiveUsers();
        
        List<Utilisateur> findByProfilId(Integer profilId);

        @Query("SELECT DISTINCT u.service FROM Utilisateur u WHERE u.service IS NOT NULL")
        List<String> findDistinctServices();

        long countByActif(Integer actif);
}
