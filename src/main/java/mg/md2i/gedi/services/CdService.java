package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Cd;
import java.util.List;

public interface CdService {
    List<Cd> getAllActive();
    Cd getById(Integer id);
    Cd save(Cd entity);
    void softDelete(Integer id);
    List<Cd> search(String query);
}


