package mg.md2i.gedi.security;

import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.session.UserSession;
import mg.md2i.gedi.gestionmetier.FonctionnaliteGestion;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.util.UiLifeCycle;
import org.zkoss.zk.ui.ShadowElement;

public abstract class PageAccessFilter implements UiLifeCycle {

    @Override
    public void afterComponentAttached(Component comp, Page page) {
        checkAccess(page);
    }

    private void checkAccess(Page page) {
        String uri = page.getRequestPath(); // Example: /admin/views/utilisateurs/list.zul

        Utilisateur user = UserSession.get();
        if (user == null) return;

        // 1. Determine "nomTable" from URI
        String nomTable = resolveNomTableFromUri(uri);
        if (nomTable == null) return; // Page not securised

        // 2. Get fonctionnalite ID from table name
        Integer fonctionnaliteId =
                FonctionnaliteGestion.findFonctionnaliteIdByNomTable(nomTable);

        if (fonctionnaliteId == null) return; // No permissions linked to this table

        // 3. Check permission
        boolean allowed =
                AccessControlService.can(user, nomTable, "lire");

        if (!allowed) {
            Executions.sendRedirect("/admin/views/errors/403.zul");
        }
    }

    /**
     * Map URI → nomTable from t_fonctionnalite
     * You must adjust the mapping based on your modules.
     */
    private String resolveNomTableFromUri(String uri) {

        uri = uri.toLowerCase();

        if (uri.contains("/utilisateurs/")) return "utilisateur";
        if (uri.contains("/profils/"))        return "profil";
        if (uri.contains("/acces/"))          return "fonctionnalite_profil";
        if (uri.contains("/explorer/"))       return "dossier_candidat";
        if (uri.contains("/concours/"))       return "concours";
        if (uri.contains("/import/"))         return "import_document";
        if (uri.contains("/dashboard/"))      return "dashboard";

        // Add more mappings depending on your app structure

        return null; // Unknown page → not secured → free access
    }

    @Override public void afterPageAttached(Page page, Desktop desktop) {}
    @Override public void afterComponentDetached(Component comp, Page page) {}
    @Override public void afterPageDetached(Page page, Desktop desktop) {}
    @Override public void afterShadowAttached(ShadowElement shadowElement, Component component) {}
    @Override public void afterShadowDetached(ShadowElement shadowElement, Component component) {}
}
