package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.TypeJournal;
import mg.md2i.gedi.repository.TypeJournalRepository;
import mg.md2i.gedi.services.TypeJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeJournalServiceImpl implements TypeJournalService {

    @Autowired
    private TypeJournalRepository repository;

    @Override
    public List<TypeJournal> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public TypeJournal getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TypeJournal save(TypeJournal entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<TypeJournal> searchByType(String query) {
        return repository.findByTypeJournalContainingIgnoreCase(query);
    }
}


