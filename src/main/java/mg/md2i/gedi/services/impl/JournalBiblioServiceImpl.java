package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.JournalBiblio;
import mg.md2i.gedi.repository.JournalBiblioRepository;
import mg.md2i.gedi.services.JournalBiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JournalBiblioServiceImpl implements JournalBiblioService {

    @Autowired
    private JournalBiblioRepository repository;

    @Override
    public List<JournalBiblio> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public JournalBiblio getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public JournalBiblio save(JournalBiblio entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<JournalBiblio> searchByTitre(String query) {
        return repository.findByTitreContainingIgnoreCase(query);
    }
}


