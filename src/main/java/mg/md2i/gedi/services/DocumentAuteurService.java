package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.DocumentAuteur;
import java.util.List;

public interface DocumentAuteurService {
    List<DocumentAuteur> getAllActive();
    DocumentAuteur getById(Integer id);
    DocumentAuteur getById(Long id);
    DocumentAuteur save(DocumentAuteur entity);
    void softDelete(Integer id);
    void softDelete(Long id);
}


