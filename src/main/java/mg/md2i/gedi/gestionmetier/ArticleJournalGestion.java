package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.ArticleJournal;
import mg.md2i.gedi.services.ArticleJournalService;

import java.util.List;

public class ArticleJournalGestion {

    private static ArticleJournalService getService() {
        return ObjectFactory.getBean(ArticleJournalService.class);
    }

    public static List<ArticleJournal> findAll() { return getService().getAllActive(); }
    public static ArticleJournal findById(Integer id) { return getService().getById(id); }
    public static ArticleJournal save(ArticleJournal e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<ArticleJournal> search(String query) { return getService().search(query); }
}