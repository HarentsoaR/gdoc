package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.LieuConcours;
import java.util.List;

public interface LieuConcoursService {
    List<LieuConcours> getAllActive();
    LieuConcours getById(Integer id);
    void save(LieuConcours lieuConcours);
    void softDelete(Integer id);
    List<LieuConcours> search(String query);
    List<LieuConcours> getByPromotion(Integer promotionId);
    List<LieuConcours> getByCentreExamen(Integer centreExamenId);
}


