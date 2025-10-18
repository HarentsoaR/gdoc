package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.CentreExamen;
import java.util.List;

public interface CentreExamenService {
    List<CentreExamen> getAllActive();
    CentreExamen getById(Integer id);
    void save(CentreExamen centreExamen);
    void softDelete(Integer id);
    List<CentreExamen> search(String query);
}
