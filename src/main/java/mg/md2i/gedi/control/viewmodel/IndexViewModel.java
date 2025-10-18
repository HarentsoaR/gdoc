package mg.md2i.gedi.control.viewmodel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.bind.annotation.ContextParam;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
 
public class IndexViewModel extends BaseViewModel {
 
    private boolean adminVisible;

    @Init
    public void init() {
        // Appelle l'initialisation de la classe de base pour gérer les messages flash
        super.baseInit();

        // Vérification du rôle pour l'accès admin
        Session session = Sessions.getCurrent();
        Object username = session.getAttribute("username");
        adminVisible = username != null && (
                "1".equals(username.toString()) ||
                username.toString().toLowerCase().contains("admin"));
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        // Appelle la méthode afterCompose de la classe de base pour gérer la disparition des messages flash
        super.baseAfterCompose(view);
    }

    public boolean isAdminVisible() { return adminVisible; }

    @Command
    public void goDocs() { Executions.sendRedirect("/documents"); }

    @Command
    public void goBiblio() { Executions.sendRedirect("/biblio"); }

    @Command
    public void goAdmin() { Executions.sendRedirect("/admin"); }

    @Command
    public void logout() {
		try {
			// Clear ZK session attributes
			Session session = Executions.getCurrent().getSession();
			session.removeAttribute("authenticated");
			session.removeAttribute("username");
			session.removeAttribute("jwtToken");
			// Invalidate session
			try { session.invalidate(); } catch (Exception ignored) {}

			// Remove JWT cookie
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if ("jwtToken".equals(c.getName())) {
						Cookie cleared = new Cookie("jwtToken", "");
						cleared.setPath(Executions.getCurrent().getContextPath());
						cleared.setHttpOnly(true);
						cleared.setMaxAge(0);
						response.addCookie(cleared);
						break;
					}
				}
			}

			// Redirect to /login with success message
			Executions.sendRedirect("/?message=logout_success");
		} catch (Exception e) {
			Executions.sendRedirect("/home?message=logout_failed");
		}
	}
}