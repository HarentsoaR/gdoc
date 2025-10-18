package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Integer> {
    List<TypeDocument> findByActif(Integer actif);
    List<TypeDocument> findByLibelleContainingIgnoreCase(String libelle);
}


