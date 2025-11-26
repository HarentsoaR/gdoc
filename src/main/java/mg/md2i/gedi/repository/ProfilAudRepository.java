package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.ProfilAud;
import mg.md2i.gedi.entity.ProfilAudId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilAudRepository extends JpaRepository<ProfilAud, ProfilAudId> {
    List<ProfilAud> findByActif(Integer actif);
}
