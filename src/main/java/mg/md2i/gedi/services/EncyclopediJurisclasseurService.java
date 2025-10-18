package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.EncyclopediJurisclasseur;
import java.util.List;

public interface EncyclopediJurisclasseurService {
    List<EncyclopediJurisclasseur> getAllActive();
    EncyclopediJurisclasseur getById(Integer id);
    EncyclopediJurisclasseur save(EncyclopediJurisclasseur entity);
    void softDelete(Integer id);
    List<EncyclopediJurisclasseur> searchByTitre(String query);
}


