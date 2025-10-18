//package mg.md2i.gedi.control.viewmodel;
//
//import mg.md2i.gedi.entity.Utilisateur;
//import mg.md2i.gedi.services.UserService;
//import org.zkoss.bind.annotation.Command;
//import org.zkoss.bind.annotation.Init;
//import org.zkoss.bind.annotation.NotifyChange;
//import org.zkoss.zk.ui.select.annotation.VariableResolver;
//import org.zkoss.zk.ui.select.annotation.WireVariable;
//import org.zkoss.zkplus.spring.DelegatingVariableResolver;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@VariableResolver(DelegatingVariableResolver.class)
//public class AdminViewModel {
//
//    @WireVariable
//    private UserService userService;
//
//    private List<Utilisateur> users = new ArrayList<>();
//    private String searchQuery;
//
//    @Init
//    public void init() {
//        loadUsers();
//    }
//
//    @Command
//    @NotifyChange("users")
//    public void loadUsers() {
//        try {
//            users = userService.getAllUsers();
//        } catch (Exception e) {
//            e.printStackTrace();
//            users = createSampleUsers();
//        }
//    }
//
//    private List<Utilisateur> createSampleUsers() {
//        List<Utilisateur> sampleUsers = new ArrayList<>();
//        
//        // Ajouter des utilisateurs de démonstration
//        for (int i = 1; i <= 12; i++) {
//            Utilisateur user = new Utilisateur();
//            user.setUtilisateurId(i);
//            user.setNom("Nom" + i);
//            user.setPrenom("Prénom" + i);
//            user.setMail("user" + i + "@gedi.com");
//            user.setProfilId((i % 3) + 1);
//            user.setFonction(getRoleLabel((i % 3) + 1));
//            user.setService("Service " + ((i % 4) + 1));
//            user.setActif(i % 5 != 0 ? 1 : 0); // 80% actifs
//            sampleUsers.add(user);
//        }
//        
//        return sampleUsers;
//    }
//
//    @Command
//    @NotifyChange("users")
//    public void toggleUserStatus(@org.zkoss.bind.annotation.BindingParam("userId") Integer userId) {
//        try {
//            userService.toggleUserStatus(userId);
//            loadUsers();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Command
//    @NotifyChange("users")
//    public void deleteUser(@org.zkoss.bind.annotation.BindingParam("userId") Integer userId) {
//        if (org.zkoss.zul.Messagebox.show("Êtes-vous sûr de vouloir supprimer cet utilisateur ?", 
//            "Confirmation", 
//            org.zkoss.zul.Messagebox.YES | org.zkoss.zul.Messagebox.NO, 
//            org.zkoss.zul.Messagebox.QUESTION) == org.zkoss.zul.Messagebox.YES) {
//            try {
//                userService.deleteUser(userId);
//                loadUsers();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Command
//    @NotifyChange("users")
//    public void updateUserRole(@org.zkoss.bind.annotation.BindingParam("userId") Integer userId, 
//                              @org.zkoss.bind.annotation.BindingParam("profilId") Integer profilId) {
//        try {
//            userService.updateUserRole(userId, profilId);
//            loadUsers();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Command
//    @NotifyChange("users")
//    public void searchUsers() {
//        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
//            try {
//                users = userService.searchUsers(searchQuery);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            loadUsers();
//        }
//    }
//
//    // Getters and Setters
//    public List<Utilisateur> getUsers() { return users; }
//    public String getSearchQuery() { return searchQuery; }
//    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
//
//    // Helper methods
//    public String getNomComplet(Utilisateur user) {
//        return (user.getPrenom() != null ? user.getPrenom() + " " : "") + user.getNom();
//    }
//
//    public String getInitials(Utilisateur user) {
//        if (user.getPrenom() != null && !user.getPrenom().isEmpty() && 
//            user.getNom() != null && !user.getNom().isEmpty()) {
//            return String.valueOf(user.getPrenom().charAt(0)) + user.getNom().charAt(0);
//        }
//        return "??";
//    }
//
//    public boolean isActif(Utilisateur user) {
//        return user.getActif() != null && user.getActif() == 1;
//    }
//
//    public String getRoleLabel(Integer profilId) {
//        if (profilId == null) return "Non défini";
//        switch (profilId) {
//            case 1: return "Administrateur";
//            case 2: return "Agent";
//            case 3: return "Lecteur";
//            default: return "Utilisateur";
//        }
//    }
//
//    public String getRoleClass(Integer profilId) {
//        if (profilId == null) return "role-lecteur";
//        switch (profilId) {
//            case 1: return "role-admin";
//            case 2: return "role-agent";
//            case 3: return "role-lecteur";
//            default: return "role-lecteur";
//        }
//    }
//    
//    public int getUsersCount() {
//        return users != null ? users.size() : 0;
//    }
//}