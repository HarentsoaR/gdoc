package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Fonctionnalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FonctionnaliteRepository extends JpaRepository<Fonctionnalite, Integer> {
	Fonctionnalite findByNomTable(String nomTable);

    List<Fonctionnalite> findByLibelleContainingIgnoreCase(String libelle);
}
