package mg.md2i.gedi.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeControl extends SelectorComposer<Component> {

	private static final Logger logger = LoggerFactory.getLogger(HomeControl.class);

	@Wire
	private Button logoutBtn;

	@Listen("onClick = #logoutBtn")
	public void doLogout() {
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
			logger.error("Error during logout", e);
			Executions.sendRedirect("/?message=logout_success");
		}
	}
}
