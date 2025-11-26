package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.dto.SearchResult;
import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.gestionmetier.DocumentGestion;
import mg.md2i.gedi.services.LuceneService;
import mg.md2i.gedi.services.impl.LuceneServiceImpl;
import mg.md2i.gedi.util.RoleUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DocumentViewModel {
    
    private LuceneService luceneService = new LuceneServiceImpl();

    private List<Document> documents = new ArrayList<>();
    private Map<String, Object> navigationArgs;
    private CandidatViewModel candidatViewModel = new CandidatViewModel();
    private boolean concoursExpanded = false;
    private boolean navigationExpanded = true;
    private boolean fluxExpanded = true;
    private boolean canValidateDossiers = false;
    private boolean canSeeSuiviGlobal = false;
    private boolean canManageReferentiels = false;
    private boolean canImportDocuments = false;
    
    private List<SearchResult> searchResults = new ArrayList<>();
    private String searchQuery = "";
    private String currentPath = "Mes Documents";
    private String currentView = "/documents/views/dashboard.zul";
    private static final Map<String, String> VIEW_TO_PATH;
    static {
        Map<String, String> map = new HashMap<>();
        map.put("/documents/views/dashboard.zul", "/documents");
        map.put("/documents/views/concours/list.zul", "/documents/concours/list");
        map.put("/documents/views/concours/new.zul", "/documents/concours/new");
        map.put("/documents/views/concours/edit.zul", "/documents/concours/edit");
        map.put("/documents/views/concours/documents-concours/list.zul", "/documents/concours/documents");
        map.put("/documents/views/concours/documents-concours/new.zul", "/documents/concours/documents/new");
        map.put("/documents/views/concours/documents-concours/edit.zul", "/documents/concours/documents/edit");
        map.put("/documents/views/concours/suivi-dossiers.zul", "/documents/concours/suivi");
        map.put("/documents/views/concours/suivi-dossier-detail.zul", "/documents/concours/suivi/dossier");
        map.put("/documents/views/concours/validation-dossiers.zul", "/documents/concours/validation");
        map.put("/documents/views/concours/validation-documents.zul", "/documents/concours/validation/documents");
        map.put("/documents/views/concours/upload-candidat.zul", "/documents/concours/upload");
        map.put("/documents/views/filiere/list.zul", "/documents/filieres/list");
        map.put("/documents/views/filiere/new.zul", "/documents/filieres/new");
        map.put("/documents/views/filiere/edit.zul", "/documents/filieres/edit");
        map.put("/documents/views/candidats/list.zul", "/documents/candidats/list");
        map.put("/documents/views/candidats/new.zul", "/documents/candidats/new");
        map.put("/documents/views/candidats/edit.zul", "/documents/candidats/edit");
        map.put("/documents/views/candidats/dossiers.zul", "/documents/candidats/dossiers");
        map.put("/documents/views/candidats/corbeille.zul", "/documents/candidats/corbeille");
        VIEW_TO_PATH = Collections.unmodifiableMap(map);
    }

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

    public List<Document> getDocuments() { return documents; }
    public void setDocuments(List<Document> documents) { this.documents = documents; }
    public List<SearchResult> getSearchResults() { return searchResults; }
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
    public boolean isNavigationExpanded() { return navigationExpanded; }
    public void setNavigationExpanded(boolean navigationExpanded) { this.navigationExpanded = navigationExpanded; }
    public boolean isFluxExpanded() { return fluxExpanded; }
    public void setFluxExpanded(boolean fluxExpanded) { this.fluxExpanded = fluxExpanded; }
    public boolean isCanValidateDossiers() { return canValidateDossiers; }
    public boolean isCanSeeSuiviGlobal() { return canSeeSuiviGlobal; }
    public boolean isCanManageReferentiels() { return canManageReferentiels; }
    public boolean isCanImportDocuments() { return canImportDocuments; }
    
    @DependsOn("currentView")
    public String getActiveView() {
        return currentView;
    }
    
    @DependsOn("currentView")
    public boolean isConcoursSectionActive() {
        return currentView != null && currentView.startsWith("/documents/views/concours");
    }

    @DependsOn({"searchResults", "searchQuery"})
    public String getSearchResultsHeadline() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return "Recherche avancée";
        }
        int count = searchResults != null ? searchResults.size() : 0;
        return count + " résultat(s) pour \"" + searchQuery + "\"";
    }

    @DependsOn("searchResults")
    public boolean isSearchResultEmpty() {
        return searchResults == null || searchResults.isEmpty();
    }

    @DependsOn({"searchResults", "searchQuery"})
    public String getSearchResultsHint() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return "Combinez des mots-clés et des critères comme concours:magistrats centre:Antananarivo";
        }
        return "Affinez encore avec concours:, filiere:, promotion:, centre:, etat:, numero:";
    }

    @Init
    public void init() {
        applyRolePermissions();
        loadDocuments();
        if (candidatViewModel != null) {
            candidatViewModel.init();
        }
        String viewParam = Executions.getCurrent().getParameter("view");
        if (viewParam != null && !viewParam.trim().isEmpty()) {
            this.currentView = viewParam;
            String sectionParam = Executions.getCurrent().getParameter("section");
            String pageParam = Executions.getCurrent().getParameter("page");
            if (sectionParam != null && !sectionParam.trim().isEmpty() &&
                    pageParam != null && !pageParam.trim().isEmpty()) {
                this.currentPath = sectionParam + " \u203A " + pageParam;
            } else if (pageParam != null && !pageParam.trim().isEmpty()) {
                this.currentPath = pageParam;
            } else {
                this.currentPath = "Mes Documents";
            }
            pushPrettyUrl(this.currentView);
        } else {
            pushPrettyUrl(this.currentView);
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
        pushPrettyUrl(view);
    }
    
    @Command
    @NotifyChange({"currentView", "currentPath"})
    public void navigateTo(@BindingParam("view") String view, @BindingParam("label") String label, @BindingParam("section") String section, @BindingParam("page") String page) {
        if (view == null || view.trim().isEmpty()) return;
        this.currentView = view;
        this.currentPath = (section != null && !section.trim().isEmpty() && page != null && !page.trim().isEmpty()) ? section + " \u203A " + page : (label != null ? label : "");
        pushPrettyUrl(view);
    }

    @Command
    @NotifyChange("concoursExpanded")
    public void toggleConcoursSection() {
        concoursExpanded = !concoursExpanded;
    }

    @Command
    @NotifyChange("navigationExpanded")
    public void toggleNavigationSection() {
        navigationExpanded = !navigationExpanded;
    }

    @Command
    @NotifyChange("fluxExpanded")
    public void toggleFluxSection() {
        fluxExpanded = !fluxExpanded;
    }

    private void pushPrettyUrl(String view) {
        String targetPath = VIEW_TO_PATH.getOrDefault(view, "/documents");
        String context = Executions.getCurrent().getContextPath();
        if (context == null) {
            context = "";
        }
        String fullPath = (context.endsWith("/") ? context.substring(0, context.length() - 1) : context) + targetPath;
        String sanitized = fullPath.replace("'", "\\'");
        Clients.evalJavaScript(String.format("window.history.replaceState(null,'','%s');", sanitized));
    }
    
    @Command
    @NotifyChange({"searchResults", "currentView", "currentPath", "searchResultsHeadline", "searchResultsHint"})
    public void searchDocuments() {
        String query = searchQuery != null ? searchQuery.trim() : "";
        if (query.isEmpty()) {
            goHomeSearch();
            return;
        }
        this.searchQuery = query;
        if (!refreshSearchResults(true)) {
            return;
        }
        navigateTo("/documents/views/shared/search-results.zul",
                "Résultats de la recherche",
                "Documents",
                "Recherche avancée");
    }

    @GlobalCommand("documentsSearchSubmit")
    @NotifyChange({"searchResults", "currentView", "currentPath", "searchResultsHeadline", "searchResultsHint"})
    public void documentsSearchSubmit() {
        searchDocuments();
    }
    
    @Command
    @NotifyChange({"searchResults", "currentView", "currentPath", "searchResultsHeadline", "searchResultsHint"})
    public void goHomeSearch() {
        this.searchQuery = "";
        this.searchResults.clear();
        navigateTo("/documents/views/dashboard.zul", "Mes Documents", null, null);
    }
    
    @Command
    @NotifyChange("searchQuery")
    public void handleSearchInput(@BindingParam("term") String rawTerm) {
        this.searchQuery = rawTerm != null ? rawTerm : "";
        if (!isSearchResultsView()) {
            return;
        }
        String trimmed = searchQuery.trim();
        if (trimmed.isEmpty()) {
            this.searchResults.clear();
        } else if (trimmed.length() >= 2) {
            refreshSearchResults(false);
        }
        BindUtils.postNotifyChange(null, null, this, "searchResults");
        BindUtils.postNotifyChange(null, null, this, "searchResultsHeadline");
        BindUtils.postNotifyChange(null, null, this, "searchResultsHint");
    }

    @GlobalCommand("documentsHandleSearchInput")
    @NotifyChange({"searchResults", "currentView", "currentPath", "searchResultsHeadline", "searchResultsHint"})
    public void handleSearchInputGlobal(@BindingParam("term") String rawTerm) {
        handleSearchInput(rawTerm);
    }
    
    @Command
    public void openSearchResult(@BindingParam("result") SearchResult result) {
        if (result == null || result.getCandidatId() == null) {
            Clients.showNotification("Impossible d'ouvrir ce dossier.", "warning", null, "top_center", 2000);
            return;
        }
        Executions.getCurrent().setAttribute("validationCandidateId", result.getCandidatId());
        Executions.getCurrent().setAttribute("validationCandidateName", result.getCandidateFullName());
        BindUtils.postGlobalCommand(null, null, "navigateToGlobal", new HashMap<String, Object>() {{
            put("view", "/documents/views/concours/validation-documents.zul");
            put("section", "Concours");
            put("page", "Validation dossiers \u203A Documents du candidat");
            put("label", "Documents du candidat");
        }});
    }
    
    private boolean refreshSearchResults(boolean warnIfShort) {
        String query = searchQuery != null ? searchQuery.trim() : "";
        if (query.length() < 2) {
            if (warnIfShort) {
                Clients.showNotification("Tapez au moins deux caractères pour lancer la recherche.", "info", null, "top_center", 2000);
            }
            return false;
        }
        List<SearchResult> results = luceneService.search(query);
        this.searchResults = results != null ? results : new ArrayList<>();
        return true;
    }

    private boolean isSearchResultsView() {
        return "/documents/views/shared/search-results.zul".equals(currentView);
    }

    @Command
    public void goToCandidatStep(@BindingParam("step") int step) {
        candidatViewModel.setCreationStep(step);
        BindUtils.postNotifyChange(null, null, candidatViewModel, "creationStep");
    }

    @Command
    public void updateCandidatConcours(@BindingParam("combobox") Combobox combobox) {
        candidatViewModel.onConcoursChange(combobox);
        BindUtils.postNotifyChange(null, null, candidatViewModel, "currentCandidat");
        BindUtils.postNotifyChange(null, null, candidatViewModel, "nextRangLabel");
    }

    @Command
    public void updateCandidatCentre(@BindingParam("combobox") Combobox combobox) {
        candidatViewModel.onCentreExamenChange(combobox);
        BindUtils.postNotifyChange(null, null, candidatViewModel, "currentCandidat");
    }

    
    @Command @NotifyChange("candidatViewModel") public void newCandidat() { candidatViewModel.newCandidat(); }
    @Command @NotifyChange("candidatViewModel") public void editCandidat(@BindingParam("candidat") Candidat c) { candidatViewModel.editCandidat(c); }
    @Command @NotifyChange("candidatViewModel") public void saveCandidat() { candidatViewModel.save(); }
    @Command @NotifyChange("candidatViewModel") public void cancelCandidat() { candidatViewModel.cancel(); }
    @Command @NotifyChange("candidatViewModel") public void deleteCandidat(@BindingParam("id") Integer id) { candidatViewModel.delete(id); }
    @Command @NotifyChange("candidatViewModel") public void viewCandidat(@BindingParam("candidat") Candidat c) { candidatViewModel.viewCandidat(c); }
    @Command @NotifyChange({"candidatViewModel.detailVisible", "candidatViewModel.detailCandidat", "candidatViewModel.detailSummary", "candidatViewModel.detailDocuments", "candidatViewModel.missingDocuments"})
    public void closeDetail() { candidatViewModel.closeDetail(); }
    @Command public void downloadDocument(@BindingParam("doc") CandidatViewModel.DocumentEntry doc) { candidatViewModel.downloadDocument(doc); }
    @Command @NotifyChange("candidatViewModel") public void refreshCandidats() { candidatViewModel.refresh(); }
    @Command @NotifyChange("candidatViewModel") public void searchCandidats() { candidatViewModel.search(); }
    @Command @NotifyChange("candidatViewModel") public void onConcoursChange(@BindingParam("self") org.zkoss.zul.Combobox c) { candidatViewModel.onConcoursChange(c); }
    @Command @NotifyChange("candidatViewModel") public void onCentreExamenChange(@BindingParam("self") org.zkoss.zul.Combobox c) { candidatViewModel.onCentreExamenChange(c); }
    @Command public void downloadCandidatDocumentsAsZip() { candidatViewModel.downloadCandidatDocumentsAsZip(); }
    
    @GlobalCommand("refreshCandidatsList")
    @NotifyChange("candidatViewModel")
    public void refreshCandidatsList() {
        if (candidatViewModel != null) {
            candidatViewModel.refreshCandidatsList();
        }
    }

    private void applyRolePermissions() {
        canValidateDossiers = RoleUtils.canValidateDossiers();
        canSeeSuiviGlobal = RoleUtils.canSeeSuiviGlobal();
        canManageReferentiels = RoleUtils.canManageReferentiels();
        canImportDocuments = RoleUtils.canImportDocuments();
    }
}
