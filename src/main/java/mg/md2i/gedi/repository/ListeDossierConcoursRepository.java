package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.ListeDossierConcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeDossierConcoursRepository extends JpaRepository<ListeDossierConcours, Integer> {
    List<ListeDossierConcours> findByActif(Integer actif);
    List<ListeDossierConcours> findByConcoursId(Integer concoursId);
    List<ListeDossierConcours> findByNomDossierContainingIgnoreCase(String nomDossier);
}


