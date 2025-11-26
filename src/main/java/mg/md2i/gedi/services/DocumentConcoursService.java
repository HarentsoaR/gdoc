package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.DocumentConcours;
import java.util.List;

public interface DocumentConcoursService {
    List<DocumentConcours> getAllActive();
    List<DocumentConcours> getAll();
    DocumentConcours getById(Integer id);
    void save(DocumentConcours dc);
    void softDelete(Integer id);
    List<DocumentConcours> search(String query);
}

