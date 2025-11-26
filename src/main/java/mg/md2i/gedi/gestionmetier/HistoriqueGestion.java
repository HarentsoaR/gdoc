package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Historique;
import mg.md2i.gedi.services.HistoriqueService;

import java.util.Date;
import java.util.List;

public class HistoriqueGestion {

    private static HistoriqueService getService() {
        return ObjectFactory.getBean(HistoriqueService.class);
    }

    public static List<Historique> findAll() {
        return getService().findAll();
    }

    public static Historique findById(Integer id) {
        return getService().findById(id);
    }

    public static List<Historique> findByDateRange(Date from, Date to) {
        return getService().findByDateRange(from, to);
    }

    public static List<Historique> findByConnexion(Integer connexionId) {
        return getService().findByConnexion(connexionId);
    }

    public static List<Historique> searchByOperation(String op) {
        return getService().searchByOperation(op);
    }
}
