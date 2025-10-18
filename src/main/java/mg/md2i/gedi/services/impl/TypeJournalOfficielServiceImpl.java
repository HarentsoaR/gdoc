package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.TypeJournalOfficiel;
import mg.md2i.gedi.repository.TypeJournalOfficielRepository;
import mg.md2i.gedi.services.TypeJournalOfficielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeJournalOfficielServiceImpl implements TypeJournalOfficielService {

    @Autowired
    private TypeJournalOfficielRepository repository;

    @Override
    public List<TypeJournalOfficiel> getAll() {
        return repository.findAll();
    }

    @Override
    public TypeJournalOfficiel getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TypeJournalOfficiel save(TypeJournalOfficiel entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<TypeJournalOfficiel> searchByTitre(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


