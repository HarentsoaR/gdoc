package mg.md2i.gedi.security;

import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user by username: '{}'", username);
        Utilisateur utilisateur = utilisateurRepository.findByLogin(username)
                .orElseThrow(() -> {
                    logger.warn("User not found with login: '{}'", username);
                    return new UsernameNotFoundException("User not found with login: " + username);
                });

        logger.info("User '{}' found. Mapping to UserDetails.", username);
        logger.trace("Retrieved encoded password for user '{}': '{}'", username, utilisateur.getPassword());
        return new User(utilisateur.getLogin(), utilisateur.getPassword(), Collections.emptyList());
    }
}