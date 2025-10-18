package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.DetailBulletin;
import mg.md2i.gedi.services.DetailBulletinService;

import java.util.List;

public class DetailBulletinGestion {

    private static DetailBulletinService getService() {
        return ObjectFactory.getBean(DetailBulletinService.class);
    }

    public static List<DetailBulletin> findAll() { return getService().getAllActive(); }
    public static DetailBulletin findById(Integer id) { return getService().getById(id); }
    public static DetailBulletin save(DetailBulletin e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
}