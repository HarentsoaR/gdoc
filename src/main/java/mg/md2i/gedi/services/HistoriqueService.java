package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Historique;
import mg.md2i.gedi.repository.HistoriqueRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoriqueService {

    private final HistoriqueRepository repository;

    public HistoriqueService(HistoriqueRepository repository) {
        this.repository = repository;
    }

    public List<Historique> findAll() {
        return repository.findAll();
    }

    public Historique findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Historique> findByDateRange(Date from, Date to) {
        return repository.findByDateBetween(from, to);
    }

    public List<Historique> findByConnexion(Integer connexionId) {
        return repository.findByConnexionId(connexionId);
    }

    public List<Historique> searchByOperation(String op) {
        return repository.findByOperationContainingIgnoreCase(op);
    }
}
