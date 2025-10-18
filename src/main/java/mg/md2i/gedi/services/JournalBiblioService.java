package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.JournalBiblio;
import java.util.List;

public interface JournalBiblioService {
    List<JournalBiblio> getAllActive();
    JournalBiblio getById(Integer id);
    JournalBiblio save(JournalBiblio entity);
    void softDelete(Integer id);
    List<JournalBiblio> searchByTitre(String query);
}


