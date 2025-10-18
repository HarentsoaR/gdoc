package mg.md2i.gedi.control.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

/**
 * Une classe de base abstraite pour les ViewModels qui fournit une gestion
 * commune des messages "flash" affichés dans une bannière.
 */
public abstract class BaseViewModel {

    protected String flashMessage;
    protected String flashMessageType;

    /**
     * Méthode d'initialisation de base à appeler par les classes enfants.
     * Elle lit le paramètre 'message' de l'URL et configure la bannière.
     */
    @Init
    public void baseInit() {
        String messageParam = Executions.getCurrent().getParameter("message");
        if (messageParam != null) {
            switch (messageParam) {
                case "login_success":
                    flashMessage = "Connexion réussie. Bienvenue !";
                    flashMessageType = "success";
                    break;
                case "logout_success":
                    flashMessage = "Déconnexion réussie. À bientôt !";
                    flashMessageType = "warn";
                    break;
                case "session_expired":
                    flashMessage = "Votre session a expiré. Veuillez vous reconnecter.";
                    flashMessageType = "error";
                    break;
                default:
                    flashMessage = null;
                    flashMessageType = null;
            }
        }
    }

    /**
     * Méthode de post-composition de base à appeler par les classes enfants.
     * Elle injecte un script JS pour faire disparaître la bannière après un délai.
     */
    @AfterCompose
    public void baseAfterCompose(@ContextParam(ContextType.VIEW) Component view) {
        if (flashMessage != null && !flashMessage.isEmpty()) {
            String script = "setTimeout(function() { " +
                            "  var banner = zk.Widget.$('$flashBanner'); " +
                            "  if (banner) { jq(banner.$n()).fadeOut(500); } " +
                            "}, 3500);";
            Clients.evalJavaScript(script);
        }
    }

    // --- Getters publics pour le databinding ZUL ---
    public String getFlashMessage() { return flashMessage; }
    public String getFlashMessageType() { return flashMessageType; }

    public String getBannerClass() {
        return flashMessageType == null || flashMessageType.isEmpty() ? "banner" : "banner banner--" + flashMessageType;
    }

    public String getBannerIconClass() {
        if (flashMessageType == null) return "";
        switch (flashMessageType) {
            case "success": return "z-icon-check";
            case "warn":    return "z-icon-exclamation-triangle";
            case "error":   return "z-icon-times-circle";
            default:        return "";
        }
    }
}