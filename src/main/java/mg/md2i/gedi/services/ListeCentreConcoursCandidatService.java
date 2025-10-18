package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ListeCentreConcoursCandidat;
import java.util.List;

public interface ListeCentreConcoursCandidatService {
    List<ListeCentreConcoursCandidat> getAllActive();
    ListeCentreConcoursCandidat getById(Integer id);
    void save(ListeCentreConcoursCandidat entity);
    void softDelete(Integer id);
    List<ListeCentreConcoursCandidat> getByConcoursPhase(Integer concoursPhaseId);
    List<ListeCentreConcoursCandidat> getByCentreConcours(Integer centreConcoursId);
    List<ListeCentreConcoursCandidat> getByCandidat(Integer candidatId);
}


