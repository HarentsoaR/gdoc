package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.DocumentBiblio;
import java.util.List;

public interface DocumentBiblioService {
    List<DocumentBiblio> getAllActive();
    DocumentBiblio getById(Integer id);
    DocumentBiblio save(DocumentBiblio entity);
    void softDelete(Integer id);
    List<DocumentBiblio> searchByTitre(String query);
    List<DocumentBiblio> searchByMotCle(String query);
}


