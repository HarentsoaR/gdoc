package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.JournalOfficiel;
import mg.md2i.gedi.repository.JournalOfficielRepository;
import mg.md2i.gedi.services.JournalOfficielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JournalOfficielServiceImpl implements JournalOfficielService {

    @Autowired
    private JournalOfficielRepository repository;

    @Override
    public List<JournalOfficiel> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public JournalOfficiel getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public JournalOfficiel save(JournalOfficiel entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }

    @Override
    public List<JournalOfficiel> findByNumero(Integer numero) {
        return repository.findByNumero(numero);
    }

    @Override
    public List<JournalOfficiel> findByDateRange(Date start, Date end) {
        return repository.findByDateBetween(start, end);
    }
}


