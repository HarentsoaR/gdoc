package mg.md2i.gedi.repository;

import mg.md2i.gedi.entity.CollectionOuvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionOuvrageRepository extends JpaRepository<CollectionOuvrage, Integer> {
    List<CollectionOuvrage> findByActif(Integer actif);
    List<CollectionOuvrage> findByNomContainingIgnoreCase(String nom);
}


