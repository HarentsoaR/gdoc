package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;

public class AdminViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(AdminViewModel.class);
    private static final String USER_LIST_VIEW = "/admin/views/utilisateurs/list.zul";
    private static final String USER_NEW_VIEW = "/admin/views/utilisateurs/new.zul";

    private String currentView = "/admin/views/dashboard.zul";
    private String currentPath = "Administration \u203A Tableau de bord";
    private boolean sidebarCollapsed = false;
    private boolean usersMenuExpanded = true;
    private Map<String, Object> navigationArgs = new HashMap<>();

    private List<Utilisateur> users = new ArrayList<>();
    private String searchQuery = "";

    private Utilisateur userForm = new Utilisateur();
    private String userFormPassword = "";
    private Integer userFormRoleId = 3;
    private boolean userFormEditing = false;
    private final Map<Integer, String> roleOptions = new LinkedHashMap<>();

    @Init
    public void init() {
        navigationArgs = new HashMap<>();
        initRoleOptions();
        resetUserFormState();
//        loadUsersInternal();
//        notifyUserListChanges();
    }
    
    @AfterCompose
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void afterCompose() {
        loadUsersInternal();
    }

    public String getCurrentView() {
        return currentView;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public boolean isSidebarCollapsed() {
        return sidebarCollapsed;
    }

    public boolean isUsersMenuExpanded() {
        return usersMenuExpanded;
    }

    public Map<String, Object> getNavigationArgs() {
        return navigationArgs;
    }

    public List<Utilisateur> getUsers() {
        return users;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Utilisateur getUserForm() {
        return userForm;
    }

    public String getUserFormPassword() {
        return userFormPassword;
    }

    public void setUserFormPassword(String userFormPassword) {
        this.userFormPassword = userFormPassword;
    }

    public Integer getUserFormRoleId() {
        return userFormRoleId;
    }

    public void setUserFormRoleId(Integer userFormRoleId) {
        this.userFormRoleId = userFormRoleId;
    }

    public Map<Integer, String> getRoleOptions() {
        return roleOptions;
    }

    public boolean isUserFormEditing() {
        return userFormEditing;
    }

    public String getUserFormTitle() {
        return userFormEditing ? "Modifier un utilisateur" : "Nouvel utilisateur";
    }

    public String getUserFormSubtitle() {
        return userFormEditing
            ? "Mettez à jour les informations du compte sélectionné"
            : "Créez un nouveau compte administrateur ou agent";
    }

    public String getUserFormPasswordPlaceholder() {
        return userFormEditing ? "Laissez vide pour ne pas modifier le mot de passe"
                               : "Créer un mot de passe";
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

    @Command
    @NotifyChange("sidebarCollapsed")
    public void toggleSidebar() {
        sidebarCollapsed = !sidebarCollapsed;
    }

    @Command
    @NotifyChange("usersMenuExpanded")
    public void toggleUsersMenu() {
        usersMenuExpanded = !usersMenuExpanded;
    }

    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void loadUsers() {
        loadUsersInternal();
    }

    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void searchUsers() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadUsersInternal();
        } else {
            List<Utilisateur> results = UtilisateurGestion.searchUsers(searchQuery);
            users = results != null ? results : new ArrayList<>();
        }
    }

    @Command
    public void editUser(@BindingParam("user") Utilisateur user) {
        if (user == null) {
            return;
        }
        Utilisateur fullUser = UtilisateurGestion.findById(user.getUtilisateurId());
        userForm = fullUser != null ? fullUser : user;
        if (userForm.getActif() == null) userForm.setActif(1);
        if (userForm.getNumero() == null) userForm.setNumero(0d);
        userFormPassword = "";
        userFormRoleId = userForm.getProfilId() != null ? userForm.getProfilId() : 3;
        userFormEditing = userForm.getUtilisateurId() != null;
        notifyUserFormState();
        setNavigationState(USER_NEW_VIEW, "Modifier un utilisateur", "Utilisateurs", "Edition", null);
        triggerNavigationChange();
    }

    @Command
    public void saveUserForm() {
        if (!validateUserForm()) {
            return;
        }
        try {
            userForm.setProfilId(userFormRoleId != null ? userFormRoleId : 3);
            if (userForm.getActif() == null) userForm.setActif(1);
            if (userForm.getNumero() == null) userForm.setNumero(0d);
            if (userFormPassword != null && !userFormPassword.trim().isEmpty()) {
                userForm.setPassword(userFormPassword.trim());
            }

            UtilisateurGestion.saveUtilisateur(userForm);
            loadUsersInternal();
            notifyUserListChanges();

            resetUserFormState();
            setNavigationState(USER_LIST_VIEW, "Liste des utilisateurs", "Utilisateurs", "Liste", null);
            triggerNavigationChange();

            Messagebox.show("Utilisateur enregistré avec succès.", "Succès",
                    Messagebox.OK, Messagebox.INFORMATION);
        } catch (Exception e) {
            LOG.error("Erreur lors de la sauvegarde de l'utilisateur", e);
            Messagebox.show("Impossible d'enregistrer l'utilisateur.", "Erreur",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void cancelUserForm() {
        resetUserFormState();
        setNavigationState(USER_LIST_VIEW, "Liste des utilisateurs", "Utilisateurs", "Liste", null);
        triggerNavigationChange();
    }

    @GlobalCommand
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void refreshUserList() {
        loadUsersInternal();
    }

    @Command
    public void deleteUser(@BindingParam("userId") Integer userId) {
        if (userId == null) return;
        Messagebox.show("Supprimer cet utilisateur ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if (Messagebox.ON_YES.equals(event.getName())) {
                        try {
                            UtilisateurGestion.deleteUtilisateurById(userId);
                            loadUsersInternal();
                            notifyUserListChanges();
                            Messagebox.show("Utilisateur supprimé avec succès", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                        } catch (Exception e) {
                            LOG.error("Erreur de suppression", e);
                            Messagebox.show("Échec de la suppression.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                        }
                    }
                });
    }

    @Command
    @NotifyChange({"currentView", "currentPath", "navigationArgs"})
    public void navigateTo(@BindingParam("view") String view,
                           @BindingParam("label") String label,
                           @BindingParam("section") String section,
                           @BindingParam("page") String page,
                           @BindingParam("args") Map<String, Object> args) {
        if (view == null || view.trim().isEmpty()) {
            return;
        }
        setNavigationState(view, label, section, page, args);
    }

    @GlobalCommand
    @NotifyChange({"currentView", "currentPath", "navigationArgs"})
    public void navigateToAdmin(@BindingParam("view") String view,
                                @BindingParam("label") String label,
                                @BindingParam("section") String section,
                                @BindingParam("page") String page,
                                @BindingParam("args") Map<String, Object> args) {
        navigateTo(view, label, section, page, args);
    }

    @Command
    public void goHome() {
        Executions.sendRedirect("/home");
    }

    @Command
    public void doLogout() {
        Session session = Executions.getCurrent().getSession();
        session.removeAttribute("authenticated");
        session.removeAttribute("username");
        session.removeAttribute("jwtToken");
        Executions.sendRedirect("/");
    }

    public Validator getUserFormValidator() {
        return new AbstractValidator() {
            @Override
            public void validate(ValidationContext ctx) {
                String fieldName = (String) ctx.getValidatorArg("field");
                if (fieldName == null) {
                    fieldName = ctx.getProperty().getProperty();
                }

                switch (fieldName) {
                    case "nom":
                    case "prenom":
                    case "login":
                        validateRequired(ctx, (String) ctx.getProperty().getValue());
                        break;
                    case "mail":
                        validateEmail(ctx, (String) ctx.getProperty().getValue());
                        break;
                    case "password":
                        validatePassword(ctx);
                        break;
                }
            }

            private void validateRequired(ValidationContext ctx, String value) {
                if (value == null || value.trim().isEmpty()) {
                    addInvalidMessage(ctx, "Ce champ est requis.");
                }
            }

            private void validateEmail(ValidationContext ctx, String email) {
                if (email == null || email.trim().isEmpty()) {
                    addInvalidMessage(ctx, "Ce champ est requis.");
                } else if (!email.matches(".+@.+\\..+")) {
                    addInvalidMessage(ctx, "Format d'email invalide.");
                }
            }

            private void validatePassword(ValidationContext ctx) {
                boolean isNew = !userFormEditing || userForm.getUtilisateurId() == null;
                if (isNew && (userFormPassword == null || userFormPassword.trim().isEmpty())) {
                    addInvalidMessage(ctx, "Le mot de passe est requis pour un nouvel utilisateur.");
                }
            }
        };
    }

    private void initRoleOptions() {
        if (!roleOptions.isEmpty()) return;
        roleOptions.put(1, "Administrateur");
        roleOptions.put(2, "Agent");
        roleOptions.put(3, "Lecteur");
    }

    private void setNavigationState(String view, String label, String section, String page, Map<String, Object> args) {
        currentView = view;
        currentPath = buildPath(label, section, page);
        navigationArgs = args != null ? new HashMap<>(args) : new HashMap<>();
        if (USER_NEW_VIEW.equals(view) && !userFormEditing) {
            resetUserFormState();
        }
    }

    private void triggerNavigationChange() {
        BindUtils.postNotifyChange(null, null, this, "currentView");
        BindUtils.postNotifyChange(null, null, this, "currentPath");
        BindUtils.postNotifyChange(null, null, this, "navigationArgs");
    }

    private void resetUserFormState() {
        userForm = new Utilisateur();
        userForm.setActif(1);
        userForm.setNumero(0d);
        userFormPassword = "";
        userFormRoleId = 3;
        userFormEditing = false;
        notifyUserFormState();
    }

    private void notifyUserFormState() {
        BindUtils.postNotifyChange(null, null, this, "userForm");
        BindUtils.postNotifyChange(null, null, this, "userFormPassword");
        BindUtils.postNotifyChange(null, null, this, "userFormRoleId");
        BindUtils.postNotifyChange(null, null, this, "userFormTitle");
        BindUtils.postNotifyChange(null, null, this, "userFormSubtitle");
        BindUtils.postNotifyChange(null, null, this, "userFormPasswordPlaceholder");
        BindUtils.postNotifyChange(null, null, this, "userFormEditing");
    }

    private void loadUsersInternal() {
        List<Utilisateur> fetchedUsers = UtilisateurGestion.findAllUtilisateurs();
        users = fetchedUsers != null ? fetchedUsers : new ArrayList<>();
    }

    private void notifyUserListChanges() {
        BindUtils.postNotifyChange(null, null, this, "users");
        BindUtils.postNotifyChange(null, null, this, "usersCount");
        BindUtils.postNotifyChange(null, null, this, "activeUsersCount");
    }

    private boolean validateUserForm() {
        if (isBlank(userForm.getNom())) {
            Messagebox.show("Le nom est requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (isBlank(userForm.getPrenom())) {
            Messagebox.show("Le prénom est requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (isBlank(userForm.getMail()) || !userForm.getMail().matches(".+@.+\\..+")) {
            Messagebox.show("L'email est invalide.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (isBlank(userForm.getLogin())) {
            Messagebox.show("Le login est requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (!userFormEditing && (userFormPassword == null || userFormPassword.trim().isEmpty())) {
            Messagebox.show("Le mot de passe est requis pour un nouvel utilisateur.", "Validation",
                    Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String buildPath(String label, String section, String page) {
        if (section != null && !section.trim().isEmpty()
            && page != null && !page.trim().isEmpty()) {
            return section + " \u203A " + page;
        }
        if (label != null && !label.trim().isEmpty()) {
            return label;
        }
        return "Administration";
    }
}
