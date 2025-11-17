package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.entity.Services;
import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.ProfilGestion;
import mg.md2i.gedi.gestionmetier.ServiceGestion;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import mg.md2i.gedi.session.UserSessionData;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import java.util.*;

public class UserViewModel {

    private List<Utilisateur> users = new ArrayList<>();
    private String searchQuery = "";

    private Utilisateur currentUtilisateur = new Utilisateur();
    private String passwordInput;

    private List<Services> availableServices = new ArrayList<>();
    private Services selectedService;

    private List<Profil> availableProfils = new ArrayList<>();
    private Profil selectedProfil;

    private boolean editingMode = false;

    @Init
    public void init() {

        String mode = (String) Executions.getCurrent().getAttribute("userPageMode");
        Utilisateur userToEdit = UserSessionData.getUserToEdit(); // FIX
        UserSessionData.clear();

        loadUsersInternal();

        availableServices = ServiceGestion.findAllServices();

        if ("edit".equals(mode) && userToEdit != null) {
            loadUserEdit(userToEdit);
        } else if ("new".equals(mode)) {
            prepareNewUser();
        }
    }

    private void loadUsersInternal() {
        users = UtilisateurGestion.findAllUtilisateurs();
    }

    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void loadUsers() {
        loadUsersInternal();
    }

    @Command
    @NotifyChange({"users", "usersCount", "activeUsersCount"})
    public void searchUsers() {
        if (isBlank(searchQuery)) {
            loadUsersInternal();
        } else {
            users = UtilisateurGestion.searchUsers(searchQuery);
        }
    }

    private void prepareNewUser() {
        editingMode = false;
        currentUtilisateur = new Utilisateur();
        currentUtilisateur.setActif(1);

        if (!availableServices.isEmpty()) {
            selectedService = availableServices.get(0);
            availableProfils = ProfilGestion.findProfilsByServiceId(selectedService.getServiceId());
            if (!availableProfils.isEmpty()) selectedProfil = availableProfils.get(0);
        }
    }

    private void loadUserEdit(Utilisateur user) {

        editingMode = true;
        currentUtilisateur = UtilisateurGestion.findById(user.getUtilisateurId());
        passwordInput = null;

        for (Services s : availableServices) {
            if (s.getLibelle().equals(currentUtilisateur.getService())) {
                selectedService = s;
                break;
            }
        }

        if (selectedService != null) {
            availableProfils = ProfilGestion.findProfilsByServiceId(selectedService.getServiceId());

            for (Profil p : availableProfils) {
                if (p.getProfilId().equals(currentUtilisateur.getProfilId())) {
                    selectedProfil = p;
                    break;
                }
            }
        }
    }

    @Command
    public void openEditUser(@BindingParam("user") Utilisateur user) {

        UserSessionData.setUserToEdit(user);
        Executions.getCurrent().setAttribute("userPageMode", "edit");

        Map<String,Object> params = new HashMap<>();
        params.put("view", "/admin/views/utilisateurs/edit.zul");
        params.put("section", "Utilisateurs");
        params.put("page", "Modifier");

        BindUtils.postGlobalCommand(null, null, "navigateToAdmin", params);
    }

    @Command
    @NotifyChange({"users","usersCount","activeUsersCount"})
    public void deleteUser(@BindingParam("userId") Integer userId) {
        Messagebox.show("Supprimer cet utilisateur ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        UtilisateurGestion.deleteUtilisateurById(userId);
                        loadUsersInternal();
                    }
                });
    }

    @Command
    @NotifyChange({"availableProfils","selectedProfil"})
    public void onServiceChange() {
        if (selectedService != null) {
            availableProfils = ProfilGestion.findProfilsByServiceId(selectedService.getServiceId());
            selectedProfil = availableProfils.isEmpty() ? null : availableProfils.get(0);
        }
    }

    @Command
    public void saveUser() {

        if (!validateForm()) return;

        currentUtilisateur.setService(selectedService.getLibelle());
        currentUtilisateur.setProfilId(selectedProfil.getProfilId());

        if (!isBlank(passwordInput)) {
            currentUtilisateur.setPassword(passwordInput);
        }

        UtilisateurGestion.save(currentUtilisateur);

        Map<String,Object> params = new HashMap<>();
        params.put("view", "/admin/views/utilisateurs/list.zul");
        params.put("section", "Utilisateurs");
        params.put("page", "Liste");

        BindUtils.postGlobalCommand(null,null,"navigateToAdmin",params);

        loadUsersInternal();
    }

    private boolean validateForm() {
        if (isBlank(currentUtilisateur.getNom())) { Messagebox.show("Nom requis."); return false; }
        if (isBlank(currentUtilisateur.getPrenom())) { Messagebox.show("Prénom requis."); return false; }
        if (isBlank(currentUtilisateur.getLogin())) { Messagebox.show("Login requis."); return false; }
        if (!editingMode && isBlank(passwordInput)) { Messagebox.show("Mot de passe requis."); return false; }
        if (selectedService == null) { Messagebox.show("Service requis."); return false; }
        if (selectedProfil == null) { Messagebox.show("Profil requis."); return false; }
        return true;
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    public List<Utilisateur> getUsers() { return users; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { searchQuery = q; }
    public int getUsersCount() { return users.size(); }
    public long getActiveUsersCount() {
        return users.stream().filter(u -> u.getActif() == 1).count();
    }

    public Utilisateur getCurrentUtilisateur() { return currentUtilisateur; }
    public String getPasswordInput() { return passwordInput; }
    public void setPasswordInput(String p) { passwordInput = p; }

    public List<Services> getAvailableServices() { return availableServices; }
    public Services getSelectedService() { return selectedService; }
    public void setSelectedService(Services s) { selectedService = s; }

    public List<Profil> getAvailableProfils() { return availableProfils; }
    public Profil getSelectedProfil() { return selectedProfil; }
    public void setSelectedProfil(Profil p) { selectedProfil = p; }

    public boolean isEditingMode() { return editingMode; }

    public String getInitials(Utilisateur u) {
        String p = u.getPrenom() != null && !u.getPrenom().isEmpty() ? u.getPrenom().substring(0,1) : "";
        String n = u.getNom() != null && !u.getNom().isEmpty() ? u.getNom().substring(0,1) : "";
        return (p+n).toUpperCase();
    }

    public String getNomComplet(Utilisateur u) {
        return (u.getPrenom() + " " + u.getNom()).trim();
    }
    public String getProfilName(Integer profilId) {
        if (profilId == null) return "N/A";
        Profil p = ProfilGestion.findById(profilId);
        return p != null ? p.getLibelle() : "N/A";
    }

}
