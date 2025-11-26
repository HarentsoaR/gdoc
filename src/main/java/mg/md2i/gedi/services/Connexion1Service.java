package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Connexion1;
import mg.md2i.gedi.repository.Connexion1Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Connexion1Service {

    private final Connexion1Repository repository;

    public Connexion1Service(Connexion1Repository repository) {
        this.repository = repository;
    }

    public List<Connexion1> findAll() {
        return repository.findAll();
    }

    public List<Connexion1> findAllActive() {
        return repository.findByActif(1);
    }

    public List<Connexion1> findByDateRange(Date from, Date to) {
        return repository.findByDateDebutBetween(from, to);
    }

    public List<Connexion1> findByUtilisateur(Integer userId) {
        return repository.findByUtilisateurId(userId);
    }

    public Connexion1 findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
