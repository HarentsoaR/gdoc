package mg.md2i.gedi.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import mg.md2i.gedi.security.CustomUserDetailsService;
import mg.md2i.gedi.security.jwt.JwtTokenUtil;

public class LoginControl extends SelectorComposer<Component> {

    private static final Logger logger = LoggerFactory.getLogger(LoginControl.class);

    @Wire
    private Textbox tbLogin;
    @Wire
    private Textbox tbPwd;

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private CustomUserDetailsService userDetailsService;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        logger.debug("LoginControl initialized.");

        // Get beans from Spring context (if not @Autowired directly)
        ApplicationContext ctx = SpringUtil.getApplicationContext();
        if (ctx != null) {
            try {
                authenticationManager = ctx.getBean(AuthenticationManager.class);
                jwtTokenUtil = ctx.getBean(JwtTokenUtil.class);
                userDetailsService = ctx.getBean(CustomUserDetailsService.class);
                logger.debug("Successfully retrieved beans from Spring context.");
            } catch (Exception e) {
                logger.error("Failed to retrieve beans from Spring context.", e);
            }
        }

        if (authenticationManager == null || jwtTokenUtil == null || userDetailsService == null) {
            logger.error("One or more essential authentication beans are null. Check Spring configuration.");
            Clients.showNotification("Erreur de configuration du serveur d'authentification.", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 5000, true);
        }
    }

    @Listen("onClick = #confirmBtn; onOK = #tbPwd")
    public void doLogin() {
        String username = tbLogin.getValue();
        String password = tbPwd.getValue();

        logger.info("Login attempt for user: '{}'", username);
        logger.debug("Raw username input: '{}', Raw password input (length): {}", username, (password != null ? password.length() : "null"));

        if (authenticationManager == null) {
            logger.error("AuthenticationManager is null. Cannot proceed with authentication.");
            Clients.showNotification("Erreur: AuthenticationManager introuvable.", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 4000, true);
            return;
        }

        try {
            logger.trace("Attempting authentication with UsernamePasswordAuthenticationToken for user: '{}'", username);
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication != null && authentication.isAuthenticated()) {
                logger.info("User '{}' authenticated successfully.", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // Generate JWT token
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                String token = jwtTokenUtil.generateToken(userDetails);
                logger.debug("Generated JWT token for user '{}'.", username);
                
                // Set JWT token in cookie
                setJwtCookie(token, username);
                
                // Store user info in session
                Session session = Executions.getCurrent().getSession();
                session.setAttribute("jwtToken", token);
                session.setAttribute("username", username);
                session.setAttribute("authenticated", true);
                
                // Redirect to main application with success message
                Executions.sendRedirect("/index.zul?message=login_success"); // Use absolute path relative to context root
            } else {
                logger.warn("Authentication failed for user '{}'.", username);
                Clients.showNotification("Authentification échouée.", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 3000, true);
            }
        } catch (BadCredentialsException e) {
            logger.warn("Invalid credentials for user '{}'", username);
            Clients.showNotification("Identifiants invalides.", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 3000, true);
        } catch (AuthenticationException e) {
            logger.error("Authentication error for user '{}'", username, e);
            Clients.showNotification("Erreur d'authentification: " + e.getMessage(), Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 4000, true);
        } catch (Exception e) {
            logger.error("An unexpected error occurred during login for user '{}'", username, e);
            Clients.showNotification("Erreur: " + e.getMessage(), Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 4000, true);
        } finally {
            // Clear the password field
            tbPwd.setValue(null);
        }
    }
    
    private void setJwtCookie(String token, String username) {
        try {
            HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
            String ctx = Executions.getCurrent().getContextPath();
            Cookie jwtCookie = new Cookie("jwtToken", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false); // Set to true in production with HTTPS
            jwtCookie.setPath(ctx);
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(jwtCookie);
            logger.debug("Set JWT cookie for user '{}'. Path: {}", username, ctx);
        } catch (Exception e) {
            logger.error("Error setting JWT cookie for user '{}'", username, e);
        }
    }
}
