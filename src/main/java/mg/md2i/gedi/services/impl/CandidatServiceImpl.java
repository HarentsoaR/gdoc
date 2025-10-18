package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Candidat;
import mg.md2i.gedi.repository.CandidatRepository;
import mg.md2i.gedi.services.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatServiceImpl implements CandidatService {

    @Autowired
    private CandidatRepository repository;

    @Override
    public List<Candidat> getAllActive() {
        // FIX: Call the method that eagerly fetches relations.
        return repository.findAllWithRelations();
    }

    @Override
    public List<Candidat> searchByNom(String query) {
        // FIX: Call the NEW search method that also eagerly fetches relations.
        return repository.searchByNomWithRelations(query);
    }

    @Override
    public Candidat getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Candidat entity) {
        repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        Candidat c = repository.findById(id).orElse(null);
        if (c != null) {
            c.setActif(0);
            repository.save(c);
        }
    }

    @Override
    public List<Candidat> getByConcours(Integer concoursId) {
        return repository.findByConcoursId(concoursId);
    }
}