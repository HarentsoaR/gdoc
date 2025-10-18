package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Integer> {

    // 🔍 Exemple de recherche par libellé
    List<Profil> findByLibelleContainingIgnoreCase(String libelle);

    // 🔍 Recherche par filière
    List<Profil> findByFiliereIgnoreCase(String filiere);

    // 🔍 Recherche par actif
    List<Profil> findByActif(Integer actif);
}
