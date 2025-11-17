package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.repository.UtilisateurRepository;
import mg.md2i.gedi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import mg.md2i.enmg.tools.Encodage; // Import Encodage class

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public List<Utilisateur> getAllUsers() {
        log.info("↘️ [Service] Appel du Repository (findAll())... C'est ici qu'on interroge la BDD.");
        List<Utilisateur> usersFromDb = utilisateurRepository.findAll();
        log.info("✅ [Service] Le Repository a retourné {} utilisateurs depuis la base de données.", (usersFromDb != null ? usersFromDb.size() : "NULL"));
        return usersFromDb;
    }
    
    // ... Le reste de votre code reste identique ...

    @Override
    public Utilisateur getUserById(Integer id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    @Override
    public Utilisateur saveUser(Utilisateur user) {
        // Encrypt password if it's a new user or password has been changed
        if (user.getUtilisateurId() == null || (user.getPassword() != null && !user.getPassword().isEmpty())) {
            try {
                Encodage enc = new Encodage();
                byte[] encryptedPasswordBytes = enc.encrypt(user.getPassword());
                user.setPassword(enc.byteToString(encryptedPasswordBytes)); // Store as String
            } catch (Exception e) {
                log.error("Error encrypting password for user: {}", user.getLogin(), e);
                // Depending on policy, you might re-throw or handle more gracefully
            }
        }
        return utilisateurRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public Utilisateur toggleUserStatus(Integer userId) {
        Utilisateur user = getUserById(userId);
        if (user != null) {
            user.setActif(user.getActif() == 1 ? 0 : 1);
            return utilisateurRepository.save(user);
        }
        return null;
    }

    @Override
    public List<Utilisateur> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllUsers();
        }
        return utilisateurRepository.findByNomContainingOrPrenomContainingOrMailContaining(query, query, query);
    }

    @Override
    public Utilisateur updateUserRole(Integer userId, Integer profilId) {
        Utilisateur user = getUserById(userId);
        if (user != null) {
            user.setProfilId(profilId);
            return utilisateurRepository.save(user);
        }
        return null;
    }

    @Override
    public List<String> findDistinctServices() {
        log.info("↘️ [Service] Appel du Repository (findDistinctServices())...");
        List<String> services = utilisateurRepository.findDistinctServices();
        log.info("✅ [Service] Le Repository a retourné {} services distincts.", (services != null ? services.size() : "NULL"));
        return services;
    }
}
