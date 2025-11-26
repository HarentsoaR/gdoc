package mg.md2i.gedi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Helper for building ZIP archives for dossier exports.
 */
public final class ArchiveUtils {

    private ArchiveUtils() {
    }

    public static File buildArchive(String baseName, Collection<ArchiveEntry> entries) throws IOException {
        if (entries == null || entries.isEmpty()) {
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String archiveName = sanitizeFileName(baseName != null ? baseName : "archive") + "_" + timestamp + ".zip";

        File temp = File.createTempFile("gedi_export_", ".zip");
        File target = new File(temp.getParentFile(), archiveName);
        if (target.exists() && !target.delete()) {
            throw new IOException("Impossible d'initialiser l'archive " + target.getAbsolutePath());
        }

        Set<String> usedNames = new HashSet<>();
        int added = 0;

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(target))) {
            byte[] buffer = new byte[8192];

            for (ArchiveEntry entry : entries) {
                if (entry == null || entry.getFile() == null || !entry.getFile().exists() || !entry.getFile().isFile()) {
                    continue;
                }

                String baseEntryName = sanitizeFileName(entry.getLabel() != null ? entry.getLabel() : entry.getFile().getName());
                String extension = getFileExtension(entry.getFile().getName());
                String fileName = baseEntryName + extension;

                String folder = entry.getFolder() != null && !entry.getFolder().trim().isEmpty()
                        ? sanitizeFileName(entry.getFolder().trim()) + "/"
                        : "";

                String finalName = folder + fileName;
                int counter = 1;
                while (usedNames.contains(finalName)) {
                    finalName = folder + baseEntryName + "_" + counter + extension;
                    counter++;
                }

                usedNames.add(finalName);
                zos.putNextEntry(new ZipEntry(finalName));
                try (FileInputStream fis = new FileInputStream(entry.getFile())) {
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                zos.closeEntry();
                added++;
            }
        }

        if (added == 0) {
            target.delete();
            return null;
        }

        return target;
    }

    public static String sanitizeFileName(String name) {
        if (name == null) {
            return "Unknown";
        }
        String sanitized = name.replaceAll("[^a-zA-Z0-9._-]", "_").replaceAll("_{2,}", "_").trim();
        if (sanitized.isEmpty()) {
            return "Unknown";
        }
        return sanitized;
    }

    private static String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) return "";
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0 && lastDot < filename.length() - 1) {
            return filename.substring(lastDot);
        }
        return "";
    }

    public static class ArchiveEntry {
        private final String label;
        private final File file;
        private final String folder;

        public ArchiveEntry(String label, File file) {
            this(label, file, null);
        }

        public ArchiveEntry(String label, File file, String folder) {
            this.label = label;
            this.file = file;
            this.folder = folder;
        }

        public String getLabel() {
            return label;
        }

        public File getFile() {
            return file;
        }

        public String getFolder() {
            return folder;
        }
    }
}
