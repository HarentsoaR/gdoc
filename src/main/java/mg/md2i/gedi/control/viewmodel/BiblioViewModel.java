package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.gestionmetier.*;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ViewModel for Biblio Management System
 * Handles all biblio-related operations and data binding
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BiblioViewModel {
    
    // Current view and navigation
    private String currentView = "/biblio/views/dashboard.zul";
    private String currentSection = "Bibliothèque";
    private String currentPage = "Tableau de bord";
    
    // Navigation state
    private boolean documentsExpanded = true;
    private boolean inventoryExpanded = false;
    private boolean categoriesExpanded = false;
    
    // Search and filtering
    private String searchQuery = "";
    private String selectedDocumentType = "";
    private String selectedDomain = "";
    private String selectedStatus = "";
    private String selectedAvailability = "";
    private String selectedJournalType = "";
    private String selectedFiliere = "";
    private String selectedPromotion = "";
    private Date selectedDate;
    
    // Data collections
    private List<DocumentBiblio> documents = new ArrayList<>();
    private List<JournalBiblio> journals = new ArrayList<>();
    private List<RapportStageBiblio> rapports = new ArrayList<>();
    private List<DocumentBiblio> recentDocuments = new ArrayList<>();
    private List<DocumentBiblio> filteredDocuments = new ArrayList<>();
    private List<DocumentBiblio> inventoryItems = new ArrayList<>();
    
    // Lookup data
    private List<TypeDocumentBiblio> documentTypes = new ArrayList<>();
    private List<DomaineOuvrage> domains = new ArrayList<>();
    private List<CategorieOuvrage> categories = new ArrayList<>();
    private List<CollectionOuvrage> collections = new ArrayList<>();
    private List<Editeur> editeurs = new ArrayList<>();
    private List<Origine> origines = new ArrayList<>();
    private List<TypeJournal> journalTypes = new ArrayList<>();
    private List<FiliereBiblio> filieres = new ArrayList<>();
    private List<PromotionBiblio> promotions = new ArrayList<>();
    
    // Form data
    private DocumentBiblio newDocument = new DocumentBiblio();
    private DocumentBiblio editingDocument = new DocumentBiblio();
    
    // Statistics
    private int totalDocuments = 0;
    private int totalOuvrages = 0;
    private int totalJournaux = 0;
    private int totalRapports = 0;
    private int documentsDisponibles = 0;
    private int documentsEmpruntes = 0;
    private int totalItems = 0;
    private int availableItems = 0;
    private int loanedItems = 0;
    private int unavailableItems = 0;
    
    // Pagination
    private int currentPageNumber = 1;
    private int totalPages = 1;
    private int pageSize = 20;
    
    // Status options
    private List<String> statusOptions = Arrays.asList("Tous", "Disponible", "Indisponible", "Emprunté");
    private List<String> availabilityOptions = Arrays.asList("Tous", "Disponible", "Indisponible");
    
    @Init
    public void init() {
        loadLookupData();
        loadStatistics();
        loadRecentDocuments();
        loadDocuments();
        
        // Handle URL parameters for view navigation
        String viewParam = Executions.getCurrent().getParameter("view");
        if (viewParam != null && !viewParam.isEmpty()) {
            currentView = viewParam;
        }
    }
    
    // Navigation commands
    @Command
    @NotifyChange({"currentView", "currentSection", "currentPage"})
    public void navigateTo(@BindingParam("view") String view, 
                          @BindingParam("label") String label,
                          @BindingParam("section") String section,
                          @BindingParam("page") String page) {
        try {
            this.currentView = view;
            this.currentPage = page != null ? page : label;
            this.currentSection = section != null ? section : "Bibliothèque";
            
            // Load data based on view
            if (view != null) {
                if (view.contains("documents/list")) {
                    loadDocuments();
                } else if (view.contains("journals/list")) {
                    loadJournals();
                } else if (view.contains("rapports/list")) {
                    loadRapports();
                } else if (view.contains("inventaire/list")) {
                    loadInventory();
                } else if (view.contains("dashboard")) {
                    loadStatistics();
                    loadRecentDocuments();
                }
            }
        } catch (Exception e) {
            Clients.showNotification("Erreur lors de la navigation: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @Command
    public void goHome() {
        try {
            Executions.sendRedirect("/home");
        } catch (Exception e) {
            Clients.showNotification("Erreur lors de la navigation: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    // Toggle sections
    @Command
    @NotifyChange("documentsExpanded")
    public void toggleDocumentsSection() {
        documentsExpanded = !documentsExpanded;
    }
    
    @Command
    @NotifyChange("inventoryExpanded")
    public void toggleInventorySection() {
        inventoryExpanded = !inventoryExpanded;
    }
    
    @Command
    @NotifyChange("categoriesExpanded")
    public void toggleCategoriesSection() {
        categoriesExpanded = !categoriesExpanded;
    }
    
    // Search and filter commands
    @Command
    public void search() {
        if (currentView.contains("documents")) {
            searchDocuments();
        } else if (currentView.contains("journals")) {
            searchJournals();
        } else if (currentView.contains("rapports")) {
            searchRapports();
        } else if (currentView.contains("inventaire")) {
            searchInventory();
        }
    }
    
    @Command
    public void searchDocuments() {
        filteredDocuments = documents.stream()
            .filter(doc -> searchQuery.isEmpty() || 
                doc.getTitre().toLowerCase().contains(searchQuery.toLowerCase()) ||
                doc.getNumeroIsbn().toLowerCase().contains(searchQuery.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    @Command
    public void searchJournals() {
        // Implement journal search
    }
    
    @Command
    public void searchRapports() {
        // Implement rapport search
    }
    
    @Command
    public void searchInventory() {
        // Implement inventory search
    }
    
    @Command
    public void resetSearch() {
        searchQuery = "";
        filteredDocuments = new ArrayList<>(documents);
    }
    
    // Filter commands
    @Command
    public void filterByType() {
        // Implement type filtering
    }
    
    @Command
    public void filterByDomain() {
        // Implement domain filtering
    }
    
    @Command
    public void filterByStatus() {
        // Implement status filtering
    }
    
    @Command
    public void filterByAvailability() {
        // Implement availability filtering
    }
    
    @Command
    public void filterByJournalType() {
        // Implement journal type filtering
    }
    
    @Command
    public void filterByFiliere() {
        // Implement filiere filtering
    }
    
    @Command
    public void filterByPromotion() {
        // Implement promotion filtering
    }
    
    @Command
    public void filterByDate() {
        // Implement date filtering
    }
    
    // Document management commands
    @Command
    public void openNewDocumentModal() {
        newDocument = new DocumentBiblio();
        // Open modal for new document
    }
    
    @Command
    public void saveDocument() {
        try {
            DocumentBiblioGestion.save(newDocument);
            Clients.showNotification("Document enregistré avec succès", "success", null, "top_right", 2000);
            loadDocuments();
            newDocument = new DocumentBiblio();
        } catch (Exception e) {
            Clients.showNotification("Erreur lors de l'enregistrement: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @Command
    public void editDocument(@BindingParam("document") DocumentBiblio document) {
        editingDocument = document;
        // Navigate to edit view
    }
    
    @Command
    public void updateDocument() {
        try {
            DocumentBiblioGestion.save(editingDocument);
            Clients.showNotification("Document mis à jour avec succès", "success", null, "top_right", 2000);
            loadDocuments();
        } catch (Exception e) {
            Clients.showNotification("Erreur lors de la mise à jour: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @Command
    public void deleteDocument(@BindingParam("document") DocumentBiblio document) {
        try {
            DocumentBiblioGestion.delete(document.getDocumentId());
            Clients.showNotification("Document supprimé avec succès", "success", null, "top_right", 2000);
            loadDocuments();
        } catch (Exception e) {
            Clients.showNotification("Erreur lors de la suppression: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @Command
    public void viewDocument(@BindingParam("document") DocumentBiblio document) {
        // Show document details
    }
    
    @Command
    public void showDocumentDetails(@BindingParam("document") DocumentBiblio document) {
        // Show detailed document information
    }
    
    // Data loading methods
    private void loadLookupData() {
        try {
            documentTypes = TypeDocumentBiblioGestion.findAll();
            domains = DomaineOuvrageGestion.findAll();
            categories = CategorieOuvrageGestion.findAll();
            collections = CollectionOuvrageGestion.findAll();
            editeurs = EditeurGestion.findAll();
            origines = OrigineGestion.findAll();
            journalTypes = TypeJournalGestion.findAll();
            filieres = FiliereBiblioGestion.findAll();
            promotions = PromotionBiblioGestion.findAll();
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement des données de référence: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @NotifyChange({"totalDocuments", "totalJournaux", "totalRapports", "documentsDisponibles", "documentsEmpruntes", "totalItems", "availableItems", "unavailableItems"})
    private void loadStatistics() {
        try {
            totalDocuments = DocumentBiblioGestion.findAll().size();
            totalJournaux = JournalBiblioGestion.findAll().size();
            totalRapports = RapportStageBiblioGestion.findAll().size();
            
            documentsDisponibles = (int) DocumentBiblioGestion.findAll().stream()
                .filter(doc -> doc.getDisponibilite() == 1)
                .count();
                
            documentsEmpruntes = totalDocuments - documentsDisponibles;
            totalItems = totalDocuments + totalJournaux + totalRapports;
            availableItems = documentsDisponibles;
            unavailableItems = totalDocuments - documentsDisponibles;
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement des statistiques: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @NotifyChange("recentDocuments")
    private void loadRecentDocuments() {
        try {
            recentDocuments = DocumentBiblioGestion.findAll().stream()
                .sorted((a, b) -> b.getDateEntree().compareTo(a.getDateEntree()))
                .limit(6)
                .collect(Collectors.toList());
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement des documents récents: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @NotifyChange({"documents", "filteredDocuments"})
    private void loadDocuments() {
        try {
            documents = DocumentBiblioGestion.findAll();
            filteredDocuments = new ArrayList<>(documents);
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement des documents: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @NotifyChange("journals")
    private void loadJournals() {
        try {
            journals = JournalBiblioGestion.findAll();
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement des journaux: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @NotifyChange("rapports")
    private void loadRapports() {
        try {
            rapports = RapportStageBiblioGestion.findAll();
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement des rapports: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    @NotifyChange("inventoryItems")
    private void loadInventory() {
        try {
            inventoryItems = DocumentBiblioGestion.findAll();
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement de l'inventaire: " + e.getMessage(), "error", null, "top_right", 3000);
        }
    }
    
    // Pagination commands
    @Command
    public void nextPage() {
        if (currentPageNumber < totalPages) {
            currentPageNumber++;
            loadDocuments();
        }
    }
    
    @Command
    public void previousPage() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
            loadDocuments();
        }
    }
    
    // Modal commands
    @Command
    public void openAdvancedSearch() {
        // Open advanced search modal
    }
    
    @Command
    public void openSettings() {
        // Open settings modal
    }
    
    @Command
    public void openImportModal() {
        // Open import modal
    }
    
    @Command
    public void exportData() {
        // Export data functionality
    }
    
    @Command
    public void openSearchModal() {
        // Open search modal
    }
    
    @Command
    public void openReportsModal() {
        // Open reports modal
    }
    
    // Getters and Setters
    public String getCurrentView() { return currentView; }
    public void setCurrentView(String currentView) { this.currentView = currentView; }
    
    public String getCurrentSection() { return currentSection; }
    public void setCurrentSection(String currentSection) { this.currentSection = currentSection; }
    
    public String getCurrentPage() { return currentPage; }
    public void setCurrentPage(String currentPage) { this.currentPage = currentPage; }
    
    public boolean isDocumentsExpanded() { return documentsExpanded; }
    public void setDocumentsExpanded(boolean documentsExpanded) { this.documentsExpanded = documentsExpanded; }
    
    public boolean isInventoryExpanded() { return inventoryExpanded; }
    public void setInventoryExpanded(boolean inventoryExpanded) { this.inventoryExpanded = inventoryExpanded; }
    
    public boolean isCategoriesExpanded() { return categoriesExpanded; }
    public void setCategoriesExpanded(boolean categoriesExpanded) { this.categoriesExpanded = categoriesExpanded; }
    
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    
    public List<DocumentBiblio> getDocuments() { return documents; }
    public void setDocuments(List<DocumentBiblio> documents) { this.documents = documents; }
    
    public List<DocumentBiblio> getFilteredDocuments() { return filteredDocuments; }
    public void setFilteredDocuments(List<DocumentBiblio> filteredDocuments) { this.filteredDocuments = filteredDocuments; }
    
    public List<DocumentBiblio> getRecentDocuments() { return recentDocuments; }
    public void setRecentDocuments(List<DocumentBiblio> recentDocuments) { this.recentDocuments = recentDocuments; }
    
    public int getTotalDocuments() { return totalDocuments; }
    public int getTotalOuvrages() { return totalOuvrages; }
    public int getTotalJournaux() { return totalJournaux; }
    public int getTotalRapports() { return totalRapports; }
    public int getDocumentsDisponibles() { return documentsDisponibles; }
    public int getDocumentsEmpruntes() { return documentsEmpruntes; }
    public int getTotalItems() { return totalItems; }
    public int getAvailableItems() { return availableItems; }
    public int getLoanedItems() { return loanedItems; }
    public int getUnavailableItems() { return unavailableItems; }
    
    public DocumentBiblio getNewDocument() { return newDocument; }
    public void setNewDocument(DocumentBiblio newDocument) { this.newDocument = newDocument; }
    
    public DocumentBiblio getEditingDocument() { return editingDocument; }
    public void setEditingDocument(DocumentBiblio editingDocument) { this.editingDocument = editingDocument; }
    
    public List<TypeDocumentBiblio> getDocumentTypes() { return documentTypes; }
    public List<DomaineOuvrage> getDomains() { return domains; }
    public List<CategorieOuvrage> getCategories() { return categories; }
    public List<CollectionOuvrage> getCollections() { return collections; }
    public List<Editeur> getEditeurs() { return editeurs; }
    public List<Origine> getOrigines() { return origines; }
    public List<String> getStatusOptions() { return statusOptions; }
    public List<String> getAvailabilityOptions() { return availabilityOptions; }
    
    public int getCurrentPageNumber() { return currentPageNumber; }
    public int getTotalPages() { return totalPages; }
}
