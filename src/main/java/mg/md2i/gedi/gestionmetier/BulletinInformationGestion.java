package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.BulletinInformation;
import mg.md2i.gedi.services.BulletinInformationService;

import java.util.List;

public class BulletinInformationGestion {

    private static BulletinInformationService getService() {
        return ObjectFactory.getBean(BulletinInformationService.class);
    }

    public static List<BulletinInformation> findAll() { return getService().getAllActive(); }
    public static BulletinInformation findById(Integer id) { return getService().getById(id); }
    public static BulletinInformation save(BulletinInformation e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<BulletinInformation> search(String query) { return getService().search(query); }
}