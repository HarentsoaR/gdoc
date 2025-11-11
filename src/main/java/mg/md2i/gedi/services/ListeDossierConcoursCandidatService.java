package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import java.util.List;
import java.util.Optional; // Importez Optional

public interface ListeDossierConcoursCandidatService {
    
    List<ListeDossierConcoursCandidat> getAllActive();
    
    ListeDossierConcoursCandidat getById(Integer id);
    
    ListeDossierConcoursCandidat save(ListeDossierConcoursCandidat entity);
    
    void softDelete(Integer id);
    
    List<ListeDossierConcoursCandidat> getByCandidat(Integer candidatId);
    
    // NOUVELLE MÉTHODE
    Optional<ListeDossierConcoursCandidat> getByCandidatAndDocumentType(Integer candidatId, Integer documentConcoursId);
    
    List<ListeDossierConcoursCandidat> getByDocumentConcours(Integer documentConcoursId);
    
    List<ListeDossierConcoursCandidat> getByFilters(Integer documentConcoursId, Integer candidatId);

    List<ListeDossierConcoursCandidat> findWithAdvancedFilters(Integer documentConcoursId, Integer concoursId, Integer centreId, String nomCandidat);
}