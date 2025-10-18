package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ConcoursPhase;
import java.util.List;

public interface ConcoursPhaseService {
    List<ConcoursPhase> getAllActive();
    ConcoursPhase getById(Integer id);
    void save(ConcoursPhase concoursPhase);
    void softDelete(Integer id);
    List<ConcoursPhase> search(String query);
    List<ConcoursPhase> getByConcours(Integer concoursId);
}


