package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.DetailTypeDocumentBiblio;
import java.util.List;

public interface DetailTypeDocumentBiblioService {
    List<DetailTypeDocumentBiblio> getAllActive();
    DetailTypeDocumentBiblio getById(Integer id);
    DetailTypeDocumentBiblio save(DetailTypeDocumentBiblio entity);
    void softDelete(Integer id);
    List<DetailTypeDocumentBiblio> findByTypeDocumentBiblioId(Integer typeDocumentBiblioId);
    List<DetailTypeDocumentBiblio> findByDocumentId(Integer documentId);
}


