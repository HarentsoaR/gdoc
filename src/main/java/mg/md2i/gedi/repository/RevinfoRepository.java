package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.revinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevinfoRepository extends JpaRepository<revinfo, Integer> {
    List<revinfo> findByActif(Integer actif);
}
