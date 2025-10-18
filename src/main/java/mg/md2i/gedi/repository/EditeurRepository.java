package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.Editeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditeurRepository extends JpaRepository<Editeur, Integer> {
    List<Editeur> findByActif(Integer actif);
    List<Editeur> findByEditeurContainingIgnoreCase(String editeur);
}


