package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Promotion;
import mg.md2i.gedi.services.PromotionService;

import java.util.List;

public class PromotionGestion {

    private static PromotionService getService() {
        return ObjectFactory.getBean(PromotionService.class);
    }

    public static List<Promotion> findAll() {
        return getService().getAllActive();
    }

    public static List<Promotion> findAllActive() {
        return getService().getAllActive();
    }

    public static Promotion findById(Integer id) {
        return getService().getById(id);
    }

    public static void save(Promotion promotion) {
        getService().save(promotion);
    }

    public static void delete(Integer id) {
        getService().softDelete(id);
    }

    public static List<Promotion> search(String q) {
        return getService().search(q);
    }
}
