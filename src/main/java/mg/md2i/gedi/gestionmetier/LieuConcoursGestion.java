package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.LieuConcours;
import mg.md2i.gedi.services.LieuConcoursService;
import java.util.List;

public class LieuConcoursGestion {

    private static LieuConcoursService getService() {
        return ObjectFactory.getBean(LieuConcoursService.class);
    }

    public static List<LieuConcours> findAll() { return getService().getAllActive(); }
    public static LieuConcours findById(Integer id) { return getService().getById(id); }
    public static void save(LieuConcours e) { getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<LieuConcours> search(String q) { return getService().search(q); }
    public static List<LieuConcours> findByPromotion(Integer promotionId) { return getService().getByPromotion(promotionId); }
    public static List<LieuConcours> findByCentreExamen(Integer centreExamenId) { return getService().getByCentreExamen(centreExamenId); }
}


