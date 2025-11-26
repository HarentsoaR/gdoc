package mg.md2i.gedi.util;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.ProfilGestion;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import mg.md2i.gedi.session.UserSession;
import org.zkoss.zk.ui.Executions;

import java.util.Locale;
import java.util.Objects;

/**
 * Small helper to retrieve the logged-in user and evaluate role-based permissions.
 */
public final class RoleUtils {

    private static final String ROLE_ADMIN_1 = "ADMIN";
    private static final String ROLE_ADMIN_2 = "ADMINISTRATEUR";
    private static final String ROLE_VALIDEUR = "VALIDATEUR DOSSIER";
    private static final String ROLE_RSP_CONCOURS = "RSP-CONCOURS";
    private static final String ROLE_RSP_CONCOURS_ALT = "RSP CONCOURS";
    private static final String ROLE_RESP_DOSSIER = "RESPONSABLE DOSSIER";

    private RoleUtils() {}

    public static Utilisateur getCurrentUser() {
        // Priority 1: explicit session helper
        Utilisateur user = UserSession.get();
        if (user != null) return hydrateProfil(user);

        // Priority 2: legacy session attribute "user"
        Object sessionUser = Executions.getCurrent().getSession().getAttribute("user");
        if (sessionUser instanceof Utilisateur) {
            return hydrateProfil((Utilisateur) sessionUser);
        }

        // Priority 3: session attribute "username" (login) set during login flow
        Object username = Executions.getCurrent().getSession().getAttribute("username");
        if (username instanceof String) {
            Utilisateur found = UtilisateurGestion.findByLogin((String) username);
            if (found != null) {
                UserSession.set(found);
                return hydrateProfil(found);
            }
        }

        return null;
    }

    private static Utilisateur hydrateProfil(Utilisateur user) {
        if (user == null) return null;
        if (user.getProfil() == null && user.getProfilId() != null) {
            Profil p = ProfilGestion.findById(user.getProfilId());
            user.setProfil(p);
        }
        return user;
    }

    public static Profil getCurrentProfil() {
        Utilisateur user = getCurrentUser();
        return user != null ? user.getProfil() : null;
    }

    public static boolean isAdmin() {
        Profil profil = getCurrentProfil();
        return isAdmin(profil);
    }

    public static boolean isValidateurDossier() {
        Profil profil = getCurrentProfil();
        return hasLibelle(profil, ROLE_VALIDEUR);
    }

    public static boolean isRSPConcours() {
        Profil profil = getCurrentProfil();
        return hasLibelle(profil, ROLE_RSP_CONCOURS, ROLE_RSP_CONCOURS_ALT);
    }

    public static boolean isResponsableDossier() {
        Profil profil = getCurrentProfil();
        return hasLibelle(profil, ROLE_RESP_DOSSIER);
    }

    public static boolean canValidateDossiers() {
        return isAdmin() || isValidateurDossier();
    }

    public static boolean canManageReferentiels() {
        return isAdmin() || isRSPConcours();
    }

    public static boolean canSeeSuiviGlobal() {
        return isAdmin() || isRSPConcours();
    }

    public static boolean canImportDocuments() {
        return isAdmin() || isResponsableDossier();
    }

    private static boolean isAdmin(Profil profil) {
        return hasLibelle(profil, ROLE_ADMIN_1, ROLE_ADMIN_2);
    }

    private static boolean hasLibelle(Profil profil, String... expected) {
        if (profil == null) return false;
        String lib = normalize(profil.getLibelle());
        for (String value : expected) {
            if (Objects.equals(lib, normalize(value))) return true;
        }
        return false;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toUpperCase(Locale.ROOT);
    }
}
