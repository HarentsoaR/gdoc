package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.DocumentMouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentMouvementRepository extends JpaRepository<DocumentMouvement, Integer> {
    List<DocumentMouvement> findByActif(Integer actif);
}


