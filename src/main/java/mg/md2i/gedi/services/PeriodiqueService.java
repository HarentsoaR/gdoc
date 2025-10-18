package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Periodique;
import java.util.List;

public interface PeriodiqueService {
    List<Periodique> getAllActive();
    Periodique getById(Integer id);
    Periodique save(Periodique entity);
    void softDelete(Integer id);
    List<Periodique> searchByTitre(String query);
}


