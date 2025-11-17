package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.services.ProfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProfilGestion {

    private static final Logger log = LoggerFactory.getLogger(ProfilGestion.class);

    private static ProfilService getService() {
        return ObjectFactory.getBean(ProfilService.class);
    }

    public static List<Profil> findAllProfils() {
        log.info("↘️ [Gestion] Appel de la couche Service (ProfilService.getAllProfils())...");
        List<Profil> result = getService().getAllProfils();
        if (result == null) {
            log.warn("⚠️ [Gestion] La couche Service a retourné NULL, remplacement par une liste vide.");
            return new ArrayList<>();
        }
        log.info("✅ [Gestion] La couche Service a retourné {} profils.", result.size());
        return result;
    }

    public static Profil findById(Integer id) {
        log.info("🔍 [Gestion] Recherche du profil ID={}", id);
        return getService().getProfilById(id);
    }

    public static void saveProfil(Profil profil) {
        log.info("💾 [Gestion] Sauvegarde du profil: {}", profil.getLibelle());
        getService().saveProfil(profil);
    }

    public static void deleteProfil(Integer id) {
        log.warn("🗑️ [Gestion] Suppression du profil ID={}", id);
        getService().deleteProfil(id);
    }

    public static List<Profil> searchProfils(String query) {
        log.info("🔎 [Gestion] Recherche de profils contenant '{}'", query);
        return getService().searchProfils(query);
    }

    public static List<Profil> findProfilsByServiceId(Integer serviceId) {
        log.info("🔎 [Gestion] Recherche de profils pour le service ID={}", serviceId);
        return getService().findProfilsByServiceId(serviceId);
    }
}
