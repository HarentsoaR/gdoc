package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.ListeCentreConcoursCandidat;
import mg.md2i.gedi.services.ListeCentreConcoursCandidatService;
import java.util.List;

public class ListeCentreConcoursCandidatGestion {

    private static ListeCentreConcoursCandidatService getService() {
        return ObjectFactory.getBean(ListeCentreConcoursCandidatService.class);
    }

    public static List<ListeCentreConcoursCandidat> findAll() { return getService().getAllActive(); }
    public static ListeCentreConcoursCandidat findById(Integer id) { return getService().getById(id); }
    public static void save(ListeCentreConcoursCandidat e) { getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<ListeCentreConcoursCandidat> findByConcoursPhase(Integer concoursPhaseId) { return getService().getByConcoursPhase(concoursPhaseId); }
    public static List<ListeCentreConcoursCandidat> findByCentreConcours(Integer centreConcoursId) { return getService().getByCentreConcours(centreConcoursId); }
    public static List<ListeCentreConcoursCandidat> findByCandidat(Integer candidatId) { return getService().getByCandidat(candidatId); }
}


