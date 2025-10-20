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
    private TypeDocumentBiblio selectedDocumentType = null;
    private DomaineOuvrage selectedDomain = null;
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
    // Current page slice for UI rendering
    private List<DocumentBiblio> pagedDocuments = new ArrayList<>();
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
    @NotifyChange({"filteredDocuments", "resultsCountText"})
    public void searchDocuments() {
        applyFilters();
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
    @NotifyChange({"searchQuery", "selectedDocumentType", "selectedDomain", "selectedStatus", "filteredDocuments", "resultsCountText"})
    public void resetSearch() {
        searchQuery = "";
        selectedDocumentType = null;
        selectedDomain = null;
        selectedStatus = "";
        if (documents != null) {
            filteredDocuments = new ArrayList<>(documents);
        } else {
            filteredDocuments = new ArrayList<>();
        }
    }
    
    private void applyFilters() {
        if (documents == null || documents.isEmpty()) {
            filteredDocuments = new ArrayList<>();
            updatePagination();
            return;
        }
        
        filteredDocuments = documents.stream()
            .filter(doc -> {
                // Search query filter
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    String query = searchQuery.toLowerCase().trim();
                    String titre = doc.getTitre() != null ? doc.getTitre().toLowerCase() : "";
                    String isbn = doc.getNumeroIsbn() != null ? doc.getNumeroIsbn().toLowerCase() : "";
                    if (!titre.contains(query) && !isbn.contains(query)) {
                        return false;
                    }
                }
                
                // Document type filter
                if (selectedDocumentType != null && doc.getDetailTypeDocumentBiblio() != null && 
                    doc.getDetailTypeDocumentBiblio().getTypeDocumentBiblio() != null) {
                    String typeLabel = doc.getDetailTypeDocumentBiblio().getTypeDocumentBiblio().getLibelle();
                    if (!Objects.equals(typeLabel, selectedDocumentType.getLibelle())) {
                        return false;
                    }
                }
                
                // Domain filter
                if (selectedDomain != null && doc.getDomaineOuvrage() != null) {
                    if (!Objects.equals(doc.getDomaineOuvrage().getLibelle(), selectedDomain.getLibelle())) {
                        return false;
                    }
                }
                
                // Status filter
                if (selectedStatus != null && !selectedStatus.isEmpty() && !"Tous".equals(selectedStatus)) {
                    String docStatus = doc.getDisponibilite() == 1 ? "Disponible" : "Emprunté";
                    if (!docStatus.equals(selectedStatus)) {
                        return false;
                    }
                }
                
                return true;
            })
            .collect(Collectors.toList());
        updatePagination();
    }
    
    // Filter commands
    @Command
    @NotifyChange({"filteredDocuments", "resultsCountText"})
    public void filterByType() {
        applyFilters();
    }
    
    @Command
    @NotifyChange({"filteredDocuments", "resultsCountText"})
    public void filterByDomain() {
        applyFilters();
    }
    
    @Command
    @NotifyChange({"filteredDocuments", "resultsCountText"})
    public void filterByStatus() {
        applyFilters();
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
    
    @Command
    public void selectDocument(@BindingParam("document") DocumentBiblio document) {
        // Select document for preview or detailed view
        editingDocument = document;
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
    
    @NotifyChange({"documents", "filteredDocuments", "resultsCountText", "pagedDocuments", "currentPageNumber", "totalPages"})
    private void loadDocuments() {
        try {
            documents = DocumentBiblioGestion.findAll();
            filteredDocuments = new ArrayList<>(documents);
            updatePagination();
            
            // Debug: Log the number of documents loaded
            System.out.println("Documents chargés: " + documents.size());
            if (!documents.isEmpty()) {
                DocumentBiblio firstDoc = documents.get(0);
                System.out.println("Premier document - Titre: " + firstDoc.getTitre());
                System.out.println("Premier document - Domaine: " + (firstDoc.getDomaineOuvrage() != null ? firstDoc.getDomaineOuvrage().getLibelle() : "NULL"));
                System.out.println("Premier document - DetailType: " + (firstDoc.getDetailTypeDocumentBiblio() != null ? "EXISTS" : "NULL"));
                if (firstDoc.getDetailTypeDocumentBiblio() != null && firstDoc.getDetailTypeDocumentBiblio().getTypeDocumentBiblio() != null) {
                    System.out.println("Premier document - Type: " + firstDoc.getDetailTypeDocumentBiblio().getTypeDocumentBiblio().getLibelle());
                }
            }
        } catch (Exception e) {
            Clients.showNotification("Erreur lors du chargement des documents: " + e.getMessage(), "error", null, "top_right", 3000);
            e.printStackTrace();
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
            updatePagination();
        }
    }
    
    @Command
    public void previousPage() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
            updatePagination();
        }
    }

    // Helper to compute paging slice from filteredDocuments
    @NotifyChange({"pagedDocuments", "currentPageNumber", "totalPages"})
    private void updatePagination() {
        int total = filteredDocuments != null ? filteredDocuments.size() : 0;
        if (pageSize <= 0) {
            pageSize = 20;
        }
        totalPages = Math.max(1, (int) Math.ceil(total / (double) pageSize));
        if (currentPageNumber > totalPages) currentPageNumber = totalPages;
        if (currentPageNumber < 1) currentPageNumber = 1;

        if (total == 0) {
            pagedDocuments = new ArrayList<>();
            return;
        }
        int startIndex = (currentPageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        if (startIndex >= endIndex) {
            startIndex = 0;
            endIndex = Math.min(pageSize, total);
            currentPageNumber = 1;
        }
        pagedDocuments = filteredDocuments.subList(startIndex, endIndex);
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
    
    // Filter properties getters and setters
    public TypeDocumentBiblio getSelectedDocumentType() { return selectedDocumentType; }
    public void setSelectedDocumentType(TypeDocumentBiblio selectedDocumentType) { this.selectedDocumentType = selectedDocumentType; }
    
    public DomaineOuvrage getSelectedDomain() { return selectedDomain; }
    public void setSelectedDomain(DomaineOuvrage selectedDomain) { this.selectedDomain = selectedDomain; }
    
    public String getSelectedStatus() { return selectedStatus; }
    public void setSelectedStatus(String selectedStatus) { this.selectedStatus = selectedStatus; }
    
    public String getSelectedAvailability() { return selectedAvailability; }
    public void setSelectedAvailability(String selectedAvailability) { this.selectedAvailability = selectedAvailability; }
    
    public String getSelectedJournalType() { return selectedJournalType; }
    public void setSelectedJournalType(String selectedJournalType) { this.selectedJournalType = selectedJournalType; }
    
    public String getSelectedFiliere() { return selectedFiliere; }
    public void setSelectedFiliere(String selectedFiliere) { this.selectedFiliere = selectedFiliere; }
    
    public String getSelectedPromotion() { return selectedPromotion; }
    public void setSelectedPromotion(String selectedPromotion) { this.selectedPromotion = selectedPromotion; }
    
    public Date getSelectedDate() { return selectedDate; }
    public void setSelectedDate(Date selectedDate) { this.selectedDate = selectedDate; }
    
    public List<JournalBiblio> getJournals() { return journals; }
    public void setJournals(List<JournalBiblio> journals) { this.journals = journals; }
    
    public List<RapportStageBiblio> getRapports() { return rapports; }
    public void setRapports(List<RapportStageBiblio> rapports) { this.rapports = rapports; }
    
    public List<DocumentBiblio> getInventoryItems() { return inventoryItems; }
    public void setInventoryItems(List<DocumentBiblio> inventoryItems) { this.inventoryItems = inventoryItems; }
    
    public List<TypeJournal> getJournalTypes() { return journalTypes; }
    public List<FiliereBiblio> getFilieres() { return filieres; }
    public List<PromotionBiblio> getPromotions() { return promotions; }
    
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    
    // Computed property for results count display
    public String getResultsCountText() {
        return "Résultats (" + filteredDocuments.size() + ")";
    }
    
    // Helper methods for ZUL template
    public String getDocumentTypeLabel(DocumentBiblio doc) {
        if (doc != null && doc.getDetailTypeDocumentBiblio() != null && 
            doc.getDetailTypeDocumentBiblio().getTypeDocumentBiblio() != null) {
            return doc.getDetailTypeDocumentBiblio().getTypeDocumentBiblio().getLibelle();
        }
        return "Non défini";
    }
    
    public String getDomainLabel(DocumentBiblio doc) {
        if (doc != null && doc.getDomaineOuvrage() != null) {
            return doc.getDomaineOuvrage().getLibelle();
        }
        return "Non défini";
    }
    
    public String getAuthorLabel(DocumentBiblio doc) {
        // DocumentBiblio doesn't have a direct author field
        // You might need to add this relationship or use a different approach
        return "Non spécifié";
    }
    
    public String getPublisherLabel(DocumentBiblio doc) {
        if (doc != null && doc.getEditeur() != null) {
            return doc.getEditeur().getEditeur(); // The field is called 'editeur', not 'nom'
        }
        return "Non spécifié";
    }
    
    public String getStatusLabel(DocumentBiblio doc) {
        return doc.getDisponibilite() == 1 ? "Disponible" : "Emprunté";
    }
    
    public String getStatusClass(DocumentBiblio doc) {
        if (doc == null) return "badge";
        return doc.getDisponibilite() == 1 ? "badge badge-success" : "badge badge-warning";
    }

    // Paging data getter for ZUL
    public List<DocumentBiblio> getPagedDocuments() { return pagedDocuments; }
}
