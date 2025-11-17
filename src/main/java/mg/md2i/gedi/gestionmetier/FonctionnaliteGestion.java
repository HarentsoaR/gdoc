package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Fonctionnalite;
import mg.md2i.gedi.services.FonctionnaliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FonctionnaliteGestion {

    private static final Logger log = LoggerFactory.getLogger(FonctionnaliteGestion.class);

    private static FonctionnaliteService getService() {
        return ObjectFactory.getBean(FonctionnaliteService.class);
    }

    public static List<Fonctionnalite> findAllFonctionnalites() {
        log.info("↘️ [Gestion] Appel de la couche Service (FonctionnaliteService.getAllFonctionnalites())...");
        List<Fonctionnalite> result = getService().getAllFonctionnalites();
        if (result == null) {
            log.warn("⚠️ [Gestion] La couche Service a retourné NULL, remplacement par une liste vide.");
            return new ArrayList<>();
        }
        return result;
    }

    public static Fonctionnalite findFonctionnaliteById(Integer id) {
        log.info("🔍 [Gestion] Recherche de la fonctionnalité ID={}", id);
        return getService().getFonctionnaliteById(id);
    }

    public static void saveFonctionnalite(Fonctionnalite fonctionnalite) {
        log.info("💾 [Gestion] Sauvegarde de la fonctionnalité: {}", fonctionnalite.getLibelle());
        getService().saveFonctionnalite(fonctionnalite);
    }

    public static void deleteFonctionnalite(Integer id) {
        log.warn("🗑️ [Gestion] Suppression de la fonctionnalité ID={}", id);
        getService().deleteFonctionnalite(id);
    }

    public static List<Fonctionnalite> searchFonctionnalites(String searchQuery) {
        log.info("🔍 [Gestion] Recherche de fonctionnalités avec le critère: {}", searchQuery);
        return getService().searchFonctionnalites(searchQuery);
    }
    public static Integer findFonctionnaliteIdByNomTable(String nomTable) {
        return getService().findFonctionnaliteIdByNomTable(nomTable);
    }

}
