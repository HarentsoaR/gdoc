package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Connexion1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Connexion1Repository extends JpaRepository<Connexion1, Integer> {
    List<Connexion1> findByActif(Integer actif);
    List<Connexion1> findByDateDebutBetween(Date from, Date to);
    List<Connexion1> findByUtilisateurId(Integer utilisateurId);
    List<Connexion1> findTop10ByActifOrderByDateDebutDesc(Integer actif);
}
