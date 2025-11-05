package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.gestionmetier.DocumentConcoursGestion;
import mg.md2i.gedi.gestionmetier.DocumentGestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;

import java.util.*;

/**
 * Clean, Lombok-free version so ZK always finds getters.
 */
public class DashboardViewModel {

    private static final Logger log = LoggerFactory.getLogger(DashboardViewModel.class);

    private List<Document> recentFolders = new ArrayList<>();
    private List<Document> recentFiles = new ArrayList<>();
    private boolean showRecentFolders = true;
    private boolean showRecentFiles = true;

    // ---------------- Getters / Setters ----------------
    public List<Document> getRecentFolders() { return recentFolders; }
    public void setRecentFolders(List<Document> recentFolders) { this.recentFolders = recentFolders; }

    public List<Document> getRecentFiles() { return recentFiles; }
    public void setRecentFiles(List<Document> recentFiles) { this.recentFiles = recentFiles; }

    public boolean isShowRecentFolders() { return showRecentFolders; }
    public void setShowRecentFolders(boolean showRecentFolders) { this.showRecentFolders = showRecentFolders; }

    public boolean isShowRecentFiles() { return showRecentFiles; }
    public void setShowRecentFiles(boolean showRecentFiles) { this.showRecentFiles = showRecentFiles; }

    // ---------------- Lifecycle ----------------
    @Init
    public void init() {
        log.info("✅ Initialisation de DashboardViewModel...");
        loadData();
    }

    // ---------------- Commands ----------------
    @Command
    public void viewFile(@BindingParam("doc") Document file) {
        if (file != null) {
            Map<String, Object> args = new HashMap<>();
            args.put("doc", file);
            BindUtils.postGlobalCommand(null, null, "selectDocFromDashboard", args);
//            log.debug("📄 Fichier sélectionné : {}", file.getTitre());
        }
    }

    @Command
    public void downloadFile(@BindingParam("doc") Document file) {
        if (file != null) {
            Map<String, Object> args = new HashMap<>();
            args.put("doc", file);
            BindUtils.postGlobalCommand(null, null, "downloadDocument", args);
//            log.debug("⬇️ Téléchargement demandé : {}", file.getTitre());
        }
    }

    @Command
    @NotifyChange({"showRecentFolders", "showRecentFiles"})
    public void toggle(@BindingParam("key") String key) {
        if ("folders".equals(key)) {
            showRecentFolders = !showRecentFolders;
        } else if ("files".equals(key)) {
            showRecentFiles = !showRecentFiles;
        }
        log.debug("🔁 Toggle effectué : folders={}, files={}", showRecentFolders, showRecentFiles);
    }

    @Command
    @NotifyChange({"recentFolders", "recentFiles"})
    public void loadData() {
        log.info("Chargement des documents récents...");
//        List<Document> all = DocumentGestion.findAllDocuments();
//        if (all == null) all = new ArrayList<>();
        List<Document> all = new ArrayList<>();
        List<DocumentConcours> concoursDocs = DocumentConcoursGestion.findAll();
        if (concoursDocs != null) {
            for (DocumentConcours dc : concoursDocs) {
                all.add(convertToDocument(dc));
            }
        }
        split(all);
    }

    // ---------------- Helpers ----------------
    private Document convertToDocument(DocumentConcours dc) {
        Document doc = new Document();
        doc.setTitre(dc.getLibelle());
        doc.setEmplacement("Concours"); // Or another meaningful location
        doc.setType("file"); // Default type
        doc.setDateUpload(new Date()); // No date on DocumentConcours, using current
        doc.setActif(dc.getActif());
        doc.setVersion(dc.getVersion());
        doc.setRemarque(dc.getRemarque());
        // Set other fields to default values if needed
        return doc;
    }

    private void split(List<Document> documents) {
        recentFolders.clear();
        recentFiles.clear();

        for (Document d : documents) {
            String t = (d.getType() != null ? d.getType().toLowerCase(Locale.ROOT) : "");
            if ("folder".equals(t)) {
                recentFolders.add(d);
            } else {
                recentFiles.add(d);
            }
        }

        if (recentFolders.isEmpty()) {
//            recentFolders.add(sample("Classroom", "Dans Mon Drive", "folder"));
//            recentFolders.add(sample("GetMyOS", "Dans Mon Drive", "folder"));
//            recentFolders.add(sample("Partagés avec moi", "Accès rapide", "folder"));
        }

        if (recentFiles.isEmpty()) {
//            recentFiles.add(sample("Sidebar Navigation Menu", "Google AI Studio", "text/plain"));
        }

        BindUtils.postNotifyChange(null, null, this, "recentFolders");
        BindUtils.postNotifyChange(null, null, this, "recentFiles");
        log.info("📂 {} dossiers | 📄 {} fichiers", recentFolders.size(), recentFiles.size());
    }

    private Document sample(String title, String location, String type) {
        Document d = new Document();
        d.setTitre(title);
        d.setEmplacement(location);
        d.setType(type);
        d.setDateUpload(new Date());
        d.setActif(1);
        d.setVersion(1);
        return d;
    }
}
