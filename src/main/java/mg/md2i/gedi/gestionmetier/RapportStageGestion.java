package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.RapportStage;
import mg.md2i.gedi.services.RapportStageService;

import java.util.List;

public class RapportStageGestion {

    private static RapportStageService getService() {
        return ObjectFactory.getBean(RapportStageService.class);
    }

    public static List<RapportStage> findAll() { return getService().getAllActive(); }
    public static RapportStage findById(Integer id) { return getService().getById(id); }
    public static RapportStage save(RapportStage e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<RapportStage> search(String query) { return getService().search(query); }
}