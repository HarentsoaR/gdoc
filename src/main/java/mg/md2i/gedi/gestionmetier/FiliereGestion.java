package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Filiere;
import mg.md2i.gedi.services.FiliereService;

import java.util.List;

public class FiliereGestion {

    private static FiliereService getService() {
        return ObjectFactory.getBean(FiliereService.class);
    }

    public static List<Filiere> findAll() {
        return getService().getAllActive();
    }

    public static Filiere findById(Integer id) {
        return getService().getById(id);
    }

    public static void save(Filiere filiere) {
        getService().save(filiere);
    }

    public static void delete(Integer id) {
        getService().softDelete(id);
    }

    public static List<Filiere> search(String q) {
        return getService().search(q);
    }
}
