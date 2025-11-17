package mg.md2i.gedi.session;

import mg.md2i.gedi.entity.Utilisateur;

public class UserSessionData {

    private static Utilisateur userToEdit;

    public static void setUserToEdit(Utilisateur u) {
        userToEdit = u;
    }

    public static Utilisateur getUserToEdit() {
        return userToEdit;
    }

    public static void clear() {
        userToEdit = null;
    }
}
