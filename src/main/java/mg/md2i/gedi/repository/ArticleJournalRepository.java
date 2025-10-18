package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.ArticleJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleJournalRepository extends JpaRepository<ArticleJournal, Integer> {
    List<ArticleJournal> findByActif(Integer actif);
    List<ArticleJournal> findByTitreContainingIgnoreCase(String titre);
}


