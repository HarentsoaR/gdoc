package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.ProfilAud;
import mg.md2i.gedi.entity.ProfilAudId;
import mg.md2i.gedi.services.ProfilAudService;

import java.util.List;
import java.util.Optional;

public class ProfilAudGestion {

    private static ProfilAudService getService() {
        return ObjectFactory.getBean(ProfilAudService.class);
    }

    public static List<ProfilAud> findAll() {
        return getService().findAll();
    }

    public static List<ProfilAud> findAllActive() {
        return getService().findAllActive();
    }

    public ProfilAud findById(ProfilAudId key) {
        if (key == null) {
            return null;
        }
        Optional<ProfilAud> found = Optional.ofNullable(getService().findById(key));
        return found.orElse(null);
    }
}
