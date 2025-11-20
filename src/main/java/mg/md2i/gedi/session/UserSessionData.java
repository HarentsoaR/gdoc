package mg.md2i.gedi.session;

import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.entity.Candidat;

public class UserSessionData {

    private static Utilisateur userToEdit;
    private static Candidat candidatToEdit;

    public static void setUserToEdit(Utilisateur u) {
        userToEdit = u;
    }

    public static Utilisateur getUserToEdit() {
        return userToEdit;
    }

    public static void setCandidatToEdit(Candidat candidat) {
        candidatToEdit = candidat;
    }

    public static Candidat getCandidatToEdit() {
        return candidatToEdit;
    }

    public static void clearCandidat() {
        candidatToEdit = null;
    }

    public static void clear() {
        userToEdit = null;
        candidatToEdit = null;
    }
}
