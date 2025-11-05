package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Filiere;

import java.util.List;

public interface FiliereService {
    List<Filiere> getAllActive();
    Filiere getById(Integer id);
    void save(Filiere filiere);
    void softDelete(Integer id);
    List<Filiere> search(String query);
}
