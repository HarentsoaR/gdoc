package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.InventaireBiblio;
import java.util.List;

public interface InventaireBiblioService {
    List<InventaireBiblio> getAllActive();
    InventaireBiblio getById(Integer id);
    InventaireBiblio save(InventaireBiblio entity);
    void softDelete(Integer id);
}


