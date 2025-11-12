package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(UserListViewModel.class);

    private List<Utilisateur> users;
    private String searchQuery = "";

    @Init
    public void init() {
        loadUsers();
    }

    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void loadUsers() {
        List<Utilisateur> fetchedUsers = UtilisateurGestion.findAllUtilisateurs();
        this.users = fetchedUsers != null ? fetchedUsers : new ArrayList<>();
        LOG.info("Loaded {} users into the ViewModel.", this.users.size());
    }

    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void searchUsers() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadUsers();
        } else {
            List<Utilisateur> results = UtilisateurGestion.searchUsers(searchQuery);
            this.users = results != null ? results : new ArrayList<>();
        }
    }

    @Command
    public void editUser(@BindingParam("user") Utilisateur user) {
        if (user == null) {
            return;
        }
        Map<String, Object> args = new HashMap<>();
        args.put("userToEdit", user);
        
        BindUtils.postGlobalCommand(null, null, "navigateToAdmin", new HashMap<String, Object>() {{
            put("view", "/admin/views/utilisateurs/new.zul");
            put("section", "Utilisateurs");
            put("page", "Edition");
            put("args", args);
        }});
    }

    @Command
    public void deleteUser(@BindingParam("userId") Integer userId) {
        if (userId == null) return;
        Messagebox.show("Êtes-vous sûr de vouloir supprimer cet utilisateur ?", "Confirmation de suppression",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if (Messagebox.ON_YES.equals(event.getName())) {
                        try {
                            UtilisateurGestion.deleteUtilisateurById(userId);
                            loadUsers();
                            Messagebox.show("Utilisateur supprimé avec succès.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                        } catch (Exception e) {
                            LOG.error("Erreur lors de la suppression de l'utilisateur.", e);
                            Messagebox.show("Une erreur est survenue lors de la suppression.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                        }
                    }
                });
    }

    public List<Utilisateur> getUsers() {
        return users;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
    
    public int getUsersCount() {
        return users != null ? users.size() : 0;
    }

    public long getActiveUsersCount() {
        if (users == null) return 0;
        return users.stream().filter(this::isActif).count();
    }

    public String getNomComplet(Utilisateur u) {
        if (u == null) return "";
        String prenom = u.getPrenom() != null ? u.getPrenom() : "";
        String nom = u.getNom() != null ? u.getNom() : "";
        return (prenom + " " + nom).trim();
    }

    public String getInitials(Utilisateur u) {
        if (u == null) return "";
        String p = (u.getPrenom() != null && !u.getPrenom().isEmpty()) ? u.getPrenom().substring(0, 1) : "";
        String n = (u.getNom() != null && !u.getNom().isEmpty()) ? u.getNom().substring(0, 1) : "";
        return (p + n).toUpperCase();
    }

    public boolean isActif(Utilisateur u) {
        return u != null && u.getActif() != null && u.getActif() == 1;
    }

    public String getRoleLabel(Integer profilId) {
        if (profilId == null) return "Non défini";
        switch (profilId) {
            case 1: return "Administrateur";
            case 2: return "Agent";
            case 3: return "Lecteur";
            default: return "Inconnu";
        }
    }

    public String getRoleClass(Integer profilId) {
        if (profilId == null) return "role-lecteur";
        switch (profilId) {
            case 1: return "role-admin";
            case 2: return "role-agent";
            case 3: return "role-lecteur";
            default: return "role-lecteur";
        }
    }
}