package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.entity.Services;
import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.ProfilGestion;
import mg.md2i.gedi.gestionmetier.ServiceGestion;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFormViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(UserFormViewModel.class);

    private Utilisateur currentUtilisateur;
    private String userFormPassword;

    private List<Services> availableServices = new ArrayList<>();
    private Services selectedService;

    private List<Profil> availableProfils = new ArrayList<>();
    private Profil selectedProfil;

    private boolean isNewUserMode;

    @Init
    public void init(@ExecutionArgParam("userToEdit") Utilisateur userToEdit) {

        availableServices = ServiceGestion.findAllServices();

        if (userToEdit != null && userToEdit.getUtilisateurId() != null) {
            currentUtilisateur = UtilisateurGestion.findById(userToEdit.getUtilisateurId());
            if (currentUtilisateur == null) {
                currentUtilisateur = new Utilisateur();
                currentUtilisateur.setActif(1);
                currentUtilisateur.setNumero(0.0);
                Messagebox.show("Utilisateur introuvable. Création d’un nouveau.", "Information", Messagebox.OK, Messagebox.INFORMATION);
                isNewUserMode = true;
            } else {
                isNewUserMode = false;
            }

            if (currentUtilisateur.getService() != null) {
                selectedService = availableServices.stream()
                        .filter(s -> s.getLibelle().equals(currentUtilisateur.getService()))
                        .findFirst().orElse(null);
            }

            if (selectedService != null) {
                availableProfils = ProfilGestion.findProfilsByServiceId(selectedService.getServiceId());
                if (currentUtilisateur.getProfilId() != null) {
                    selectedProfil = availableProfils.stream()
                            .filter(p -> p.getProfilId().equals(currentUtilisateur.getProfilId()))
                            .findFirst().orElse(null);
                }
            }

            userFormPassword = null;

        } else {
            currentUtilisateur = new Utilisateur();
            currentUtilisateur.setActif(1);
            currentUtilisateur.setNumero(0.0);
            isNewUserMode = true;

            if (!availableServices.isEmpty()) {
                selectedService = availableServices.get(0);
                availableProfils = ProfilGestion.findProfilsByServiceId(selectedService.getServiceId());
                if (!availableProfils.isEmpty()) {
                    selectedProfil = availableProfils.get(0);
                }
            }
        }
    }

    @Command
    @NotifyChange({"availableProfils", "selectedProfil"})
    public void onServiceChange() {
        if (selectedService != null) {
            availableProfils = ProfilGestion.findProfilsByServiceId(selectedService.getServiceId());
        } else {
            availableProfils = new ArrayList<>();
        }
        selectedProfil = availableProfils.isEmpty() ? null : availableProfils.get(0);
    }

    @Command
    public void saveUserForm(@ContextParam(ContextType.VIEW) Component view) {

        if (!validateUserForm()) {
            return;
        }

        if (selectedService != null) {
            currentUtilisateur.setService(selectedService.getLibelle());
        }

        if (selectedProfil != null) {
            currentUtilisateur.setProfilId(selectedProfil.getProfilId());
        }

        if (userFormPassword != null && !userFormPassword.isEmpty()) {
            currentUtilisateur.setPassword(userFormPassword);
        }

        try {
            UtilisateurGestion.save(currentUtilisateur);
            Messagebox.show("Utilisateur sauvegardé.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
            BindUtils.postGlobalCommand(null, null, "refreshUserList", null);

            if (view instanceof Window) {
                ((Window) view).detach();
            }

        } catch (Exception e) {
            LOG.error("Erreur sauvegarde user", e);
            Messagebox.show("Erreur: " + e.getMessage(), "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void cancelUserForm(@ContextParam(ContextType.VIEW) Component view) {
        Messagebox.show(isNewUserMode ? "Annuler la création ?" : "Annuler les modifications ?",
                "Confirmation", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, ev -> {
                    if (Messagebox.ON_YES.equals(ev.getName())) {
                        if (view instanceof Window) ((Window) view).detach();
                    }
                });
    }

    private boolean validateUserForm() {
        if (isBlank(currentUtilisateur.getNom())) {
            Messagebox.show("Nom requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (isBlank(currentUtilisateur.getPrenom())) {
            Messagebox.show("Prénom requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (isBlank(currentUtilisateur.getMail()) || !currentUtilisateur.getMail().matches(".+@.+\\..+")) {
            Messagebox.show("Email invalide.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (isBlank(currentUtilisateur.getLogin())) {
            Messagebox.show("Login requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (isNewUserMode && (userFormPassword == null || userFormPassword.isEmpty())) {
            Messagebox.show("Mot de passe requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (selectedService == null) {
            Messagebox.show("Service requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        if (selectedProfil == null) {
            Messagebox.show("Profil requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return false;
        }
        return true;
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public Utilisateur getCurrentUtilisateur() { return currentUtilisateur; }
    public String getUserFormPassword() { return userFormPassword; }
    public void setUserFormPassword(String userFormPassword) { this.userFormPassword = userFormPassword; }
    public List<Services> getAvailableServices() { return availableServices; }
    public Services getSelectedService() { return selectedService; }
    public void setSelectedService(Services selectedService) { this.selectedService = selectedService; }
    public List<Profil> getAvailableProfils() { return availableProfils; }
    public Profil getSelectedProfil() { return selectedProfil; }
    public void setSelectedProfil(Profil selectedProfil) { this.selectedProfil = selectedProfil; }
    public String getUserFormTitle() { return isNewUserMode ? "Nouvel utilisateur" : "Modifier l'utilisateur"; }
    public String getUserFormSubtitle() { return isNewUserMode ? "Création d’un compte" : "Mise à jour du compte"; }
    public String getSaveActionLabel() { return isNewUserMode ? "Créer" : "Mettre à jour"; }
    public String getUserFormPasswordPlaceholder() { return isNewUserMode ? "Mot de passe" : "Laisser vide pour ne pas modifier"; }
}
