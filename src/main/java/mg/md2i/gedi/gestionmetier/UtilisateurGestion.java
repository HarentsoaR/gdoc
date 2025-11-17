package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UtilisateurGestion {
    
    private static final Logger log = LoggerFactory.getLogger(UtilisateurGestion.class);

    private static UserService getService() {
        return ObjectFactory.getBean(UserService.class);
    }

    public static List<Utilisateur> findAllUtilisateurs() {
        log.info("↘️ [Gestion] Appel de la couche Service (UserService.getAllUsers())...");
        List<Utilisateur> result = getService().getAllUsers();
        log.info("✅ [Gestion] La couche Service a retourné {} utilisateurs.", (result != null ? result.size() : "NULL"));
        return result;
    }
    
    // ... Le reste de vos méthodes reste identique ...

    public static void deleteUtilisateurById(Integer utilisateurId) {
        getService().deleteUser(utilisateurId);
    }

    public static Utilisateur findById(Integer utilisateurId) {
        return getService().getUserById(utilisateurId);
    }

    public static void save(Utilisateur utilisateur) { // Renamed from updateUtilisateur and saveUtilisateur
        getService().saveUser(utilisateur);
    }

    public static List<Utilisateur> searchUsers(String query) {
        return getService().searchUsers(query);
    }

    public static List<String> findDistinctServices() {
        log.info("↘️ [Gestion] Appel de la couche Service (UserService.findDistinctServices())...");
        List<String> services = getService().findDistinctServices();
        log.info("✅ [Gestion] La couche Service a retourné {} services distincts.", (services != null ? services.size() : "NULL"));
        return services;
    }
}