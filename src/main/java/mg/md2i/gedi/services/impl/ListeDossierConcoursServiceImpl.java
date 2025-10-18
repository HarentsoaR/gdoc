package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.ListeDossierConcours;
import mg.md2i.gedi.repository.ListeDossierConcoursRepository;
import mg.md2i.gedi.services.ListeDossierConcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListeDossierConcoursServiceImpl implements ListeDossierConcoursService {

    @Autowired
    private ListeDossierConcoursRepository repository;

    @Override
    public List<ListeDossierConcours> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public ListeDossierConcours getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(ListeDossierConcours entity) {
        repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        ListeDossierConcours e = repository.findById(id).orElse(null);
        if (e != null) {
            e.setActif(0);
            repository.save(e);
        }
    }

    @Override
    public List<ListeDossierConcours> getByConcours(Integer concoursId) {
        return repository.findByConcoursId(concoursId);
    }

    @Override
    public List<ListeDossierConcours> searchByNom(String query) {
        return repository.findByNomDossierContainingIgnoreCase(query);
    }
}


