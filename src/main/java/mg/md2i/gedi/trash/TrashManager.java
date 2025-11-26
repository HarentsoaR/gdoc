package mg.md2i.gedi.trash;

import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.Base64;
import java.util.function.Consumer;

/**
 * Simple JSON-based persistence for trash metadata.
 * This avoids altering the database schema while still tracking deletion dates.
 */
public final class TrashManager {

    private static final Path STORE = Paths.get("data", "trash-documents.json");
    private static final Map<Integer, TrashEntry> CACHE = new LinkedHashMap<>();
    private static final Duration RETENTION = Duration.ofDays(20);

    static {
        load();
    }

    private TrashManager() {}

    private static void load() {
        try {
            if (!Files.exists(STORE)) {
                Files.createDirectories(STORE.getParent());
                Files.write(STORE, "[]".getBytes(StandardCharsets.UTF_8));
            }
            List<String> lines = Files.readAllLines(STORE, StandardCharsets.UTF_8);
            CACHE.clear();
            for (String line : lines) {
                if (isBlank(line)) continue;
                TrashEntry entry = parse(line);
                if (entry != null) {
                    CACHE.put(entry.getDocumentId(), entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void persist() {
        try {
            Files.createDirectories(STORE.getParent());
            List<String> lines = new ArrayList<>();
            for (TrashEntry entry : CACHE.values()) {
                lines.add(serialize(entry));
            }
            Files.write(STORE, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String serialize(TrashEntry entry) {
        return entry.getDocumentId() + "|" +
                encode(entry.getDocumentLabel()) + "|" +
                encode(entry.getCandidatName()) + "|" +
                encode(entry.getFileName()) + "|" +
                entry.getDeletedAt() + "|" +
                encode(entry.getType());
    }

    private static TrashEntry parse(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 5) return null;
            Integer id = Integer.parseInt(parts[0]);
            String docLabel = decode(parts[1]);
            String candidat = decode(parts[2]);
            String fileName = decode(parts[3]);
            long deletedAt = Long.parseLong(parts[4]);
            String type = parts.length > 5 ? decode(parts[5]) : "document";
            TrashEntry entry = new TrashEntry(id, docLabel, candidat, fileName, deletedAt);
            entry.setType(type);
            return entry;
        } catch (Exception e) {
            return null;
        }
    }

    private static String encode(String value) {
        if (value == null) value = "";
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String encoded) {
        if (isBlank(encoded)) return "";
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static synchronized void registerDocument(ListeDossierConcoursCandidat doc, String documentLabel) {
        if (doc == null || doc.getListeDossierConcoursCandidatId() == null) {
            return;
        }
        Candidat candidat = doc.getCandidat();
        String candidatName = candidat != null ? String.format("%s %s",
                Optional.ofNullable(candidat.getNom()).orElse(""),
                Optional.ofNullable(candidat.getPrenom()).orElse("")).trim() : "Candidat inconnu";
        String filePath = doc.getRemarqueFacultatif();
        String fileName = "-";
        if (filePath != null) {
            int idx = filePath.lastIndexOf(File.separator);
            fileName = idx >= 0 ? filePath.substring(idx + 1) : filePath;
        }

        TrashEntry entry = new TrashEntry(
                doc.getListeDossierConcoursCandidatId(),
                documentLabel,
                candidatName,
                fileName,
                System.currentTimeMillis()
        );
        CACHE.put(entry.getDocumentId(), entry);
        persist();
    }

    public static synchronized TrashEntry get(Integer id) {
        return id == null ? null : CACHE.get(id);
    }

    public static synchronized List<TrashEntry> getAll() {
        return new ArrayList<>(CACHE.values());
    }

    public static synchronized void remove(Integer id) {
        if (id == null) return;
        CACHE.remove(id);
        persist();
    }

    /**
    * Remove a document from cache and optionally trigger a permanent deletion callback.
    */
    public static synchronized void remove(Integer id, Consumer<Integer> permanentDelete) {
        if (id == null) return;
        if (permanentDelete != null) {
            permanentDelete.accept(id);
        }
        CACHE.remove(id);
        persist();
    }

    /**
     * Purges entries older than the retention duration.
     * @param permanentDelete action to execute for each purged document id.
     */
    public static synchronized void purgeExpired(Consumer<Integer> permanentDelete) {
        if (permanentDelete == null) return;
        List<Integer> toRemove = new ArrayList<>();
        long now = System.currentTimeMillis();
        for (TrashEntry entry : CACHE.values()) {
            if (entry.getDeletedAt() <= now - RETENTION.toMillis()) {
                toRemove.add(entry.getDocumentId());
            }
        }
        for (Integer id : toRemove) {
            permanentDelete.accept(id);
            CACHE.remove(id);
        }
        if (!toRemove.isEmpty()) {
            persist();
        }
    }
}
