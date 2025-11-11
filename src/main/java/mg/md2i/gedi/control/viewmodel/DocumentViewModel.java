package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.dto.SearchResult;
import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.gestionmetier.DocumentGestion;
import mg.md2i.gedi.services.LuceneService;
import mg.md2i.gedi.services.impl.LuceneServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentViewModel {

    private static final Logger log = LoggerFactory.getLogger(DocumentViewModel.class);
    
    private LuceneService luceneService = new LuceneServiceImpl();

    private List<Document> documents = new ArrayList<>();
    private Map<String, Object> navigationArgs;
    private CandidatViewModel candidatViewModel = new CandidatViewModel();
    private boolean concoursExpanded = false;
    
    private List<SearchResult> searchResults = new ArrayList<>();
    private List<SearchResult> suggestions = new ArrayList<>();
    private List<SearchResult> filteredSearchResults = new ArrayList<>();
    private String searchQuery = "";
    private String currentPath = "Mes Documents";
    private String currentView = "/documents/views/dashboard.zul";

    private List<String> availableConcoursFilters = new ArrayList<>();
    private List<String> availableDocTypeFilters = new ArrayList<>();
    private String selectedConcoursFilter = "";
    private String selectedDocTypeFilter = "";
    
    private List<String> availableFilieres = new ArrayList<>();
    private List<String> availablePromotions = new ArrayList<>();
    private List<String> availableCentres = new ArrayList<>();
    private String selectedFiliere = "";
    private String selectedPromotion = "";
    private String selectedCentre = "";
    
    // This is the state for our sidebar
    private boolean sidebarCollapsed = false;
    public boolean isSidebarCollapsed() { return sidebarCollapsed; }
    public void setSidebarCollapsed(boolean sidebarCollapsed) { this.sidebarCollapsed = sidebarCollapsed; }

    // This is the command that correctly toggles the state and notifies the UI
    @Command
    @NotifyChange("sidebarCollapsed")
    public void toggleSidebar() {
        sidebarCollapsed = !sidebarCollapsed;
    }

    @Wire("#searchBox") 
    private Bandbox searchBox;

    public List<Document> getDocuments() { return documents; }
    public void setDocuments(List<Document> documents) { this.documents = documents; }
    public List<SearchResult> getSearchResults() { return searchResults; }
    public List<SearchResult> getSuggestions() { return suggestions; } 
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public String getCurrentPath() { return currentPath; }
    public void setCurrentPath(String currentPath) { this.currentPath = currentPath; }
    public String getCurrentView() { return currentView; }
    public void setCurrentView(String currentView) { this.currentView = currentView; }
    public Map<String, Object> getNavigationArgs() { return navigationArgs; }
    public void setNavigationArgs(Map<String, Object> navigationArgs) { this.navigationArgs = navigationArgs; }
    public CandidatViewModel getCandidatViewModel() { return candidatViewModel; }
    public void setCandidatViewModel(CandidatViewModel candidatViewModel) { this.candidatViewModel = candidatViewModel; }
    public boolean isConcoursExpanded() { return concoursExpanded; }
    public void setConcoursExpanded(boolean concoursExpanded) { this.concoursExpanded = concoursExpanded; }
    public List<SearchResult> getFilteredSearchResults() { return filteredSearchResults; }
    public List<String> getAvailableConcoursFilters() { return availableConcoursFilters; }
    public List<String> getAvailableDocTypeFilters() { return availableDocTypeFilters; }
    public String getSelectedConcoursFilter() { return selectedConcoursFilter; }
    public void setSelectedConcoursFilter(String selectedConcoursFilter) { this.selectedConcoursFilter = selectedConcoursFilter; }
    public String getSelectedDocTypeFilter() { return selectedDocTypeFilter; }
    public void setSelectedDocTypeFilter(String selectedDocTypeFilter) { this.selectedDocTypeFilter = selectedDocTypeFilter; }
    public List<String> getAvailableFilieres() { return availableFilieres; }
    public List<String> getAvailablePromotions() { return availablePromotions; }
    public List<String> getAvailableCentres() { return availableCentres; }
    public String getSelectedFiliere() { return selectedFiliere; }
    public void setSelectedFiliere(String selectedFiliere) { this.selectedFiliere = selectedFiliere; }
    public String getSelectedPromotion() { return selectedPromotion; }
    public void setSelectedPromotion(String selectedPromotion) { this.selectedPromotion = selectedPromotion; }
    public String getSelectedCentre() { return selectedCentre; }
    public void setSelectedCentre(String selectedCentre) { this.selectedCentre = selectedCentre; }

    @Init
    public void init() {
        loadDocuments();
        if (candidatViewModel != null) {
            candidatViewModel.init();
        }
    }
    
    @Command
    @NotifyChange("documents")
    public void loadDocuments() {
        this.documents = DocumentGestion.findAllDocuments();
        if (this.documents == null) this.documents = new ArrayList<>();
    }

    @Command
    public void goHome() {
        Executions.sendRedirect("/home");
    }

    @GlobalCommand
    @NotifyChange({"currentView", "currentPath", "navigationArgs"})
    public void navigateToGlobal(@BindingParam("view") String view, @BindingParam("label") String label, @BindingParam("section") String section, @BindingParam("page") String page, @BindingParam("parentModel") Object payload) {
        this.currentView = view;
        this.navigationArgs = (payload instanceof Map) ? (Map<String, Object>) payload : new HashMap<>();
        this.currentPath = (section != null && !section.trim().isEmpty() && page != null && !page.trim().isEmpty()) ? section + " \u203A " + page : (label != null ? label : "");
    }
    
    @Command
    @NotifyChange({"currentView", "currentPath"})
    public void navigateTo(@BindingParam("view") String view, @BindingParam("label") String label, @BindingParam("section") String section, @BindingParam("page") String page) {
        if (view == null || view.trim().isEmpty()) return;
        this.currentView = view;
        this.currentPath = (section != null && !section.trim().isEmpty() && page != null && !page.trim().isEmpty()) ? section + " \u203A " + page : (label != null ? label : "");
    }

    @Command
    @NotifyChange("concoursExpanded")
    public void toggleConcoursSection() {
        concoursExpanded = !concoursExpanded;
    }
    
    @Command
    @NotifyChange({"suggestions", "searchResults", "filteredSearchResults", "availableConcoursFilters", "availableDocTypeFilters", "availableFilieres", "availablePromotions", "availableCentres"})
    public void updateDynamicSearch() {
        if (searchQuery == null || searchQuery.trim().length() < 2) {
            suggestions.clear();
            if (searchBox != null) searchBox.close();
            return;
        }

        this.suggestions = luceneService.getSuggestions(searchQuery);
        if (searchBox != null) {
            if (suggestions.isEmpty()) searchBox.close();
            else searchBox.open();
        }

        this.searchResults = luceneService.search(searchQuery);
        populateFilters();
        applyFilters();
    }
    
    @Command
    @NotifyChange({"searchQuery", "suggestions"})
    public void selectSuggestion(@BindingParam("suggestion") SearchResult result) {
        this.searchQuery = result.getCandidateFullName();
        this.suggestions.clear();
        if (searchBox != null) {
            searchBox.close();
        }
        searchDocuments();
    }

    @Command
    @NotifyChange({"searchResults", "filteredSearchResults", "currentView", "currentPath", "suggestions", "availableConcoursFilters", "availableDocTypeFilters", "availableFilieres", "availablePromotions", "availableCentres", "selectedConcoursFilter", "selectedDocTypeFilter", "selectedFiliere", "selectedPromotion", "selectedCentre"})
    public void searchDocuments() {
        if (searchBox != null) searchBox.close();
        this.suggestions.clear();
        
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            goHomeSearch();
            return;
        }
        
        this.searchResults = luceneService.search(searchQuery);
        populateFilters();
        applyFilters();
        
        navigateToGlobal("/documents/views/shared/search-results.zul", "Résultats de la recherche", null, null, null);
    }
    
    @Command
    @NotifyChange({"searchResults", "filteredSearchResults", "currentView", "currentPath"})
    public void goHomeSearch() {
        this.searchResults.clear();
        this.filteredSearchResults.clear();
        navigateToGlobal("/documents/views/dashboard.zul", "Mes Documents", null, null, null);
    }
    
    private void populateFilters() {
        this.availableConcoursFilters = extractDistinctValues(SearchResult::getConcoursDisplayInfo);
        this.availableDocTypeFilters = extractDistinctValues(SearchResult::getDocTypeName);
        this.availableFilieres = extractDistinctValues(SearchResult::getFiliere);
        this.availablePromotions = extractDistinctValues(SearchResult::getPromotion);
        this.availableCentres = extractDistinctValues(SearchResult::getCentreExamen);
    }

    private List<String> extractDistinctValues(java.util.function.Function<SearchResult, String> extractor) {
        return searchResults.stream()
            .map(extractor)
            .filter(s -> s != null && !s.isEmpty())
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    @Command
    @NotifyChange("filteredSearchResults")
    public void applyFilters() {
        this.filteredSearchResults = this.searchResults.stream()
            .filter(result -> {
                boolean concoursMatch = selectedConcoursFilter == null || selectedConcoursFilter.isEmpty() || selectedConcoursFilter.equals(result.getConcoursDisplayInfo());
                boolean docTypeMatch = selectedDocTypeFilter == null || selectedDocTypeFilter.isEmpty() || selectedDocTypeFilter.equals(result.getDocTypeName());
                boolean filiereMatch = selectedFiliere == null || selectedFiliere.isEmpty() || selectedFiliere.equals(result.getFiliere());
                boolean promotionMatch = selectedPromotion == null || selectedPromotion.isEmpty() || selectedPromotion.equals(result.getPromotion());
                boolean centreMatch = selectedCentre == null || selectedCentre.isEmpty() || selectedCentre.equals(result.getCentreExamen());
                return concoursMatch && docTypeMatch && filiereMatch && promotionMatch && centreMatch;
            })
            .collect(Collectors.toList());
    }
    
    @Command
    @NotifyChange({"filteredSearchResults", "selectedConcoursFilter", "selectedDocTypeFilter", "selectedFiliere", "selectedPromotion", "selectedCentre"})
    public void clearFilters() {
        this.selectedConcoursFilter = null;
        this.selectedDocTypeFilter = null;
        this.selectedFiliere = null;
        this.selectedPromotion = null;
        this.selectedCentre = null;
        applyFilters(); 
    }
    
    @Command @NotifyChange({"selectedConcoursFilter", "filteredSearchResults"}) public void removeConcoursFilter() { this.selectedConcoursFilter = null; applyFilters(); }
    @Command @NotifyChange({"selectedDocTypeFilter", "filteredSearchResults"}) public void removeDocTypeFilter() { this.selectedDocTypeFilter = null; applyFilters(); }
    @Command @NotifyChange({"selectedFiliere", "filteredSearchResults"}) public void removeFiliereFilter() { this.selectedFiliere = null; applyFilters(); }
    @Command @NotifyChange({"selectedPromotion", "filteredSearchResults"}) public void removePromotionFilter() { this.selectedPromotion = null; applyFilters(); }
    @Command @NotifyChange({"selectedCentre", "filteredSearchResults"}) public void removeCentreFilter() { this.selectedCentre = null; applyFilters(); }
    @Command @NotifyChange("candidatViewModel") public void newCandidat() { candidatViewModel.newCandidat(); }
    @Command @NotifyChange("candidatViewModel") public void editCandidat(@BindingParam("candidat") Candidat c) { candidatViewModel.editCandidat(c); }
    @Command @NotifyChange("candidatViewModel") public void saveCandidat() { candidatViewModel.save(); }
    @Command @NotifyChange("candidatViewModel") public void cancelCandidat() { candidatViewModel.cancel(); }
    @Command @NotifyChange("candidatViewModel") public void deleteCandidat(@BindingParam("id") Integer id) { candidatViewModel.delete(id); }
    @Command @NotifyChange("candidatViewModel") public void viewCandidat(@BindingParam("candidat") Candidat c) { candidatViewModel.viewCandidat(c); }
    @Command @NotifyChange("candidatViewModel") public void refreshCandidats() { candidatViewModel.refresh(); }
    @Command @NotifyChange("candidatViewModel") public void searchCandidats() { candidatViewModel.search(); }
    @Command @NotifyChange("candidatViewModel") public void onConcoursChange(@BindingParam("self") org.zkoss.zul.Combobox c) { candidatViewModel.onConcoursChange(c); }
    @Command @NotifyChange("candidatViewModel") public void onCentreExamenChange(@BindingParam("self") org.zkoss.zul.Combobox c) { candidatViewModel.onCentreExamenChange(c); }
}