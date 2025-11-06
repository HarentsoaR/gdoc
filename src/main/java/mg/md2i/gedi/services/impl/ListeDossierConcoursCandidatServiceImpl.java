package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import mg.md2i.gedi.repository.ListeDossierConcoursCandidatRepository;
import mg.md2i.gedi.services.ListeDossierConcoursCandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListeDossierConcoursCandidatServiceImpl implements ListeDossierConcoursCandidatService {

    @Autowired
    private ListeDossierConcoursCandidatRepository repository;

    @Override
    public List<ListeDossierConcoursCandidat> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public ListeDossierConcoursCandidat getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ListeDossierConcoursCandidat save(ListeDossierConcoursCandidat entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> {
            e.setActif(0);
            repository.save(e);
        });
    }

    @Override
    public List<ListeDossierConcoursCandidat> getByCandidat(Integer candidatId) {
        return repository.findByCandidatId(candidatId);
    }

    @Override
    public List<ListeDossierConcoursCandidat> getByDocumentConcours(Integer documentConcoursId) {
        return repository.findByDocumentConcoursId(documentConcoursId);
    }

    @Override
    public List<ListeDossierConcoursCandidat> getByFilters(Integer documentConcoursId, Integer candidatId) {
        return repository.findByFilters(documentConcoursId, candidatId);
    }
    
    @Override
    public List<ListeDossierConcoursCandidat> findWithAdvancedFilters(Integer documentConcoursId, Integer concoursId, Integer centreId, String nomCandidat) {
        return repository.findWithAdvancedFilters(documentConcoursId, concoursId, centreId, nomCandidat);
    }
}