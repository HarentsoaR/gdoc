package mg.md2i.gedi.control.viewmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import java.util.HashMap;
import java.util.Map;

public class AdminViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(AdminViewModel.class);

    private String currentView = "/admin/views/dashboard.zul";
    private String currentPath = "Administration \u203A Tableau de bord";
    private boolean sidebarCollapsed = false;
    private boolean usersMenuExpanded = true;
    private boolean configurationsMenuExpanded = true;
    private Map<String, Object> navigationArgs = new HashMap<>();

    private String previousView = null;
    private String previousPath = null;

    @Init
    public void init() {
        navigationArgs = new HashMap<>();
    }

    public String getCurrentView() {
        return currentView;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public boolean isSidebarCollapsed() {
        return sidebarCollapsed;
    }

    public boolean isUsersMenuExpanded() {
        return usersMenuExpanded;
    }

    public boolean isConfigurationsMenuExpanded() {
        return configurationsMenuExpanded;
    }

    public Map<String, Object> getNavigationArgs() {
        return navigationArgs;
    }
    
    @Command
    @NotifyChange("sidebarCollapsed")
    public void toggleSidebar() {
        sidebarCollapsed = !sidebarCollapsed;
    }

    @Command
    @NotifyChange("usersMenuExpanded")
    public void toggleUsersMenu() {
        usersMenuExpanded = !usersMenuExpanded;
    }

    @Command
    @NotifyChange("configurationsMenuExpanded")
    public void toggleConfigurationsMenu() {
        configurationsMenuExpanded = !configurationsMenuExpanded;
    }

    @Command
    @NotifyChange({"currentView", "currentPath", "navigationArgs"})
    public void navigateTo(@BindingParam("view") String view,
                           @BindingParam("label") String label,
                           @BindingParam("section") String section,
                           @BindingParam("page") String page,
                           @BindingParam("args") Map<String, Object> args) {
        if (view == null || view.trim().isEmpty()) {
            return;
        }
        setNavigationState(view, label, section, page, args);
    }

    @GlobalCommand
    @NotifyChange({"currentView", "currentPath", "navigationArgs"})
    public void navigateToAdmin(@BindingParam("view") String view,
                                @BindingParam("label") String label,
                                @BindingParam("section") String section,
                                @BindingParam("page") String page,
                                @BindingParam("args") Map<String, Object> args) {
        navigateTo(view, label, section, page, args);
    }

    @GlobalCommand
    @NotifyChange({"currentView", "currentPath", "navigationArgs"})
    public void navigateBack() {
        if (previousView != null) {
            currentView = previousView;
            currentPath = previousPath;
            previousView = null; 
            previousPath = null;
            navigationArgs = new HashMap<>();
        } else {
            setNavigationState("/admin/views/dashboard.zul", null, "Administration", "Tableau de bord", null);
        }
    }

    @Command
    public void goHome() {
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

    private void setNavigationState(String view, String label, String section, String page, Map<String, Object> args) {
        this.previousView = this.currentView;
        this.previousPath = this.currentPath;
        currentView = view;
        currentPath = buildPath(label, section, page);
        navigationArgs = args != null ? new HashMap<>(args) : new HashMap<>();
    }
    
    private String buildPath(String label, String section, String page) {
        if (section != null && !section.trim().isEmpty()
            && page != null && !page.trim().isEmpty()) {
            return section + " \u203A " + page;
        }
        if (label != null && !label.trim().isEmpty()) {
            return label;
        }
        return "Administration";
    }
}