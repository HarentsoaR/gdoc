package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.ListeDossierConcours;
import java.util.List;

public interface ListeDossierConcoursService {
    List<ListeDossierConcours> getAllActive();
    ListeDossierConcours getById(Integer id);
    void save(ListeDossierConcours entity);
    void softDelete(Integer id);
    List<ListeDossierConcours> getByConcours(Integer concoursId);
    List<ListeDossierConcours> searchByNom(String query);
}


