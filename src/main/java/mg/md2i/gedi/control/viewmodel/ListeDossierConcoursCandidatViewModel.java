package mg.md2i.gedi.control.viewmodel;

import mg.md2i.enmg.utils.Breadcrumb;
import mg.md2i.enmg.utils.DisplayItem;
import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.gestionmetier.*;
import mg.md2i.gedi.trash.TrashManager;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ListeDossierConcoursCandidatViewModel {

    private DocumentConcours selectedDocumentType;
    private Concours selectedConcoursFilter;
    private CentreExamen selectedCentreExamenFilter;
    private String filterNomCandidat = "";

    private List<DisplayItem> displayItems;
    private List<Breadcrumb> breadcrumbs;

    private List<Concours> allConcours;
    private List<CentreExamen> allCentresExamen;

    private List<DocumentConcours> allDocumentTypes;
    private boolean filtersVisible = true;

    @Init
    public void init() {
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
            breadcrumbs.add(new Breadcrumb(selectedDocumentType.getLibelle(), null));
        }
    }

    private void populateDisplayItems() {
        displayItems = new ArrayList<>();

        if (selectedDocumentType == null) {
            for (DocumentConcours docType : allDocumentTypes) {
                displayItems.add(new DisplayItem(docType.getLibelle(), "z-icon-folder", docType));
            }
        } else {
            Integer docId = selectedDocumentType.getDocumentConcoursId();
            Integer concoursId = (selectedConcoursFilter != null) ? selectedConcoursFilter.getConcoursId() : null;
            Integer centreId = (selectedCentreExamenFilter != null) ? selectedCentreExamenFilter.getCentreExamenId() : null;

            List<ListeDossierConcoursCandidat> files = ListeDossierConcoursCandidatGestion.findWithAdvancedFilters(
                    docId, concoursId, centreId, filterNomCandidat
            );

            if (files != null) {
                for (ListeDossierConcoursCandidat file : files) {
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

    @Command
    @NotifyChange({"displayItems", "breadcrumbs", "selectedDocumentType"})
    public void navigate(@BindingParam("item") DisplayItem item) {
        openFolder(item);
    }

    private void openFolder(DisplayItem item) {
        if (item == null || !item.isFolder() || !(item.getData() instanceof DocumentConcours)) return;
        this.selectedDocumentType = (DocumentConcours) item.getData();
        applyFilters();
    }

    @Command
    @NotifyChange({"displayItems", "breadcrumbs", "selectedDocumentType"})
    public void navigateViaBreadcrumb(@BindingParam("crumb") Breadcrumb crumb) {
        if (crumb != null) crumb.navigate();
    }

    private void navigateToRoot() {
        this.selectedDocumentType = null;
        clearFilters();
    }

    @Command
    @NotifyChange("displayItems")
    public void applyFilters() {
        refresh();
    }

    @Command
    @NotifyChange({"displayItems", "selectedConcoursFilter", "selectedCentreExamenFilter", "filterNomCandidat", "anyFilterActive"})
    public void clearFilters() {
        this.selectedConcoursFilter = null;
        this.selectedCentreExamenFilter = null;
        this.filterNomCandidat = "";
        refresh();
    }

    @Command
    public void download(@BindingParam("item") DisplayItem item) {
        if (item.isFolder() || !(item.getData() instanceof ListeDossierConcoursCandidat)) return;
        ListeDossierConcoursCandidat fileEntity = (ListeDossierConcoursCandidat) item.getData();
        if (fileEntity.getRemarqueFacultatif() == null) return;
        try {
            File f = new File(fileEntity.getRemarqueFacultatif());
            if (!f.exists()) return;
            String contentType = Files.probeContentType(f.toPath());
            if (contentType == null) contentType = "application/octet-stream";
            Filedownload.save(f, contentType);
        } catch (Exception ignored) {}
    }

    @Command
    public void openItem(@BindingParam("item") DisplayItem item) {
        if (item == null) return;
        if (item.isFolder()) {
            openFolder(item);
            BindUtils.postNotifyChange(null, null, this, "displayItems");
            BindUtils.postNotifyChange(null, null, this, "breadcrumbs");
            BindUtils.postNotifyChange(null, null, this, "selectedDocumentType");
        } else {
            download(item);
        }
    }

    @Command
    public void delete(@BindingParam("item") DisplayItem item) {
        if (item == null || item.isFolder() || !(item.getData() instanceof ListeDossierConcoursCandidat)) return;
        Messagebox.show("Déplacer ce document dans la corbeille ?", "Suppression sécurisée",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, (Event evt) -> {
                    if ("onYes".equals(evt.getName())) {
                        ListeDossierConcoursCandidat entity = (ListeDossierConcoursCandidat) item.getData();
                        ListeDossierConcoursCandidatGestion.moveToTrash(entity.getListeDossierConcoursCandidatId());
                        String documentLabel = selectedDocumentType != null
                                ? selectedDocumentType.getLibelle()
                                : (entity.getDocumentConcours() != null ? entity.getDocumentConcours().getLibelle() : "Document");
                        TrashManager.registerDocument(entity, documentLabel);
                        refresh();
                        BindUtils.postNotifyChange(null, null, ListeDossierConcoursCandidatViewModel.this, "displayItems");
                    }
                });
    }

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
    public boolean isFiltersVisible() { return filtersVisible; }

    @Command
    @NotifyChange("filtersVisible")
    public void toggleFilters() { filtersVisible = !filtersVisible; }

    public boolean isAnyFilterActive() {
        return selectedConcoursFilter != null
                || selectedCentreExamenFilter != null
                || (filterNomCandidat != null && !filterNomCandidat.trim().isEmpty());
    }

    @Command
    @NotifyChange({"selectedConcoursFilter","selectedCentreExamenFilter","filterNomCandidat","displayItems", "anyFilterActive"})
    public void removeFilter(@BindingParam("type") String type) {
        if ("concours".equals(type)) {
            selectedConcoursFilter = null;
        } else if ("centre".equals(type)) {
            selectedCentreExamenFilter = null;
        } else if ("nom".equals(type)) {
            filterNomCandidat = "";
        }
        refresh();
    }

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

    public String getCandidatLabel(DisplayItem item) {
        ListeDossierConcoursCandidat entity = asEntity(item);
        return entity == null ? "-" : getCandidatFullName(entity.getCandidat());
    }

    public String getEtatLabel(DisplayItem item) {
        ListeDossierConcoursCandidat entity = asEntity(item);
        if (entity == null) return "-";
        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(entity.getEtatDocument());
        entity.setEtatDoc(etat.getLabel());
        return entity.getEtatDoc();
    }

    public String getEtatSclass(DisplayItem item) {
        ListeDossierConcoursCandidat entity = asEntity(item);
        if (entity == null) return "";
        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(entity.getEtatDocument());
        return "status-label " + etat.getChipSclass();
    }

    private ListeDossierConcoursCandidat asEntity(DisplayItem item) {
        if (item == null || item.isFolder() || !(item.getData() instanceof ListeDossierConcoursCandidat)) {
            return null;
        }
        return (ListeDossierConcoursCandidat) item.getData();
    }
}