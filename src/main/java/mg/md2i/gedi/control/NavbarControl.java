package mg.md2i.gedi.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;

/**
 * Contrôleur pour la navbar d'administration
 */
public class NavbarControl extends SelectorComposer<Component> {

    private static final Logger logger = LoggerFactory.getLogger(NavbarControl.class);

    @Wire private Menuitem logoutMenuitem;
    @Wire private Menuitem profilMenuItem;
    @Wire private Menu userMenu;
    @Wire private Label userConnecterLbl;
    @Wire private Label profilUserConnecterLbl;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        initializeUserInfo();
    }

    /**
     * Initialise les informations utilisateur dans la navbar
     */
    private void initializeUserInfo() {
        try {
            // Récupérer l'utilisateur depuis la session
            Object user = Executions.getCurrent().getSession().getAttribute("user");
            
            if (user != null) {
                // Adapter selon votre objet utilisateur
                if (user instanceof mg.md2i.gedi.entity.Utilisateur) {
                    mg.md2i.gedi.entity.Utilisateur utilisateur = (mg.md2i.gedi.entity.Utilisateur) user;
                    
                    if (userConnecterLbl != null) {
                        userConnecterLbl.setValue(utilisateur.getPrenom() + " " + utilisateur.getNom());
                    }
                    
                    if (profilUserConnecterLbl != null) {
                        profilUserConnecterLbl.setValue(getRoleName(utilisateur.getProfilId()));
                    }
                    
                    if (userMenu != null) {
                        userMenu.setLabel("Bonjour, " + utilisateur.getPrenom());
                    }
                }
            } else {
                // Valeurs par défaut pour le développement
                if (userConnecterLbl != null) {
                    userConnecterLbl.setValue("Administrateur");
                }
                if (profilUserConnecterLbl != null) {
                    profilUserConnecterLbl.setValue("Super Admin");
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors de l'initialisation des infos utilisateur", e);
        }
    }

    /**
     * Convertit l'ID du profil en nom de rôle
     */
    private String getRoleName(Integer profilId) {
        if (profilId == null) return "Utilisateur";
        
        switch (profilId) {
            case 1: return "Administrateur";
            case 2: return "Agent";
            case 3: return "Lecteur";
            default: return "Utilisateur";
        }
    }

    @Listen("onClick = #logoutMenuitem, #logoutMenuitemc")
    public void onLogout() {
        logger.info("Déconnexion de l'utilisateur");
        try {
            // Nettoyer la session
            Executions.getCurrent().getSession().invalidate();
            // Rediriger vers la page de login
            Executions.sendRedirect("/gedi/login.zul");
        } catch (Exception e) {
            logger.error("Erreur lors de la déconnexion", e);
            Executions.sendRedirect("/gedi/");
        }
    }

    @Listen("onClick = #profilMenuItem")
    public void onProfil() {
        logger.info("Accès au profil utilisateur");
        // TODO: Rediriger vers la page de profil
        Messagebox.show("Page profil à implémenter");
    }

    @Listen("onClick = #togglerBtn")
    public void onToggleSidebar() {
        logger.info("Basculement de la sidebar");
        // Implémentation du toggle sidebar si nécessaire
    }

    @Listen("onClick = #atask")
    public void onTaskClick() {
        logger.info("Clic sur les tâches");
    }

    @Listen("onClick = #anoti")
    public void onNotificationClick() {
        logger.info("Clic sur les notifications");
    }

    @Listen("onClick = #amsg")
    public void onMessageClick() {
        logger.info("Clic sur les messages");
    }

    /**
     * Méthode pour mettre à jour les compteurs de notifications
     */
    public void updateNotificationCounters(int tasks, int notifications, int messages) {
        // Implémentation pour mettre à jour les badges
        logger.info("Mise à jour des compteurs: Tâches={}, Notifications={}, Messages={}", 
                   tasks, notifications, messages);
    }

    /**
     * Méthode pour afficher une notification toast
     */
    public void showToastNotification(String message, String type) {
        try {
            // Implémentation des notifications toast
            Clients.showNotification(message, "info", null, "middle_center", 3000);
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage de la notification", e);
        }
    }
}