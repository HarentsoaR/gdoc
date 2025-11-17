package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Fonctionnalite;
import mg.md2i.gedi.gestionmetier.FonctionnaliteGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class FonctionnaliteFormViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(FonctionnaliteFormViewModel.class);

    private Fonctionnalite currentFonctionnalite;
    private boolean isNewFonctionnaliteMode;

    @Init
    public void init(@ExecutionArgParam("fonctionnaliteToEdit") Fonctionnalite fonctionnaliteToEdit) {
        if (fonctionnaliteToEdit != null && fonctionnaliteToEdit.getFonctionnaliteId() != null) {
            this.currentFonctionnalite = fonctionnaliteToEdit;
            this.isNewFonctionnaliteMode = false;
        } else {
            this.currentFonctionnalite = new Fonctionnalite();
            this.currentFonctionnalite.setActif(1);
            this.currentFonctionnalite.setVersion(1);
            this.currentFonctionnalite.setGauche(0);
            this.currentFonctionnalite.setDroite(0);
            this.currentFonctionnalite.setNiveau(0);
            this.isNewFonctionnaliteMode = true;
        }
    }

    @Command
    public void saveFonctionnalite(@ContextParam(ContextType.VIEW) Component view) {
        if (isBlank(currentFonctionnalite.getLibelle())) {
            Messagebox.show("Le libellé est requis.", "Validation", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        try {
            FonctionnaliteGestion.saveFonctionnalite(currentFonctionnalite);
            Messagebox.show("Fonctionnalité sauvegardée avec succès!", "Succès", Messagebox.OK, Messagebox.INFORMATION);
            BindUtils.postGlobalCommand(null, null, "refreshFonctionnaliteList", null);
            if (view instanceof Window) {
                ((Window) view).detach();
            }
        } catch (Exception e) {
            LOG.error("Erreur lors de la sauvegarde", e);
            Messagebox.show("Échec de la sauvegarde: " + e.getMessage(), "Erreur", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void cancelFonctionnaliteForm(@ContextParam(ContextType.VIEW) Component view) {
        String confirmMessage = isNewFonctionnaliteMode ? "Annuler la création de la fonctionnalité ?" : "Annuler les modifications ?";
        Messagebox.show(confirmMessage, "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        if (view instanceof Window) {
                            ((Window) view).detach();
                        }
                    }
                });
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public Fonctionnalite getCurrentFonctionnalite() { return currentFonctionnalite; }
    public void setCurrentFonctionnalite(Fonctionnalite cf) { this.currentFonctionnalite = cf; }
    public String getFormTitle() { return isNewFonctionnaliteMode ? "Nouvelle Fonctionnalité" : "Modifier la Fonctionnalité"; }
    public String getSaveActionLabel() { return isNewFonctionnaliteMode ? "Créer" : "Mettre à jour"; }
}