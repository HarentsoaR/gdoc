package mg.md2i.gedi.security;

import mg.md2i.gedi.entity.FonctionnaliteProfil;
import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.FonctionnaliteProfilGestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessControlService {

    // Cache permission: profilId → fonctionnaliteId → permission object
    private static final Map<Integer, Map<Integer, FonctionnaliteProfil>> permissionCache = new HashMap<>();

    public static void loadPermissionsForUser(Utilisateur user) {
        if (user == null || user.getProfilId() == null) return;
        if (permissionCache.containsKey(user.getProfilId())) return;

        List<FonctionnaliteProfil> list =
                FonctionnaliteProfilGestion.findFonctionnaliteProfilsByProfilId(user.getProfilId());

        Map<Integer, FonctionnaliteProfil> map = new HashMap<>();
        for (FonctionnaliteProfil fp : list) {
            map.put(fp.getFonctionnaliteId(), fp);
        }

        permissionCache.put(user.getProfilId(), map);
    }

    @SuppressWarnings("unlikely-arg-type")
	public static boolean can(Utilisateur user, String nomTable, String action) {

        loadPermissionsForUser(user);

        Map<Integer, FonctionnaliteProfil> map = permissionCache.get(user.getProfilId());
        if (map == null) return false;

        FonctionnaliteProfil fp = map.get(nomTable);
        if (fp == null) return false;

        switch (action) {
            case "lire": return fp.getLire() == 1;
            case "creer": return fp.getNouveau() == 1;
            case "modifier": return fp.getModifier() == 1;
            case "supprimer": return fp.getSupprimer() == 1;
            case "exporter": return fp.getExporter() == 1;
            case "dupliquer": return fp.getDupliquer() == 1;
            default: return false;
        }
    }
}
