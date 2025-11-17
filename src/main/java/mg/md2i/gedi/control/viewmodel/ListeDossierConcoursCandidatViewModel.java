// File Path: src/main/java/mg/md2i/gedi/control/viewmodel/ListeDossierConcoursCandidatViewModel.java
package mg.md2i.gedi.control.viewmodel;

import mg.md2i.enmg.utils.Breadcrumb;
import mg.md2i.enmg.utils.DisplayItem;
import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.*;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Filedownload;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ListeDossierConcoursCandidatViewModel {

    // --- State Variables (Navigation) ---
    private DocumentConcours selectedDocumentType;

    // --- NOUVEAUX ÉTATS POUR LES FILTRES ---
    private Concours selectedConcoursFilter;
    private CentreExamen selectedCentreExamenFilter;
    private String filterNomCandidat = "";

    // --- Data Lists ---
    private List<DisplayItem> displayItems;
    private List<Breadcrumb> breadcrumbs;

    // --- NOUVELLES LISTES POUR LES COMBOBOX ---
    private List<Concours> allConcours;
    private List<CentreExamen> allCentresExamen;

    // --- Cached Data ---
    private List<DocumentConcours> allDocumentTypes;

    @Init
    public void init() {
        // Chargement des données pour les filtres
        allConcours = ConcoursGestion.findAll();
        allCentresExamen = CentreExamenGestion.findAll();

        allDocumentTypes = DocumentConcoursGestion.findAll();
        navigateToRoot();
    }

    private void refresh() {
        buildBreadcrumbs();
        populateDisplayItems();
    }

    private void buildBreadcrumbs() {
        breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Dossiers Candidats", this::navigateToRoot));

        if (selectedDocumentType != null) {
            // Le deuxième niveau n'est plus cliquable, c'est la vue actuelle.
            breadcrumbs.add(new Breadcrumb(selectedDocumentType.getLibelle(), null));
        }
    }

    private void populateDisplayItems() {
        displayItems = new ArrayList<>();

        if (selectedDocumentType == null) {
            // Niveau 1 : Affiche les dossiers de type de document (DEMANDE, DIPLOME, etc.)
            for (DocumentConcours docType : allDocumentTypes) {
                displayItems.add(new DisplayItem(docType.getLibelle(), "z-icon-folder", docType));
            }
        } else {
            // Niveau 2 : Affiche une liste plate de fichiers basée sur les filtres.
            Integer docId = selectedDocumentType.getDocumentConcoursId();
            Integer concoursId = (selectedConcoursFilter != null) ? selectedConcoursFilter.getConcoursId() : null;
            Integer centreId = (selectedCentreExamenFilter != null) ? selectedCentreExamenFilter.getCentreExamenId() : null;

            List<ListeDossierConcoursCandidat> files = ListeDossierConcoursCandidatGestion.findWithAdvancedFilters(
                    docId, concoursId, centreId, filterNomCandidat
            );

            if (files != null) {
                for (ListeDossierConcoursCandidat file : files) {
                    // Logique pour obtenir le libellé de l'état si 'etatDoc' n'est pas déjà rempli
                    if (file.getEtatDoc() == null || file.getEtatDoc().isEmpty()) {
                        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(file.getEtatDocument());
                        file.setEtatDoc(etat.getLabel());
                    }
                    String fileName = getFileName(file);
                    String subtitle = "Candidat: " + getCandidatFullName(file.getCandidat());
                    displayItems.add(new DisplayItem(fileName, subtitle, getFileIcon(fileName), file));
                }
            }
        }
    }

    // --- Navigation Commands ---
    @Command
    @NotifyChange({"displayItems", "breadcrumbs", "selectedDocumentType"})
    public void navigate(@BindingParam("item") DisplayItem item) {
        if (!item.isFolder() || !(item.getData() instanceof DocumentConcours)) return;
        
        this.selectedDocumentType = (DocumentConcours) item.getData();
        applyFilters(); // Appliquer les filtres (vides au début) dès qu'on entre dans un dossier
    }

    @Command
    @NotifyChange({"displayItems", "breadcrumbs", "selectedDocumentType"})
    public void navigateViaBreadcrumb(@BindingParam("crumb") Breadcrumb crumb) {
        // L'objet Breadcrumb sait déjà s'il a une action (navigate() no-op si null)
        if (crumb != null) crumb.navigate();
    }

    // --- Navigation Helpers ---
    private void navigateToRoot() {
        this.selectedDocumentType = null;
        clearFilters(); // Efface les filtres et rafraîchit la vue
    }
    
    // --- NOUVELLES COMMANDES DE FILTRE ---
    @Command
    @NotifyChange("displayItems")
    public void applyFilters() {
        refresh();
    }

    @Command
    @NotifyChange({"displayItems", "selectedConcoursFilter", "selectedCentreExamenFilter", "filterNomCandidat"})
    public void clearFilters() {
        this.selectedConcoursFilter = null;
        this.selectedCentreExamenFilter = null;
        this.filterNomCandidat = "";
        refresh();
    }

    // --- Action Commands (download, delete) restent les mêmes ---
    @Command
    public void download(@BindingParam("item") DisplayItem item) {
        if (item.isFolder() || !(item.getData() instanceof ListeDossierConcoursCandidat)) return;
        ListeDossierConcoursCandidat fileEntity = (ListeDossierConcoursCandidat) item.getData();
        if (fileEntity.getRemarqueFacultatif() == null) return;
        try {
            File f = new File(fileEntity.getRemarqueFacultatif());
            if (!f.exists()) return; // Gérer l'erreur si le fichier n'existe pas
            String contentType = Files.probeContentType(f.toPath());
            if (contentType == null) contentType = "application/octet-stream";
            Filedownload.save(f, contentType);
        } catch (Exception ignored) {}
    }

    @Command
    @NotifyChange("displayItems")
    public void delete(@BindingParam("item") DisplayItem item) {
        if (item.isFolder() || !(item.getData() instanceof ListeDossierConcoursCandidat)) return;
        ListeDossierConcoursCandidat fileEntity = (ListeDossierConcoursCandidat) item.getData();
        ListeDossierConcoursCandidatGestion.delete(fileEntity.getListeDossierConcoursCandidatId());
        refresh(); // Rafraîchir la liste après suppression
    }

    // --- Getters & Setters for ZUL ---
    public List<DisplayItem> getDisplayItems() { return displayItems; }
    public List<Breadcrumb> getBreadcrumbs() { return breadcrumbs; }
    public DocumentConcours getSelectedDocumentType() { return selectedDocumentType; }
    public List<Concours> getAllConcours() { return allConcours; }
    public List<CentreExamen> getAllCentresExamen() { return allCentresExamen; }
    public Concours getSelectedConcoursFilter() { return selectedConcoursFilter; }
    public void setSelectedConcoursFilter(Concours selectedConcoursFilter) { this.selectedConcoursFilter = selectedConcoursFilter; }
    public CentreExamen getSelectedCentreExamenFilter() { return selectedCentreExamenFilter; }
    public void setSelectedCentreExamenFilter(CentreExamen selectedCentreExamenFilter) { this.selectedCentreExamenFilter = selectedCentreExamenFilter; }
    public String getFilterNomCandidat() { return filterNomCandidat; }
    public void setFilterNomCandidat(String filterNomCandidat) { this.filterNomCandidat = filterNomCandidat; }
    
    // --- Helper Methods ---
    private String getCandidatFullName(Candidat c) {
        if (c == null) return "Candidat Inconnu";
        String nom = c.getNom() != null ? c.getNom() : "";
        String prenom = c.getPrenom() != null ? c.getPrenom() : "";
        String full = (nom + " " + prenom).trim();
        return full.isEmpty() ? ("Candidat #" + c.getCandidatId()) : full;
    }

    private String getFileName(ListeDossierConcoursCandidat e) {
        if (e == null || e.getRemarqueFacultatif() == null) return "Nom de fichier invalide";
        String p = e.getRemarqueFacultatif();
        int s = p.lastIndexOf(File.separator);
        return s >= 0 ? p.substring(s + 1) : p;
    }

    private String getFileIcon(String filename) {
        String fn = filename.toLowerCase();
        if (fn.endsWith(".pdf")) return "z-icon-file-pdf-o";
        if (fn.endsWith(".doc") || fn.endsWith(".docx")) return "z-icon-file-word-o";
        if (fn.endsWith(".xls") || fn.endsWith(".xlsx")) return "z-icon-file-excel-o";
        if (fn.endsWith(".png") || fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".gif")) return "z-icon-file-image-o";
        return "z-icon-file-o";
    }
}
