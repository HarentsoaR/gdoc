package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByActif(Integer actif);
    List<Document> findByTitreContainingIgnoreCase(String titre);
}
