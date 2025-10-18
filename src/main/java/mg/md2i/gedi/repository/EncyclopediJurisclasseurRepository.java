package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.EncyclopediJurisclasseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncyclopediJurisclasseurRepository extends JpaRepository<EncyclopediJurisclasseur, Integer> {
    List<EncyclopediJurisclasseur> findByActif(Integer actif);
    List<EncyclopediJurisclasseur> findByTitreContainingIgnoreCase(String titre);
}


