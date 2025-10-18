package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.BulletinInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinInformationRepository extends JpaRepository<BulletinInformation, Integer> {
    List<BulletinInformation> findByActif(Integer actif);
    List<BulletinInformation> findByTitreContainingIgnoreCase(String titre);
}


