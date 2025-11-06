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

    public static void deleteAndDeindex(Integer id) {
        luceneService.deleteDocument(id.longValue());
        getService().softDelete(id);
    }

    // --- Les autres méthodes restent inchangées ---
    public static List<ListeDossierConcoursCandidat> findAll() { return getService().getAllActive(); }
    public static ListeDossierConcoursCandidat findById(Integer id) { return getService().getById(id); }
    public static void save(ListeDossierConcoursCandidat e) { getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<ListeDossierConcoursCandidat> findByCandidat(Integer candidatId) { return getService().getByCandidat(candidatId); }
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
}