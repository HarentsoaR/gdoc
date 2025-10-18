package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.security.CustomUserDetailsService;
import mg.md2i.gedi.security.jwt.JwtTokenUtil;
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
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zkplus.spring.SpringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class LoginViewModel extends BaseViewModel {

    private static final Logger logger = LoggerFactory.getLogger(LoginViewModel.class);

    private String username;
    private String password;
    private boolean isLoggingIn = false;
    private boolean passwordVisible = false;

    private boolean loginError = false;
    private String errorMessage = "";

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private CustomUserDetailsService userDetailsService;

    // --- Getters & Setters ---
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean getIsLoggingIn() { return isLoggingIn; }
    public boolean isPasswordVisible() { return passwordVisible; }
    public boolean isLoginError() { return loginError; }
    public String getErrorMessage() { return errorMessage; }

    @Init
    public void init() {
        super.baseInit();
        ApplicationContext ctx = SpringUtil.getApplicationContext();
        if (ctx != null) {
            try {
                authenticationManager = ctx.getBean(AuthenticationManager.class);
                jwtTokenUtil = ctx.getBean(JwtTokenUtil.class);
                userDetailsService = ctx.getBean(CustomUserDetailsService.class);
            } catch (Exception e) {
                logger.error("CRITICAL: Cannot fetch Spring beans for authentication.", e);
            }
        }
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        // Appelle la méthode afterCompose de la classe de base pour gérer la disparition des messages flash
        super.baseAfterCompose(view);
    }

    @Command
    @NotifyChange({"isLoggingIn", "loginError", "errorMessage", "password"})
    public void login() {
        // ✅ CORRECTION : La validation se fait maintenant ici, au clic.
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            this.loginError = true;
            this.errorMessage = "Veuillez remplir le nom d'utilisateur et le mot de passe.";
            return; // On arrête l'exécution si les champs sont vides
        }

        isLoggingIn = true;
        loginError = false; 
        errorMessage = "";

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
            if (authentication != null && authentication.isAuthenticated()) {
                // ... (le reste de la logique de succès est inchangé)
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                String token = jwtTokenUtil.generateToken(userDetails);
                
                setJwtCookie(token);
                Session session = Executions.getCurrent().getSession();
                session.setAttribute("jwtToken", token);
                session.setAttribute("username", username);
                session.setAttribute("authenticated", true);
                
                Executions.sendRedirect("/home?message=login_success");
            }
        } catch (BadCredentialsException e) {
            loginError = true;
            errorMessage = "Nom d'utilisateur ou mot de passe incorrect.";
        } catch (AuthenticationException e) {
            logger.warn("Authentication failed for user {}: {}", username, e.getMessage());
            loginError = true;
            errorMessage = "Un problème est survenu lors de l'authentification.";
        } finally {
            isLoggingIn = false;
            password = null;
        }
    }

    @Command
    @NotifyChange({"passwordInputType", "passwordVisibilityIcon"})
    public void togglePasswordVisibility() {
        this.passwordVisible = !this.passwordVisible;
    }

    public String getPasswordInputType() {
        return passwordVisible ? "text" : "password";
    }

    public String getPasswordVisibilityIcon() {
        return passwordVisible ? "z-icon-eye-slash" : "z-icon-eye";
    }

    // ✅ SUPPRESSION : La méthode onCredentialsChange a été entièrement supprimée pour la simplicité.

    private void setJwtCookie(String token) {
        // ... (cette méthode reste inchangée)
        try {
            HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
            String ctx = Executions.getCurrent().getContextPath();
            Cookie jwtCookie = new Cookie("jwtToken", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(Executions.getCurrent().getScheme().equals("https"));
            jwtCookie.setPath(ctx.isEmpty() ? "/" : ctx);
            jwtCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(jwtCookie);
        } catch (Exception e) {
            logger.error("Failed to set JWT cookie.", e);
        }
    }
}