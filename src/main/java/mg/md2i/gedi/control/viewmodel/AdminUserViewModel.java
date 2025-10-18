package mg.md2i.gedi.control.viewmodel;

import lombok.Getter;
import lombok.Setter;
import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils; // ✅ IMPORT IMPORTANT
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.*;

public class AdminUserViewModel {

    private static final Logger log = LoggerFactory.getLogger(AdminUserViewModel.class);

    @Getter @Setter
    private List<Utilisateur> users = new ArrayList<>();

    @Getter @Setter
    private String searchQuery = "";

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        log.info("--- Début de l'initialisation de AdminUserViewModel ---");
        try {
            // 1. Les données sont chargées en mémoire dans la liste 'users'
            loadUsers(); 

            // 2. ✅ LA CORRECTION : On notifie manuellement l'interface graphique que les données ont changé.
            // Ceci force la <listbox> à se redessiner avec les nouvelles données.
            BindUtils.postNotifyChange(null, null, this, "users");
            BindUtils.postNotifyChange(null, null, this, "usersCount");
            BindUtils.postNotifyChange(null, null, this, "activeUsersCount");

            log.info("✅ Initialisation terminée. Notification envoyée à l'interface graphique.");

        } catch (Exception e) {
            log.error("❌ Erreur critique lors du chargement initial des utilisateurs", e);
            Messagebox.show("Erreur interne lors du chargement des utilisateurs. Consultez les logs.", "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    // Cette méthode a déjà le @NotifyChange, ce qui est parfait pour les appels depuis les boutons (clics)
    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void loadUsers() {
        log.info("➡️ [ViewModel] Appel de UtilisateurGestion.findAllUtilisateurs()...");
        List<Utilisateur> fetchedUsers = UtilisateurGestion.findAllUtilisateurs();

        if (fetchedUsers == null) {
            log.warn("️⚠️ [ViewModel] UtilisateurGestion a retourné NULL. Initialisation avec une liste vide.");
            this.users = new ArrayList<>();
        } else {
            log.info("✅ [ViewModel] UtilisateurGestion a retourné {} utilisateurs.", fetchedUsers.size());
            this.users = fetchedUsers;
        }
    }
    
    // ... Le reste du code est parfait et ne change pas ...
    
    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void searchUsers() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadUsers();
        } else {
            users = UtilisateurGestion.searchUsers(searchQuery);
        }
    }

    @Command
    public void openCreateModal() {
        openModal(new Utilisateur());
    }

    @Command
    public void openEditModal(@BindingParam("user") Utilisateur user) {
        Utilisateur fullUser = UtilisateurGestion.findById(user.getUtilisateurId());
        openModal(fullUser != null ? fullUser : user);
    }

    private void openModal(Utilisateur user) {
        Map<String, Object> args = new HashMap<>();
        args.put("userToEdit", user);
        Window win = (Window) Executions.createComponents("/admin/utilisateurs/edit.zul", null, args);
        win.doModal();
    }

    @GlobalCommand
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void refreshUserList() {
        loadUsers();
    }

    @Command
    public void deleteUser(@BindingParam("userId") Integer userId) {
        Messagebox.show("Supprimer cet utilisateur ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if (Messagebox.ON_YES.equals(event.getName())) {
                        try {
                            UtilisateurGestion.deleteUtilisateurById(userId);
                            refreshUserList();
                            Messagebox.show("Utilisateur supprimé avec succès", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                        } catch (Exception e) {
                            log.error("Erreur de suppression", e);
                            Messagebox.show("Échec de la suppression.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                        }
                    }
                });
    }

    public int getUsersCount() { return users != null ? users.size() : 0; }
    public long getActiveUsersCount() {
        if (users == null) return 0;
        return users.stream().filter(u -> u.getActif() != null && u.getActif() == 1).count();
    }
    public String getNomComplet(Utilisateur u) {
        if (u == null) return "";
        return (u.getPrenom() != null ? u.getPrenom() : "") + " " + (u.getNom() != null ? u.getNom() : "");
    }
    public String getInitials(Utilisateur u) {
        if (u == null) return "";
        String p = (u.getPrenom() != null && !u.getPrenom().isEmpty()) ? u.getPrenom().substring(0, 1) : "";
        String n = (u.getNom() != null && !u.getNom().isEmpty()) ? u.getNom().substring(0, 1) : "";
        return (p + n).toUpperCase();
    }
    public boolean isActif(Utilisateur u) { return u != null && u.getActif() != null && u.getActif() == 1; }
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