package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import java.util.List;

public interface ListeDossierConcoursCandidatService {
    // ... tes méthodes existantes
    List<ListeDossierConcoursCandidat> getAllActive();
    ListeDossierConcoursCandidat getById(Integer id);
    void save(ListeDossierConcoursCandidat entity);
    void softDelete(Integer id);
    List<ListeDossierConcoursCandidat> getByCandidat(Integer candidatId);
    List<ListeDossierConcoursCandidat> getByDocumentConcours(Integer documentConcoursId);
    List<ListeDossierConcoursCandidat> getByFilters(Integer documentConcoursId, Integer candidatId);

    // NOUVELLE MÉTHODE À AJOUTER
    /**
     * Recherche les documents avec des filtres avancés sur le concours, le centre et le nom du candidat.
     * @param documentConcoursId ID du type de document (peut être null)
     * @param concoursId ID du concours (peut être null)
     * @param centreId ID du centre d'examen (peut être null)
     * @param nomCandidat Terme de recherche pour le nom/prénom du candidat (peut être null)
     * @return Une liste de documents correspondants.
     */
    List<ListeDossierConcoursCandidat> findWithAdvancedFilters(Integer documentConcoursId, Integer concoursId, Integer centreId, String nomCandidat);
}