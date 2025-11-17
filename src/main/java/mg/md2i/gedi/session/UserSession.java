package mg.md2i.gedi.session;

import mg.md2i.gedi.entity.Utilisateur;
import org.zkoss.zk.ui.Sessions;

public class UserSession {

    private static final String USER_KEY = "gediLoggedUser";

    public static void set(Utilisateur user) {
        Sessions.getCurrent().setAttribute(USER_KEY, user);
    }

    public static Utilisateur get() {
        return (Utilisateur) Sessions.getCurrent().getAttribute(USER_KEY);
    }

    public static void clear() {
        Sessions.getCurrent().removeAttribute(USER_KEY);
    }
}
