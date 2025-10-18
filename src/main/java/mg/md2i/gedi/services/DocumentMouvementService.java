package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.DocumentMouvement;
import java.util.List;

public interface DocumentMouvementService {
    List<DocumentMouvement> getAllActive();
    DocumentMouvement getById(Integer id);
    DocumentMouvement save(DocumentMouvement entity);
    void softDelete(Integer id);
}


