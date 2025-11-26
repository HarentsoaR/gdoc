package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import mg.md2i.gedi.services.LuceneService;
import mg.md2i.gedi.services.impl.LuceneServiceImpl;
import mg.md2i.gedi.services.ListeDossierConcoursCandidatService;

import java.util.List;

public class ListeDossierConcoursCandidatGestion {

    private static final LuceneService luceneService = new LuceneServiceImpl();

    private static ListeDossierConcoursCandidatService getService() {
        return ObjectFactory.getBean(ListeDossierConcoursCandidatService.class);
    }
    
    public static ListeDossierConcoursCandidat saveAndIndex(ListeDossierConcoursCandidat entity) {
        ListeDossierConcoursCandidat savedEntity = getService().save(entity);
        
        if (savedEntity != null && savedEntity.getListeDossierConcoursCandidatId() != null) {
            luceneService.indexDocument(savedEntity);
        }
        return savedEntity;
    }

    // NOUVELLE MÉTHODE (pour l'erreur 2)
    public static ListeDossierConcoursCandidat updateAndIndex(ListeDossierConcoursCandidat entity) {
        // La méthode save gère à la fois la création et la mise à jour, 
        // donc on peut réutiliser saveAndIndex
        return saveAndIndex(entity);
    }

    public static void deleteAndDeindex(Integer id) {
        luceneService.deleteDocument(id.longValue()); // Lucene utilise souvent des Long pour les ID
        getService().softDelete(id);
    }

    public static List<ListeDossierConcoursCandidat> findAll() { return getService().getAllActive(); }
    public static ListeDossierConcoursCandidat findById(Integer id) { return getService().getById(id); }
    public static void save(ListeDossierConcoursCandidat e) { getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }

    public static void moveToTrash(Integer id) { deleteAndDeindex(id); }

    public static void restore(Integer id) {
        getService().restore(id);
        ListeDossierConcoursCandidat entity = getService().getById(id);
        if (entity != null) {
            luceneService.indexDocument(entity);
        }
    }

    public static void hardDelete(Integer id) {
        if (id == null) return;
        // deindex first
        luceneService.deleteDocument(id.longValue());
        // delete associated file from storage if present
        try {
            ListeDossierConcoursCandidat entity = getService().getById(id);
            if (entity != null && entity.getRemarqueFacultatif() != null) {
                java.io.File f = new java.io.File(entity.getRemarqueFacultatif());
                if (f.exists()) {
                    f.delete();
                    // clean up empty parent directory
                    java.io.File parent = f.getParentFile();
                    if (parent != null && parent.isDirectory() && parent.list() != null && parent.list().length == 0) {
                        parent.delete();
                    }
                }
            }
        } catch (Exception ignored) {}
        getService().hardDelete(id);
    }
    
    // CORRECTION (pour l'erreur 3) : Renommage de findByCandidat en findByCandidatId
    public static List<ListeDossierConcoursCandidat> findByCandidatId(Integer candidatId) { 
        return getService().getByCandidat(candidatId); 
    }

    // NOUVELLE MÉTHODE (pour l'erreur 1)
    public static ListeDossierConcoursCandidat findByCandidatIdAndDocumentId(Integer candidatId, Integer documentConcoursId) {
        return getService().getByCandidatAndDocumentType(candidatId, documentConcoursId).orElse(null);
    }

    public static List<ListeDossierConcoursCandidat> findByDocumentConcours(Integer documentConcoursId) { return getService().getByDocumentConcours(documentConcoursId); }
    public static List<ListeDossierConcoursCandidat> findByFilters(Integer documentConcoursId, Integer candidatId) {
        return getService().getByFilters(documentConcoursId, candidatId);
    }
    public static List<ListeDossierConcoursCandidat> findWithAdvancedFilters(Integer documentConcoursId, Integer concoursId, Integer centreId, String nomCandidat) {
        String formattedNom = (nomCandidat == null || nomCandidat.trim().isEmpty())
                              ? null
                              : "%" + nomCandidat.trim().toLowerCase() + "%";
        return getService().findWithAdvancedFilters(documentConcoursId, concoursId, centreId, formattedNom);
    }

    public static List<ListeDossierConcoursCandidat> findDeleted() {
        return getService().getAllDeleted();
    }
}
