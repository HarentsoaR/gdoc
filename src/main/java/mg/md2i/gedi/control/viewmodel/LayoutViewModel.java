package mg.md2i.gedi.control.viewmodel;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import java.util.ArrayList;
import java.util.List;

public class LayoutViewModel {

    private String currentPage = "/admin/dashboard.zul";
    private boolean sidebarVisible = true; // default expanded
    private final List<String> breadcrumbs = new ArrayList<>();
    private boolean usersMenuOpen = false;

    @Init
    public void init() {
        updateBreadcrumbs(currentPage);
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public boolean isSidebarVisible() {
        return sidebarVisible;
    }

    public boolean isUsersMenuOpen() {
        return usersMenuOpen;
    }

    @Command
    @NotifyChange("sidebarVisible")
    public void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
    }

    @Command
    @NotifyChange("usersMenuOpen")
    public void toggleUsersMenu() {
        usersMenuOpen = !usersMenuOpen;
    }

    @Command
    @NotifyChange({"currentPage", "breadcrumbs", "sidebarVisible"})
    public void navigateTo(@BindingParam("page") String page) {
        if (page == null || page.equals(currentPage)) return;
        currentPage = page;
        updateBreadcrumbs(page);
        sidebarVisible = false; // auto-close on mobile
    }

    private void updateBreadcrumbs(String page) {
        breadcrumbs.clear();
        if (page.contains("dashboard")) {
            breadcrumbs.add("Tableau de bord");
        } else if (page.contains("utilisateurs")) {
            breadcrumbs.add("Utilisateurs");
            if (page.contains("list")) breadcrumbs.add("Liste");
            else if (page.contains("new")) breadcrumbs.add("Nouveau");
            else if (page.contains("profils")) breadcrumbs.add("Profils");
            else if (page.contains("acces")) breadcrumbs.add("Accès");
            else if (page.contains("audit")) breadcrumbs.add("Audit");
        } else if (page.contains("documents")) {
            breadcrumbs.add("Documents");
        } else if (page.contains("parametres")) {
            breadcrumbs.add("Paramètres");
        }
    }

    public List<String> getBreadcrumbs() {
        return new ArrayList<>(breadcrumbs);
    }

    @Command
    public void goHome() {
        // Redirige vers la page d'accueil principale de l'application
        Executions.sendRedirect("/home");
    }

    @Command
    public void doLogout() {
        Session session = Executions.getCurrent().getSession();
        session.removeAttribute("authenticated");
        session.removeAttribute("username");
        session.removeAttribute("jwtToken");
        Executions.sendRedirect("/");
    }
}
