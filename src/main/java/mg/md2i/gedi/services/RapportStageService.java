package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.RapportStage;
import java.util.List;

public interface RapportStageService {
    List<RapportStage> getAllActive();
    RapportStage getById(Integer id);
    RapportStage save(RapportStage entity);
    void softDelete(Integer id);
    List<RapportStage> search(String query);
}


