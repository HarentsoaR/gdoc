package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.services.CandidatService;
import java.util.List;

public class CandidatGestion {

    private static CandidatService getService() {
        return ObjectFactory.getBean(CandidatService.class);
    }

    public static List<Candidat> findAll() { return getService().getAllActive(); }
    public static Candidat findById(Integer id) { return getService().getById(id); }
    public static void save(Candidat e) { getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<Candidat> searchByNom(String q) { return getService().searchByNom(q); }
    public static List<Candidat> findByConcours(Integer concoursId) { return getService().getByConcours(concoursId); }
}