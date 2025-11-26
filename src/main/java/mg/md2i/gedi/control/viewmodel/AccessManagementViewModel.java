package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Fonctionnalite;
import mg.md2i.gedi.entity.FonctionnaliteProfil;
import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.gestionmetier.FonctionnaliteGestion;
import mg.md2i.gedi.gestionmetier.FonctionnaliteProfilGestion;
import mg.md2i.gedi.gestionmetier.ProfilGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.util.Clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AccessManagementViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(AccessManagementViewModel.class);

    // --------------------------------------------------------------------
    // STATE
    // --------------------------------------------------------------------
    private List<Profil> profils = new ArrayList<>();
    private Profil selectedProfil;
    private List<FonctionnalitePermissionItem> permissionItems = new ArrayList<>();
    private List<FonctionnalitePermissionItem> allPermissionItems = new ArrayList<>();
    private boolean filtersVisible = true;
    private String profilFilter = "";
    private String permissionFilter = "";

    // --------------------------------------------------------------------
    // DTO pour la grille (une ligne = une fonctionnalité)
    // --------------------------------------------------------------------
    public static class FonctionnalitePermissionItem {
        private final Fonctionnalite f;
        private FonctionnaliteProfil fp;
        private boolean canRead;
        private boolean canCreate;
        private boolean canUpdate;
        private boolean canDelete;
        private boolean canImport;
        private boolean canDuplicate;

        public FonctionnalitePermissionItem(Fonctionnalite f, FonctionnaliteProfil fp) {
            this.f = f;
            this.fp = fp;
            this.canRead      = fp != null && fp.getLire()      != null && fp.getLire()      == 1;
            this.canCreate    = fp != null && fp.getNouveau()   != null && fp.getNouveau()   == 1;
            this.canUpdate    = fp != null && fp.getModifier()  != null && fp.getModifier()  == 1;
            this.canDelete    = fp != null && fp.getSupprimer() != null && fp.getSupprimer() == 1;
            this.canImport    = fp != null && fp.getExporter()  != null && fp.getExporter()  == 1;
            this.canDuplicate = fp != null && fp.getDupliquer() != null && fp.getDupliquer() == 1;
        }

        public boolean hasAnyPermission() {
            return canRead || canCreate || canUpdate || canDelete || canImport || canDuplicate;
        }

        public Fonctionnalite getF() { return f; }

        public FonctionnaliteProfil getFp() { return fp; }
        public void setFp(FonctionnaliteProfil fp) { this.fp = fp; }

        public boolean isCanRead() { return canRead; }
        public void setCanRead(boolean v) { this.canRead = v; }

        public boolean isCanCreate() { return canCreate; }
        public void setCanCreate(boolean v) { this.canCreate = v; }

        public boolean isCanUpdate() { return canUpdate; }
        public void setCanUpdate(boolean v) { this.canUpdate = v; }

        public boolean isCanDelete() { return canDelete; }
        public void setCanDelete(boolean v) { this.canDelete = v; }

        public boolean isCanImport() { return canImport; }
        public void setCanImport(boolean v) { this.canImport = v; }

        public boolean isCanDuplicate() { return canDuplicate; }
        public void setCanDuplicate(boolean v) { this.canDuplicate = v; }
    }

    // --------------------------------------------------------------------
    // INIT
    // --------------------------------------------------------------------
    @Init
    @NotifyChange({"profils", "selectedProfil", "permissionItems"})
    public void init() {
        loadProfils();
        if (!profils.isEmpty()) {
            this.selectedProfil = profils.get(0);
            loadPermissionsForProfil(this.selectedProfil);
        }
    }

    private void loadProfils() {
        try {
            List<Profil> result = ProfilGestion.findAllProfils();
            if (result == null) {
                result = new ArrayList<Profil>();
            }
            // Only keep ADMIN + service_id = 40 (cellule concours)
            result = result.stream()
                    .filter(this::isProfilAllowed)
                    .collect(Collectors.toList());

            if (profilFilter != null && !profilFilter.trim().isEmpty()) {
                String lower = profilFilter.toLowerCase();
                result = result.stream()
                        .filter(p -> p.getLibelle() != null && p.getLibelle().toLowerCase().contains(lower))
                        .collect(Collectors.toList());
            }
            profils = result;
        } catch (Exception e) {
            LOG.error("Erreur lors du chargement des profils", e);
            profils = new ArrayList<Profil>();
            Clients.showNotification("Erreur au chargement des profils", "error", null, "middle_center", 3000);
        }
    }

    // --------------------------------------------------------------------
    // CHARGER LES PERMISSIONS POUR UN PROFIL
    // --------------------------------------------------------------------
    @Command
    @NotifyChange({"selectedProfil","permissionItems"})
    public void loadPermissionsForProfil(@BindingParam("profil") Profil profil) {
        if (profil == null) {
            allPermissionItems = new ArrayList<>();
            permissionItems = new ArrayList<FonctionnalitePermissionItem>();
            return;
        }

        this.selectedProfil = profil;

        try {
            List<Fonctionnalite> allFuncs = FonctionnaliteGestion.findAllFonctionnalites();
            if (allFuncs == null) {
                allFuncs = new ArrayList<Fonctionnalite>();
            }

            List<FonctionnaliteProfil> profilPermissions =
                    FonctionnaliteProfilGestion.findFonctionnaliteProfilsByProfilId(profil.getProfilId());
            if (profilPermissions == null) {
                profilPermissions = new ArrayList<FonctionnaliteProfil>();
            }

            // Nettoyer les doublons éventuels (même fonctionnalité_id plusieurs fois)
            profilPermissions = normalizePermissions(profil.getProfilId(), profilPermissions);

            Map<Integer, FonctionnaliteProfil> existingPermissions = profilPermissions.stream()
                    .collect(Collectors.toMap(
                            FonctionnaliteProfil::getFonctionnaliteId,
                            fp -> fp,
                            // en cas de doublon inattendu, on garde le premier
                            (fp1, fp2) -> fp1,
                            java.util.LinkedHashMap::new
                    ));

            allPermissionItems = new ArrayList<>();
            for (Fonctionnalite f : allFuncs) {
                FonctionnaliteProfil fp = existingPermissions.get(f.getFonctionnaliteId());
                allPermissionItems.add(new FonctionnalitePermissionItem(f, fp));
            }
            applyPermissionFilter();

            LOG.info("Profil {} → {} fonctionnalités, {} permissions existantes, {} items en grille",
                    profil.getProfilId(), allFuncs.size(), profilPermissions.size(), permissionItems.size());

        } catch (Exception e) {
            LOG.error("Erreur lors du chargement des permissions pour le profil {}", profil.getProfilId(), e);
            permissionItems = new ArrayList<FonctionnalitePermissionItem>();
            Clients.showNotification("Erreur lors du chargement des permissions", "error", null, "top_center", 3000);
        }
    }

    // --------------------------------------------------------------------
    // SAUVEGARDE
    // --------------------------------------------------------------------
    @Command
    public void savePermissions() {
        if (selectedProfil == null || permissionItems == null) {
            Clients.showNotification("Aucun profil sélectionné pour la sauvegarde.", "warning", null, "top_center", 2000);
            return;
        }

        try {
            List<FonctionnalitePermissionItem> target = allPermissionItems != null ? allPermissionItems : permissionItems;
            for (FonctionnalitePermissionItem item : target) {
                FonctionnaliteProfil fp = item.getFp();
                boolean existsInDb = fp != null && fp.getFonctionnaliteProfilId() != null;

                if (item.hasAnyPermission()) {
                    if (!existsInDb) {
                        fp = new FonctionnaliteProfil();
                        fp.setProfilId(selectedProfil.getProfilId());
                        fp.setFonctionnaliteId(item.getF().getFonctionnaliteId());
                        item.setFp(fp);
                    }
                    fp.setLire(item.isCanRead() ? 1 : 0);
                    fp.setNouveau(item.isCanCreate() ? 1 : 0);
                    fp.setModifier(item.isCanUpdate() ? 1 : 0);
                    fp.setSupprimer(item.isCanDelete() ? 1 : 0);
                    fp.setExporter(item.isCanImport() ? 1 : 0);
                    fp.setDupliquer(item.isCanDuplicate() ? 1 : 0);
                    fp.setActif(1);
                    FonctionnaliteProfilGestion.saveFonctionnaliteProfil(fp);
                } else if (existsInDb) {
                    FonctionnaliteProfilGestion.deleteFonctionnaliteProfil(fp.getFonctionnaliteProfilId());
                    item.setFp(null);
                }
            }
            Clients.showNotification("Permissions sauvegardées avec succès", "info", null, "top_center", 2000);
        } catch (Exception e) {
            LOG.error("Erreur lors de la sauvegarde des permissions pour le profil {}", selectedProfil.getProfilId(), e);
            Clients.showNotification("Échec de la sauvegarde des permissions", "error", null, "top_center", 3000);
        }
    }

    // --------------------------------------------------------------------
    // GETTERS / SETTERS (nécessaires pour ZK)
    // --------------------------------------------------------------------
    public List<Profil> getProfils() {
        return profils;
    }

    public Profil getSelectedProfil() {
        return selectedProfil;
    }

    public void setSelectedProfil(Profil selectedProfil) {
        this.selectedProfil = selectedProfil;
    }

    public List<FonctionnalitePermissionItem> getPermissionItems() {
        return permissionItems;
    }

    @Command
    @NotifyChange("filtersVisible")
    public void toggleFilters() {
        filtersVisible = !filtersVisible;
    }

    @Command
    @NotifyChange({"profils", "selectedProfil", "permissionItems"})
    public void filterProfils(@BindingParam("query") String query) {
        profilFilter = query != null ? query.trim() : "";
        loadProfils();
        if (!profils.isEmpty()) {
            selectedProfil = profils.get(0);
            loadPermissionsForProfil(selectedProfil);
        } else {
            selectedProfil = null;
            permissionItems = new ArrayList<FonctionnalitePermissionItem>();
        }
    }

    @Command
    @NotifyChange("permissionItems")
    public void filterPermissions(@BindingParam("query") String query) {
        permissionFilter = query != null ? query.trim() : "";
        applyPermissionFilter();
    }

    public boolean isFiltersVisible() {
        return filtersVisible;
    }

    public String getProfilFilter() {
        return profilFilter;
    }

    public String getPermissionFilter() {
        return permissionFilter;
    }

    public void setPermissionFilter(String permissionFilter) {
        this.permissionFilter = permissionFilter;
    }

    private void applyPermissionFilter() {
        if (allPermissionItems == null) {
            permissionItems = new ArrayList<>();
            return;
        }
        String filter = permissionFilter != null ? permissionFilter.trim().toLowerCase() : "";
        if (filter.isEmpty()) {
            permissionItems = new ArrayList<>(allPermissionItems);
            return;
        }
        permissionItems = allPermissionItems.stream()
                .filter(item -> {
                    String libelle = item.getF().getLibelle() != null ? item.getF().getLibelle().toLowerCase() : "";
                    String table = item.getF().getNomTable() != null ? item.getF().getNomTable().toLowerCase() : "";
                    return libelle.contains(filter) || table.contains(filter);
                })
                .collect(Collectors.toList());
    }

    // --------------------------------------------------------------------
    // NORMALISATION DES DOUBLONS
    // --------------------------------------------------------------------
    private List<FonctionnaliteProfil> normalizePermissions(Integer profilId,
                                                            List<FonctionnaliteProfil> profilPermissions) {
        if (profilPermissions == null || profilPermissions.size() <= 1) {
            return profilPermissions;
        }

        Map<Integer, FonctionnaliteProfil> unique = new java.util.LinkedHashMap<Integer, FonctionnaliteProfil>();
        List<FonctionnaliteProfil> duplicates = new ArrayList<FonctionnaliteProfil>();
        java.util.Set<FonctionnaliteProfil> updated = new java.util.LinkedHashSet<FonctionnaliteProfil>();

        for (FonctionnaliteProfil fp : profilPermissions) {
            Integer funcId = fp.getFonctionnaliteId();
            FonctionnaliteProfil existing = unique.get(funcId);
            if (existing == null) {
                unique.put(funcId, fp);
            } else {
                LOG.warn("Doublon FonctionnaliteProfil pour fonctionnalite_id={} profil_id={}", funcId, profilId);
                if (mergeRights(existing, fp)) {
                    updated.add(existing);
                }
                duplicates.add(fp);
            }
        }

        // Suppression des doublons en base
        for (FonctionnaliteProfil duplicate : duplicates) {
            try {
                FonctionnaliteProfilGestion.deleteFonctionnaliteProfil(duplicate.getFonctionnaliteProfilId());
            } catch (Exception ex) {
                LOG.error("Impossible de supprimer le doublon FonctionnaliteProfil {}", duplicate.getFonctionnaliteProfilId(), ex);
            }
        }

        // Sauvegarde des droits fusionnés
        for (FonctionnaliteProfil fp : updated) {
            try {
                FonctionnaliteProfilGestion.saveFonctionnaliteProfil(fp);
            } catch (Exception ex) {
                LOG.error("Impossible de sauvegarder les droits fusionnés pour fonctionnalite_id={} profil_id={}",
                        fp.getFonctionnaliteId(), profilId, ex);
            }
        }

        return new ArrayList<FonctionnaliteProfil>(unique.values());
    }

    private boolean mergeRights(FonctionnaliteProfil keep, FonctionnaliteProfil duplicate) {
        boolean changed = false;
        if (toInt(keep.getLire()) == 0 && toInt(duplicate.getLire()) == 1) { keep.setLire(1); changed = true; }
        if (toInt(keep.getNouveau()) == 0 && toInt(duplicate.getNouveau()) == 1) { keep.setNouveau(1); changed = true; }
        if (toInt(keep.getModifier()) == 0 && toInt(duplicate.getModifier()) == 1) { keep.setModifier(1); changed = true; }
        if (toInt(keep.getSupprimer()) == 0 && toInt(duplicate.getSupprimer()) == 1) { keep.setSupprimer(1); changed = true; }
        if (toInt(keep.getExporter()) == 0 && toInt(duplicate.getExporter()) == 1) { keep.setExporter(1); changed = true; }
        if (toInt(keep.getDupliquer()) == 0 && toInt(duplicate.getDupliquer()) == 1) { keep.setDupliquer(1); changed = true; }
        if (toInt(keep.getActif()) == 0 && toInt(duplicate.getActif()) == 1) { keep.setActif(1); changed = true; }
        return changed;
    }

    private int toInt(Integer value) {
        return value != null ? value.intValue() : 0;
    }

    private boolean isProfilAllowed(Profil p) {
        if (p == null) return false;
        // Admin profiles are always allowed
        String lib = p.getLibelle() != null ? p.getLibelle().trim().toUpperCase() : "";
        if ("ADMINISTRATEUR".equals(lib) || "ADMIN".equals(lib)) {
            return true;
        }
        // Only keep service_id = 40 (Cellule concours)
        return p.getServiceId() != null && p.getServiceId() == 40;
    }
}
