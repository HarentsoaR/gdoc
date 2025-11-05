package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.biblio.DocumentBiblio;
import mg.md2i.gedi.gestionmetier.biblio.DocumentBiblioGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ViewModel for /biblio/views/documents/list.zul
 * Manages CRUD operations, searching, filtering, and pagination for DocumentBiblio entities.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BiblioDocumentViewModel {

    private static final Logger log = LoggerFactory.getLogger(BiblioDocumentViewModel.class);

    // Injected Spring service/bean for data access
    @WireVariable
    private DocumentBiblioGestion documentBiblioGestion;

    private List<DocumentBiblio> allDocuments;
    private List<DocumentBiblio> pagedDocuments;
    private String searchQuery;

    // Filter properties
    private List<String> documentTypes = Arrays.asList("Tous", "Livre", "Magazine", "Article");
    private String selectedDocumentType = "Tous";
    private List<String> domains = Arrays.asList("Tous", "Informatique", "Science", "Histoire");
    private String selectedDomain = "Tous";
    private List<String> availabilityOptions = Arrays.asList("Tous", "Disponible", "Indisponible");
    private String selectedAvailability = "Tous";

    // Pagination properties
    private int currentPageNumber = 1;
    private int pageSize = 10;
    private int totalPages = 1;

    @Init
    public void init() {
        // The @WireVariable for documentBiblioGestion is injected after init,
        // so we move the initial data loading to an @AfterCompose method.
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireVariables(view, this, Selectors.newVariableResolvers(getClass(), null));
        log.info("DocumentBiblioGestion injected: {}", (documentBiblioGestion != null));
        loadDocuments();
    }

    @Command
    @NotifyChange({"pagedDocuments", "currentPageNumber", "totalPages"})
    public void loadDocuments() {
        log.info("Loading all documents...");
        // In a real app, you would fetch from a service.
        // For now, we use the injected gestion class.
        allDocuments = documentBiblioGestion.findAll(); // Assuming this method exists
        applyFiltersAndPagination();
        log.info("Loaded {} documents.", allDocuments.size());
    }

    @Command
    @NotifyChange({"pagedDocuments", "currentPageNumber", "totalPages"})
    public void searchDocuments() {
        applyFiltersAndPagination();
    }

    @Command
    @NotifyChange({"searchQuery", "pagedDocuments", "currentPageNumber", "totalPages"})
    public void resetSearch() {
        this.searchQuery = "";
        applyFiltersAndPagination();
    }

    @Command
    @NotifyChange({"pagedDocuments", "currentPageNumber", "totalPages"})
    public void filterByType() {
        applyFiltersAndPagination();
    }

    @Command
    @NotifyChange({"pagedDocuments", "currentPageNumber", "totalPages"})
    public void filterByDomain() {
        applyFiltersAndPagination();
    }

    @Command
    @NotifyChange({"pagedDocuments", "currentPageNumber", "totalPages"})
    public void filterByAvailability() {
        applyFiltersAndPagination();
    }

    @Command
    @NotifyChange({"pagedDocuments", "currentPageNumber"})
    public void previousPage() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
            applyFiltersAndPagination();
        }
    }

    @Command
    @NotifyChange({"pagedDocuments", "currentPageNumber"})
    public void nextPage() {
        if (currentPageNumber < totalPages) {
            currentPageNumber++;
            applyFiltersAndPagination();
        }
    }

    @Command
    public void newDocument() {
        Map<String, Object> args = new HashMap<>();
        args.put("document", new DocumentBiblio());
        args.put("viewModel", this); // Pass this VM to the modal
        Executions.createComponents("/biblio/views/documents/edit.zul", null, args);
    }

    @Command
    public void editDocument(DocumentBiblio document) {
        Map<String, Object> args = new HashMap<>();
        args.put("document", document);
        args.put("viewModel", this);
        Executions.createComponents("/biblio/views/documents/edit.zul", null, args);
    }

    @Command
    public void deleteDocument(DocumentBiblio document) {
        Messagebox.show("Êtes-vous sûr de vouloir supprimer ce document : " + document.getTitre() + "?",
            "Confirmation de suppression", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
            event -> {
                if (Messagebox.ON_YES.equals(event.getName())) {
                    try {
                        documentBiblioGestion.delete(document.getId()); // Assuming a delete method
                        Messagebox.show("Document supprimé avec succès.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                        loadDocuments(); // Refresh the list
                    } catch (Exception e) {
                        log.error("Error deleting document", e);
                        Messagebox.show("Erreur lors de la suppression du document.", "Erreur", Messagebox.OK, Messagebox.ERROR);
                    }
                }
            });
    }

    @Command
    public void viewDocument(DocumentBiblio document) {
        Messagebox.show("Affichage du document : " + document.getTitre(), "Détail", Messagebox.OK, Messagebox.INFORMATION);
    }

    private void applyFiltersAndPagination() {
        // This is a placeholder for actual filtering logic.
        // In a real application, this would be done efficiently in the database.
        List<DocumentBiblio> filtered = allDocuments; // Start with all documents

        // 1. Apply search and filters (example)
        // filtered = allDocuments.stream()...

        // 2. Apply pagination
        int totalSize = filtered.size();
        totalPages = (int) Math.ceil((double) totalSize / pageSize);
        if (totalPages == 0) totalPages = 1;
        if (currentPageNumber > totalPages) currentPageNumber = totalPages;

        int startIndex = (currentPageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalSize);

        pagedDocuments = filtered.subList(startIndex, endIndex);
        
        // Notify the binder that dependent properties have changed
        BindUtils.postNotifyChange(null, null, this, "pagedDocuments");
        BindUtils.postNotifyChange(null, null, this, "totalPages");
        BindUtils.postNotifyChange(null, null, this, "currentPageNumber");
    }

    // Getters for ZUL binding
    public List<DocumentBiblio> getPagedDocuments() { return pagedDocuments; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public List<String> getDocumentTypes() { return documentTypes; }
    public String getSelectedDocumentType() { return selectedDocumentType; }
    public void setSelectedDocumentType(String selectedDocumentType) { this.selectedDocumentType = selectedDocumentType; }
    public List<String> getDomains() { return domains; }
    public String getSelectedDomain() { return selectedDomain; }
    public void setSelectedDomain(String selectedDomain) { this.selectedDomain = selectedDomain; }
    public List<String> getAvailabilityOptions() { return availabilityOptions; }
    public String getSelectedAvailability() { return selectedAvailability; }
    public void setSelectedAvailability(String selectedAvailability) { this.selectedAvailability = selectedAvailability; }
    public int getCurrentPageNumber() { return currentPageNumber; }
    public int getTotalPages() { return totalPages; }

    // Helper methods for display labels (placeholders)
    public String getAuthorLabel(DocumentBiblio doc) { return doc.getAuteur() != null ? doc.getAuteur() : "N/A"; }
    public String getPublisherLabel(DocumentBiblio doc) { return doc.getEditeur() != null ? doc.getEditeur() : "N/A"; }
    public String getDocumentTypeLabel(DocumentBiblio doc) { return doc.getTypeDocument() != null ? doc.getTypeDocument() : "N/A"; }
}