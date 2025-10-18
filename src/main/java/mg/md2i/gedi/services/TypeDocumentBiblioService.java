package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.TypeDocumentBiblio;
import java.util.List;

public interface TypeDocumentBiblioService {
    List<TypeDocumentBiblio> getAllActive();
    TypeDocumentBiblio getById(Integer id);
    TypeDocumentBiblio save(TypeDocumentBiblio entity);
    void delete(Integer id);
    List<TypeDocumentBiblio> searchByLibelle(String query);
}


