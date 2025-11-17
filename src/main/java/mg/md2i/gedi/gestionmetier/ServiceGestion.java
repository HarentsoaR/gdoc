package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Services;
import mg.md2i.gedi.services.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ServiceGestion {

    private static final Logger log = LoggerFactory.getLogger(ServiceGestion.class);

    private static ServiceService getService() {
        return ObjectFactory.getBean(ServiceService.class);
    }

    public static List<Services> findAllServices() {
        log.info("↘️ [Gestion] Appel de la couche Service (ServiceService.getAllServices())...");
        return getService().getAllServices();
    }

    public static Services findServiceById(Integer id) {
        log.info("🔍 [Gestion] Recherche du service ID={}", id);
        return getService().getServiceById(id);
    }

    public static void saveService(Services services) {
        log.info("💾 [Gestion] Sauvegarde du service: {}", services.getLibelle());
        getService().saveService(services);
    }

    public static void deleteService(Integer id) {
        log.warn("🗑️ [Gestion] Suppression du service ID={}", id);
        getService().deleteService(id);
    }

    public static List<Services> searchServices(String searchQuery) {
        log.info("🔍 [Gestion] Recherche de services avec le critère: {}", searchQuery);
        return getService().searchServices(searchQuery);
    }
}
