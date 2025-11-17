package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(UserListViewModel.class);

    private List<Utilisateur> users = new ArrayList<>();
    private String searchQuery = "";

    @Init
    public void init() {
        loadUsersInternal();
    }

    @GlobalCommand
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void refreshUserList() {
        loadUsersInternal();
    }

    private void loadUsersInternal() {
        List<Utilisateur> fetched = UtilisateurGestion.findAllUtilisateurs();
        users = fetched != null ? fetched : new ArrayList<>();
    }

    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void searchUsers() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadUsersInternal();
        } else {
            users = UtilisateurGestion.searchUsers(searchQuery);
        }
    }

    @Command
    public void deleteUser(@BindingParam("userId") Integer userId) {
        Messagebox.show("Supprimer cet utilisateur ?", "Confirmation", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION, evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        UtilisateurGestion.deleteUtilisateurById(userId);
                        loadUsersInternal();
                        BindUtils.postNotifyChange(null, null, this, "users");
                        BindUtils.postNotifyChange(null, null, this, "usersCount");
                        BindUtils.postNotifyChange(null, null, this, "activeUsersCount");
                    }
                });
    }

    @Command
    public void openEditUserModal(@BindingParam("user") Utilisateur user) {
        Map<String, Object> args = new HashMap<>();
        args.put("userToEdit", user);
        Window window = (Window) Executions.createComponents("/admin/views/utilisateurs/edit.zul", null, args);
        window.doModal();
    }

    public List<Utilisateur> getUsers() { return users; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public int getUsersCount() { return users.size(); }
    public long getActiveUsersCount() { return users.stream().filter(u -> u.getActif() == 1).count(); }

    public String getNomComplet(Utilisateur u) {
        return (u.getPrenom() != null ? u.getPrenom() : "") + " " + (u.getNom() != null ? u.getNom() : "");
    }

    public String getInitials(Utilisateur u) {
        String p = u.getPrenom() != null ? u.getPrenom().substring(0, 1) : "";
        String n = u.getNom() != null ? u.getNom().substring(0, 1) : "";
        return (p + n).toUpperCase();
    }

    public boolean isActif(Utilisateur u) {
        return u.getActif() != null && u.getActif() == 1;
    }
}
