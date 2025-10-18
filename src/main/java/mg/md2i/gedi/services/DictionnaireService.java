package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Dictionnaire;
import java.util.List;

public interface DictionnaireService {
    List<Dictionnaire> getAllActive();
    Dictionnaire getById(Integer id);
    Dictionnaire save(Dictionnaire entity);
    void softDelete(Integer id);
    List<Dictionnaire> searchByTitre(String query);
}


