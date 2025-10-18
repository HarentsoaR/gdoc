package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DetailBulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailBulletinRepository extends JpaRepository<DetailBulletin, Integer> {
    List<DetailBulletin> findByActif(Integer actif);
    List<DetailBulletin> findByTitreContainingIgnoreCase(String titre);
}


