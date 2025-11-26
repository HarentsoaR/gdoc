package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Connexion1;
import mg.md2i.gedi.services.Connexion1Service;

import java.util.Date;
import java.util.List;

public class Connexion1Gestion {

    private static Connexion1Service getService() {
        return ObjectFactory.getBean(Connexion1Service.class);
    }

    public static List<Connexion1> findAll() {
        return getService().findAll();
    }

    public static List<Connexion1> findAllActive() {
        return getService().findAllActive();
    }

    public static List<Connexion1> findByDateRange(Date from, Date to) {
        return getService().findByDateRange(from, to);
    }

    public static List<Connexion1> findByUtilisateur(Integer userId) {
        return getService().findByUtilisateur(userId);
    }

    public static Connexion1 findById(Integer id) {
        return getService().findById(id);
    }
}
