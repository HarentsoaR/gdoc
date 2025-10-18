package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.TypeDocument;
import java.util.List;

public interface TypeDocumentService {
    List<TypeDocument> getAllActive();
    TypeDocument getById(Integer id);
    TypeDocument save(TypeDocument entity);
    void delete(Integer id);
    List<TypeDocument> searchByLibelle(String query);
}


