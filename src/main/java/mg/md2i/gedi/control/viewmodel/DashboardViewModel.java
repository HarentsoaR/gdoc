package mg.md2i.gedi.control.viewmodel;

import lombok.Getter;
import lombok.Setter;
import mg.md2i.gedi.entity.Document;
import mg.md2i.gedi.gestionmetier.DocumentGestion;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardViewModel {

    @Getter
    private List<Document> recentFolders = new ArrayList<>();

    @Getter
    private List<Document> recentFiles = new ArrayList<>();

    @Setter
    private boolean showRecentFolders = true;
    @Setter
    private boolean showRecentFiles = true;

    public boolean getShowRecentFolders() { return showRecentFolders; }
    public boolean getShowRecentFiles() { return showRecentFiles; }

    @Init
    public void init() {
        loadData();
    }

    // Actions forwarded to the main document VM via global commands
    @Command
    public void viewFile(@BindingParam("doc") Document file) {
        if (file != null) {
            java.util.Map<String, Object> args = new java.util.HashMap<>();
            args.put("doc", file);
            BindUtils.postGlobalCommand(null, null, "selectDocFromDashboard", args);
        }
    }

    @Command
    public void downloadFile(@BindingParam("doc") Document file) {
        if (file != null) {
            java.util.Map<String, Object> args = new java.util.HashMap<>();
            args.put("doc", file);
            BindUtils.postGlobalCommand(null, null, "downloadDocument", args);
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
    }

    @Command
    @NotifyChange({"recentFolders", "recentFiles"})
    public void loadData() {
        List<Document> all = DocumentGestion.findAllDocuments();
        split(all);
    }

    private void split(List<Document> documents) {
        recentFolders.clear();
        recentFiles.clear();
        for (Document d : documents) {
            String t = d.getType() != null ? d.getType().toLowerCase(Locale.ROOT) : "";
            if ("folder".equals(t)) {
                recentFolders.add(d);
            } else {
                recentFiles.add(d);
            }
        }
        if (recentFolders.isEmpty()) {
            recentFolders.add(sample("Classroom", "Dans Mon Drive", "folder"));
            recentFolders.add(sample("GetMyOS", "Dans Mon Drive", "folder"));
            recentFolders.add(sample("Partagés avec moi", "Accès rapide", "folder"));
        }
        if (recentFiles.isEmpty()) {
            recentFiles.add(sample("Sidebar Navigation Menu", "Google AI Studio", "text/plain"));
        }
        BindUtils.postNotifyChange(null, null, this, "recentFolders");
        BindUtils.postNotifyChange(null, null, this, "recentFiles");
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


