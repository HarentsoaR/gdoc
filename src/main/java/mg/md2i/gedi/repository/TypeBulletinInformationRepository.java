package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.TypeBulletinInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeBulletinInformationRepository extends JpaRepository<TypeBulletinInformation, Integer> {
    List<TypeBulletinInformation> findByActif(Integer actif);
    List<TypeBulletinInformation> findByLibelleContainingIgnoreCase(String libelle);
}


