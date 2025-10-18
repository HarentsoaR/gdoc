package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Candidat;
import java.util.List;

public interface CandidatService {
    List<Candidat> getAllActive();
    Candidat getById(Integer id);
    void save(Candidat entity);
    void softDelete(Integer id);
    List<Candidat> searchByNom(String query);
    List<Candidat> getByConcours(Integer concoursId);
}


