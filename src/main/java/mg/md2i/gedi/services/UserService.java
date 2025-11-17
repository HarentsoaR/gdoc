package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Utilisateur;
import java.util.List;

public interface UserService {
    List<Utilisateur> getAllUsers();
    Utilisateur getUserById(Integer id);
    Utilisateur saveUser(Utilisateur user);
    void deleteUser(Integer id);
    Utilisateur toggleUserStatus(Integer userId);
    List<Utilisateur> searchUsers(String query);
    Utilisateur updateUserRole(Integer userId, Integer profilId);
    List<String> findDistinctServices();
}