package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Memoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoireRepository extends JpaRepository<Memoire, Integer> {
    List< Memoire > findByActif(Integer actif);
}


