package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Integer> {
    List<Historique> findByDateBetween(Date from, Date to);
    List<Historique> findByConnexionId(Integer connexionId);
    List<Historique> findByOperationContainingIgnoreCase(String op);
}
