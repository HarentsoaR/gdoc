package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Concours;
import java.util.List;

public interface ConcoursService {
    List<Concours> getAllActive();
    Concours getById(Integer id);
    void save(Concours concours);
    void softDelete(Integer id);
    List<Concours> search(String query);
}


