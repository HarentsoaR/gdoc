package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import mg.md2i.gedi.services.ListeDossierConcoursCandidatService;
import java.util.List;

public class ListeDossierConcoursCandidatGestion {

    private static ListeDossierConcoursCandidatService getService() {
        return ObjectFactory.getBean(ListeDossierConcoursCandidatService.class);
    }

    // --- Méthodes existantes (inchangées) ---
    public static List<ListeDossierConcoursCandidat> findAll() { return getService().getAllActive(); }
    public static ListeDossierConcoursCandidat findById(Integer id) { return getService().getById(id); }
    public static void save(ListeDossierConcoursCandidat e) { getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<ListeDossierConcoursCandidat> findByCandidat(Integer candidatId) { return getService().getByCandidat(candidatId); }
    public static List<ListeDossierConcoursCandidat> findByDocumentConcours(Integer documentConcoursId) { return getService().getByDocumentConcours(documentConcoursId); }

    public static List<ListeDossierConcoursCandidat> findByFilters(Integer documentConcoursId, Integer candidatId) {
        return getService().getByFilters(documentConcoursId, candidatId);
    }

    // --- NOUVELLE MÉTHODE FINALE POUR LE VIEWMODEL ---
    /**
     * Appelle le service avec des filtres avancés.
     * Prépare le terme de recherche pour le nom du candidat avant de l'envoyer.
     */
    public static List<ListeDossierConcoursCandidat> findWithAdvancedFilters(Integer documentConcoursId, Integer concoursId, Integer centreId, String nomCandidat) {
        // Formate le nom pour une recherche LIKE (insensible à la casse, contenant le terme)
        String formattedNom = (nomCandidat == null || nomCandidat.trim().isEmpty())
                              ? null
                              : "%" + nomCandidat.trim().toLowerCase() + "%";

        return getService().findWithAdvancedFilters(documentConcoursId, concoursId, centreId, formattedNom);
    }
}