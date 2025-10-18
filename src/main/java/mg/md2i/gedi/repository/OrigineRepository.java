package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Origine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrigineRepository extends JpaRepository<Origine, Integer> {
    List<Origine> findByActif(Integer actif);
    List<Origine> findByOrigineContainingIgnoreCase(String origine);
}


