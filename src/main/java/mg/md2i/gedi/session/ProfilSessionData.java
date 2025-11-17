package mg.md2i.gedi.session;

import mg.md2i.gedi.entity.Profil;

public class ProfilSessionData {

    private static Profil profilToEdit;

    public static void setProfilToEdit(Profil p) {
        profilToEdit = p;
    }

    public static Profil getProfilToEdit() {
        return profilToEdit;
    }

    public static void clear() {
        profilToEdit = null;
    }
}
