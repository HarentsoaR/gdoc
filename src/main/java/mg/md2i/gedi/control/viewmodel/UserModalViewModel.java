package mg.md2i.gedi.control.viewmodel;

import lombok.Getter;
import lombok.Setter;
import mg.md2i.gedi.entity.Utilisateur;
import mg.md2i.gedi.gestionmetier.UtilisateurGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.*;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class UserModalViewModel {
    
    private static final Logger log = LoggerFactory.getLogger(UserModalViewModel.class);

    private Window view;
    private Utilisateur editableUser;
    private String password;

    private Map<Integer, String> roles;
    private Integer selectedProfilId;

    @Init
    public void init(@ExecutionArgParam("userToEdit") Utilisateur user) {
        // ✅ CORRECTION : Créer un nouvel utilisateur si null
        if (user == null) {
            this.editableUser = new Utilisateur();
            log.info("➡️ Initialisation pour NOUVEL utilisateur");
        } else {
            this.editableUser = user;
            log.info("➡️ Modale initialisée pour l'utilisateur : {}", 
                    (user.getNom() != null ? user.getNom() : "MODIFICATION"));
        }

        // Initialiser les rôles
        roles = new LinkedHashMap<>();
        roles.put(1, "Administrateur");
        roles.put(2, "Agent");
        roles.put(3, "Lecteur");

        // Définir le rôle sélectionné
        if (editableUser.getProfilId() != null) {
            this.selectedProfilId = editableUser.getProfilId();
        } else {
            this.selectedProfilId = 3; // Rôle par défaut (Lecteur)
        }
        
        // Notifier le changement
        BindUtils.postNotifyChange(null, null, this, "editableUser");
        BindUtils.postNotifyChange(null, null, this, "selectedProfilId");
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        this.view = (Window) view;
    }

    @Command
    public void save() {
        try {
            // Validation avant sauvegarde
            if (!validateRequiredFields()) {
                return;
            }

            // Définir le profil
            editableUser.setProfilId(selectedProfilId);

            // Gérer le mot de passe
            if (password != null && !password.trim().isEmpty()) {
                editableUser.setPassword(password);
            }

            // Sauvegarder
            UtilisateurGestion.saveUtilisateur(editableUser);
            
            log.info("✅ Utilisateur sauvegardé : {}", editableUser.getNom());

            // Notifier le parent pour rafraîchir la liste
            BindUtils.postGlobalCommand(null, null, "refreshUserList", null);
            
            // Fermer la fenêtre
            closeModal();

        } catch (Exception e) {
            log.error("❌ Erreur lors de la sauvegarde de l'utilisateur", e);
            // Vous pouvez ajouter une notification d'erreur ici
        }
    }

    @Command
    public void closeModal() {
        if (view != null) {
            view.detach();
        }
    }

    // Validation des champs requis
    private boolean validateRequiredFields() {
        if (editableUser.getNom() == null || editableUser.getNom().trim().isEmpty()) {
            log.warn("❌ Le nom est requis");
            return false;
        }
        if (editableUser.getPrenom() == null || editableUser.getPrenom().trim().isEmpty()) {
            log.warn("❌ Le prénom est requis");
            return false;
        }
        if (editableUser.getMail() == null || editableUser.getMail().trim().isEmpty()) {
            log.warn("❌ L'email est requis");
            return false;
        }
        if (editableUser.getLogin() == null || editableUser.getLogin().trim().isEmpty()) {
            log.warn("❌ Le login est requis");
            return false;
        }
        
        // Pour un nouvel utilisateur, le mot de passe est requis
        if ((editableUser.getUtilisateurId() == null) && 
            (password == null || password.trim().isEmpty())) {
            log.warn("❌ Le mot de passe est requis pour un nouvel utilisateur");
            return false;
        }
        
        return true;
    }

    // --- Getters pour l'affichage ---
    public String getModalTitle() {
        return (editableUser != null && editableUser.getUtilisateurId() != null) 
                ? "✏️ Modifier l'Utilisateur" 
                : "➕ Nouvel Utilisateur";
    }

    public String getPasswordPlaceholder() {
        return (editableUser != null && editableUser.getUtilisateurId() != null) 
                ? "Laissez vide pour ne pas changer" 
                : "Créer un mot de passe";
    }

    // --- VALIDATEUR UNIQUE POUR TOUT LE FORMULAIRE ---
    public Validator getFormValidator() {
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
                        validatePasswordLogic(ctx);
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
                    addInvalidMessage(ctx, "Le format de l'email est invalide.");
                }
            }
            
            private void validatePasswordLogic(ValidationContext ctx) {
                boolean isNewUser = (editableUser == null || editableUser.getUtilisateurId() == null);
                if (isNewUser && (password == null || password.trim().isEmpty())) {
                    addInvalidMessage(ctx, "Le mot de passe est requis pour un nouvel utilisateur.");
                }
            }
        };
    }
}