package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.InventaireBiblio;
import mg.md2i.gedi.repository.InventaireBiblioRepository;
import mg.md2i.gedi.services.InventaireBiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventaireBiblioServiceImpl implements InventaireBiblioService {

    @Autowired
    private InventaireBiblioRepository repository;

    @Override
    public List<InventaireBiblio> getAllActive() {
        return repository.findByActif(1);
    }

    @Override
    public InventaireBiblio getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public InventaireBiblio save(InventaireBiblio entity) {
        return repository.save(entity);
    }

    @Override
    public void softDelete(Integer id) {
        repository.findById(id).ifPresent(e -> { e.setActif(0); repository.save(e); });
    }
}


