package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.FonctionnaliteProfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FonctionnaliteProfilRepository extends JpaRepository<FonctionnaliteProfil, Integer> {
    List<FonctionnaliteProfil> findByProfilId(Integer profilId);
    FonctionnaliteProfil findByProfilIdAndFonctionnaliteId(Integer profilId, Integer fonctionnaliteId);
}
