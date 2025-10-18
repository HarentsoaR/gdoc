package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.RapportStageBiblio;
import java.util.List;

public interface RapportStageBiblioService {
    List<RapportStageBiblio> getAllActive();
    RapportStageBiblio getById(Integer id);
    RapportStageBiblio save(RapportStageBiblio entity);
    void softDelete(Integer id);
    List<RapportStageBiblio> search(String query);
}


