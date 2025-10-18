package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.CentreConcours;
import java.util.List;

public interface CentreConcoursService {
    List<CentreConcours> getAllActive();
    CentreConcours getById(Integer id);
    void save(CentreConcours centreConcours);
    void softDelete(Integer id);
    List<CentreConcours> search(String query);
    List<CentreConcours> getByPromotion(Integer promotionId);
}


