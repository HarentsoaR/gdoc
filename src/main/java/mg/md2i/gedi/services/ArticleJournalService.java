package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ArticleJournal;
import java.util.List;

public interface ArticleJournalService {
    List<ArticleJournal> getAllActive();
    ArticleJournal getById(Integer id);
    ArticleJournal save(ArticleJournal entity);
    void softDelete(Integer id);
    List<ArticleJournal> search(String query);
}


