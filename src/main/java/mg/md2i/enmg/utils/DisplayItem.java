package mg.md2i.enmg.utils;

public class DisplayItem {
    private boolean isFolder;
    private String name;
    private String subtitle;
    private String iconSclass;
    private Object data; // L'entité réelle (DocumentConcours, Candidat, ou ListeDossierConcoursCandidat)

    // Constructeur pour un dossier
    public DisplayItem(String name, String iconSclass, Object data) {
        this.isFolder = true;
        this.name = name;
        this.iconSclass = iconSclass;
        this.data = data;
        this.subtitle = "";
    }

    // Constructeur pour un fichier
    public DisplayItem(String name, String subtitle, String iconSclass, Object data) {
        this.isFolder = false;
        this.name = name;
        this.subtitle = subtitle;
        this.iconSclass = iconSclass;
        this.data = data;
    }

    // Getters
    public boolean isFolder() { return isFolder; }
    public String getName() { return name; }
    public String getSubtitle() { return subtitle; }
    public String getIconSclass() { return iconSclass; }
    public Object getData() { return data; }
}
