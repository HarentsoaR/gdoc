package mg.md2i.gedi.control.viewmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import mg.md2i.gedi.control.HomeControl;

public class MainViewModel {

    private static final Logger logger = LoggerFactory.getLogger(MainViewModel.class);

    private String currentPage;
    private boolean adminVisible;

    @Init
    public void init() {
        Session session = Executions.getCurrent().getSession();
        Object username = session.getAttribute("username");

        // show admin button only if username is '1' or contains 'admin'
        adminVisible = username != null && (
            "1".equals(username.toString()) ||
            username.toString().toLowerCase().contains("admin")
        );

        // default page
        currentPage = "/admin/utilisateurs/list.zul";
    }

    public boolean isAdminVisible() {
        return adminVisible;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    @Command
    @NotifyChange("currentPage")
    public void navigateTo(@BindingParam("page") String page) {
        switch (page) {
            case "utilisateurs": currentPage = "/admin/utilisateurs/list.zul"; break;
            case "documents": currentPage = "/admin/documents.zul"; break;
            case "audit": currentPage = "/admin/audit.zul"; break;
            case "access": currentPage = "/admin/acces.zul"; break;
            case "parametres": currentPage = "/admin/parametres.zul"; break;
            default: currentPage = "/admin/utilisateurs/list.zul";
        }
    }

    @Command
    public void doLogout() {
        try {
            new HomeControl().doLogout();
        } catch (Exception e) {
            logger.warn("Fallback logout: clearing session only", e);
            Session session = Executions.getCurrent().getSession();
            session.removeAttribute("authenticated");
            session.removeAttribute("username");
            session.removeAttribute("jwtToken");
            Executions.sendRedirect("/");
        }
    }
}
