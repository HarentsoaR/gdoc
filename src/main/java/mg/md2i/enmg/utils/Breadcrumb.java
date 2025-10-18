package mg.md2i.enmg.utils;

public class Breadcrumb {
    private String label;
    private Runnable action; // Action à exécuter au clic

    public Breadcrumb(String label, Runnable action) {
        this.label = label;
        this.action = action;
    }

    public String getLabel() { return label; }
    
    // Méthode pour être appelée depuis le ZUL via @command
    public void navigate() {
        if (action != null) {
            action.run();
        }
    }
}
