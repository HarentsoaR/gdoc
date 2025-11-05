package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.gestionmetier.DocumentConcoursGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentConcoursViewModel {

    private static final Logger log = LoggerFactory.getLogger(DocumentConcoursViewModel.class);

    private List<DocumentConcours> documentList; // The correct name is documentList
    private DocumentConcours currentDocument;
    private String searchQuery;

    @Init
    public void init(@ExecutionArgParam("documentToManage") DocumentConcours documentToManage) {
        if (documentToManage != null) {
            this.currentDocument = documentToManage;
            if (currentDocument.getDocumentConcoursId() != null) {
                log.info("[DocConcoursVM] Edit mode for id={}", currentDocument.getDocumentConcoursId());
                this.currentDocument = DocumentConcoursGestion.findById(currentDocument.getDocumentConcoursId());
            } else {
                log.info("[DocConcoursVM] New mode initialized");
            }
        } else {
            log.info("[DocConcoursVM] List mode => load all");
            loadDocumentList();
        }

        if (this.currentDocument == null) {
            this.currentDocument = new DocumentConcours();
            log.info("[DocConcoursVM] Safety init => new empty currentDocument created");
        }
    }

    @GlobalCommand
    @NotifyChange("documentList")
    public void refreshDocumentList() {
        loadDocumentList();
    }

    @Command
    public void addDocument() {
        Map<String, Object> args = new HashMap<>();
        args.put("documentToManage", new DocumentConcours());
        Window window = (Window) Executions.createComponents("/documents/views/concours/documents-concours/new.zul", null, args);
        window.doModal();
    }

    @Command
    public void edit(@BindingParam("doc") DocumentConcours docToEdit) {
        Map<String, Object> args = new HashMap<>();
        args.put("documentToManage", docToEdit);
        Window window = (Window) Executions.createComponents("/documents/views/concours/documents-concours/edit.zul", null, args);
        window.doModal();
    }

    @Command
    public void saveDocument(@ContextParam(ContextType.VIEW) Component view) {
        DocumentConcoursGestion.save(currentDocument);
        Messagebox.show("Document sauvegardé avec succès!", "Succès", Messagebox.OK, Messagebox.INFORMATION);

        BindUtils.postGlobalCommand(null, null, "refreshDocumentList", null);
        view.detach();
    }

    @Command
    public void cancel(@ContextParam(ContextType.VIEW) Component view) {
        view.detach();
    }

    @Command
    public void deleteDocument(@BindingParam("id") Integer id) {
        Messagebox.show("Êtes-vous sûr de vouloir supprimer ce document ?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        DocumentConcoursGestion.delete(id);
                        refreshDocumentList();
                        Messagebox.show("Document supprimé avec succès.", "Succès", Messagebox.OK, Messagebox.INFORMATION);
                    }
                });
    }

    @Command
    @NotifyChange("documentList")
    public void search() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadDocumentList();
        } else {
            documentList = DocumentConcoursGestion.search(searchQuery);
        }
    }

    private void loadDocumentList() {
        documentList = DocumentConcoursGestion.findAll();
    }

    // --- Getters & Setters ---
    public List<DocumentConcours> getDocumentList() { return documentList; }
    public DocumentConcours getCurrentDocument() { return currentDocument; }
    public void setCurrentDocument(DocumentConcours d) { this.currentDocument = d; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String q) { this.searchQuery = q; }
}