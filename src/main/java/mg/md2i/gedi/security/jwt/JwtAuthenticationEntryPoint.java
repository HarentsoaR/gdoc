package mg.md2i.gedi.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        logger.warn("Unauthenticated access attempt to {}: {}", request.getRequestURI(), authException.getMessage());

        // OLD API BEHAVIOR:
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        // NEW WEB APP BEHAVIOR: Redirect to the login page
        // We use request.getContextPath() to make the URL relative to the application's root
        response.sendRedirect(request.getContextPath() + "/?message=session_expired");
    }
}