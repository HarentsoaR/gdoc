package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.DocumentConcours;
import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import mg.md2i.gedi.gestionmetier.ListeDossierConcoursCandidatGestion;
import mg.md2i.gedi.trash.TrashEntry;
import mg.md2i.gedi.trash.TrashManager;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CorbeilleViewModel {

    private List<TrashRow> documents;

    @Init
    public void init() {
        TrashManager.purgeExpired(ListeDossierConcoursCandidatGestion::hardDelete);
        loadDocuments();
    }

    private void loadDocuments() {
        List<ListeDossierConcoursCandidat> deleted = ListeDossierConcoursCandidatGestion.findDeleted();
        documents = deleted.stream()
                .map(doc -> {
                    TrashEntry entry = TrashManager.get(doc.getListeDossierConcoursCandidatId());
                    return new TrashRow(doc, entry);
                })
                .sorted(Comparator.comparingLong(TrashRow::getDeletedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<TrashRow> getDocuments() {
        return documents;
    }

    @Command
    @NotifyChange("documents")
    public void restore(@BindingParam("id") Integer id) {
        if (id == null) return;
        ListeDossierConcoursCandidatGestion.restore(id);
        TrashManager.remove(id);
        loadDocuments();
    }

    @Command
    @NotifyChange("documents")
    public void deleteForever(@BindingParam("id") Integer id) {
        if (id == null) return;
        ListeDossierConcoursCandidatGestion.hardDelete(id);
        TrashManager.remove(id);
        loadDocuments();
    }

    @Command
    @NotifyChange("documents")
    public void purgeExpired() {
        TrashManager.purgeExpired(ListeDossierConcoursCandidatGestion::hardDelete);
        loadDocuments();
    }

    public static class TrashRow {
        private final Integer id;
        private final String candidate;
        private final String document;
        private final String fileName;
        private final long deletedAt;
        private final String deletedAtLabel;

        public TrashRow(ListeDossierConcoursCandidat entity, TrashEntry entry) {
            this.id = entity.getListeDossierConcoursCandidatId();
            this.candidate = buildCandidate(entity, entry);
            this.document = Optional.ofNullable(entity.getDocumentConcours())
                    .map(DocumentConcours::getLibelle)
                    .orElse(entry != null ? entry.getDocumentLabel() : "Document");
            this.fileName = entry != null ? entry.getFileName() : "-";
            this.deletedAt = entry != null ? entry.getDeletedAt() : 0L;
            this.deletedAtLabel = entry != null ? entry.getDeletedAtLabel() : "-";
        }

        private String buildCandidate(ListeDossierConcoursCandidat entity, TrashEntry entry) {
            if (entity.getCandidat() != null) {
                String nom = Optional.ofNullable(entity.getCandidat().getNom()).orElse("");
                String prenom = Optional.ofNullable(entity.getCandidat().getPrenom()).orElse("");
                String result = (nom + " " + prenom).trim();
                if (!result.isEmpty()) {
                    return result;
                }
            }
            if (entry != null && entry.getCandidatName() != null && !entry.getCandidatName().trim().isEmpty()) {
                return entry.getCandidatName();
            }
            return "Candidat #" + entity.getCandidatId();
        }

        public Integer getId() { return id; }
        public String getCandidate() { return candidate; }
        public String getDocument() { return document; }
        public String getFileName() { return fileName; }
        public long getDeletedAt() { return deletedAt; }
        public String getDeletedAtLabel() { return deletedAtLabel; }
    }
}