package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import java.util.List;

public interface ListeDossierConcoursCandidatService {
    
    List<ListeDossierConcoursCandidat> getAllActive();
    
    ListeDossierConcoursCandidat getById(Integer id);
    
    // CORRECTION : La méthode save retourne maintenant l'entité sauvegardée
    ListeDossierConcoursCandidat save(ListeDossierConcoursCandidat entity);
    
    void softDelete(Integer id);
    
    List<ListeDossierConcoursCandidat> getByCandidat(Integer candidatId);
    
    List<ListeDossierConcoursCandidat> getByDocumentConcours(Integer documentConcoursId);
    
    List<ListeDossierConcoursCandidat> getByFilters(Integer documentConcoursId, Integer candidatId);

    List<ListeDossierConcoursCandidat> findWithAdvancedFilters(Integer documentConcoursId, Integer concoursId, Integer centreId, String nomCandidat);
}