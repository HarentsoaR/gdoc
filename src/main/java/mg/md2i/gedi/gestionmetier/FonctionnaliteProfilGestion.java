package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.FonctionnaliteProfil;
import mg.md2i.gedi.services.FonctionnaliteProfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FonctionnaliteProfilGestion {

    private static final Logger log = LoggerFactory.getLogger(FonctionnaliteProfilGestion.class);

    private static FonctionnaliteProfilService getService() {
        return ObjectFactory.getBean(FonctionnaliteProfilService.class);
    }

    public static List<FonctionnaliteProfil> findFonctionnaliteProfilsByProfilId(Integer profilId) {
        log.info("↘️ [Gestion] Appel de la couche Service (FonctionnaliteProfilService.getFonctionnaliteProfilsByProfilId())...");
        List<FonctionnaliteProfil> result = getService().getFonctionnaliteProfilsByProfilId(profilId);
        if (result == null) {
            log.warn("⚠️ [Gestion] La couche Service a retourné NULL pour le profil {}, remplacement par une liste vide.", profilId);
            return new ArrayList<>();
        }
        return result;
    }

    public static FonctionnaliteProfil findFonctionnaliteProfilByProfilIdAndFonctionnaliteId(Integer profilId, Integer fonctionnaliteId) {
        log.info("🔍 [Gestion] Recherche de FonctionnaliteProfil pour profil ID={} et fonctionnalité ID={}", profilId, fonctionnaliteId);
        return getService().getFonctionnaliteProfilByProfilIdAndFonctionnaliteId(profilId, fonctionnaliteId);
    }

    public static void saveFonctionnaliteProfil(FonctionnaliteProfil fonctionnaliteProfil) {
        log.info("💾 [Gestion] Sauvegarde de FonctionnaliteProfil pour profil ID={} et fonctionnalité ID={}", fonctionnaliteProfil.getProfilId(), fonctionnaliteProfil.getFonctionnaliteId());
        getService().saveFonctionnaliteProfil(fonctionnaliteProfil);
    }

    public static void deleteFonctionnaliteProfil(Integer id) {
        log.warn("🗑️ [Gestion] Suppression de FonctionnaliteProfil ID={}", id);
        getService().deleteFonctionnaliteProfil(id);
    }

    public static void deleteAllFonctionnaliteProfilsByProfilId(Integer profilId) {
        log.warn("🗑️ [Gestion] Suppression de tous les FonctionnaliteProfils pour le profil ID={}", profilId);
        getService().deleteAllFonctionnaliteProfilsByProfilId(profilId);
    }
    
    public static List<FonctionnaliteProfil> findAndCleanByProfilId(Integer profilId) {

        List<FonctionnaliteProfil> list = findFonctionnaliteProfilsByProfilId(profilId);
        if (list == null || list.isEmpty()) return new ArrayList<>();

        Map<Integer, FonctionnaliteProfil> unique = new LinkedHashMap<>();
        List<FonctionnaliteProfil> duplicates = new ArrayList<>();

        for (FonctionnaliteProfil fp : list) {
            Integer funcId = fp.getFonctionnaliteId();

            if (!unique.containsKey(funcId)) {
                unique.put(funcId, fp);
            } else {
                // DUPLICATE FOUND IN DATABASE
                duplicates.add(fp);

                FonctionnaliteProfil keep = unique.get(funcId);

                keep.setLire(max(keep.getLire(), fp.getLire()));
                keep.setNouveau(max(keep.getNouveau(), fp.getNouveau()));
                keep.setModifier(max(keep.getModifier(), fp.getModifier()));
                keep.setSupprimer(max(keep.getSupprimer(), fp.getSupprimer()));
                keep.setExporter(max(keep.getExporter(), fp.getExporter()));
                keep.setDupliquer(max(keep.getDupliquer(), fp.getDupliquer()));
                keep.setActif(1);

                saveFonctionnaliteProfil(keep);
            }
        }

        // DELETE duplicates safely
        for (FonctionnaliteProfil fp : duplicates) {
            deleteFonctionnaliteProfil(fp.getFonctionnaliteProfilId());
        }

        return new ArrayList<>(unique.values());
    }

    private static int max(Integer a, Integer b) {
        return (a != null && a == 1) || (b != null && b == 1) ? 1 : 0;
    }

}
