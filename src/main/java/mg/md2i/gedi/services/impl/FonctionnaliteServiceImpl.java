package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Fonctionnalite;
import mg.md2i.gedi.repository.FonctionnaliteRepository;
import mg.md2i.gedi.services.FonctionnaliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FonctionnaliteServiceImpl implements FonctionnaliteService {

    private static final Logger log = LoggerFactory.getLogger(FonctionnaliteServiceImpl.class);

    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;

    @Override
    public List<Fonctionnalite> getAllFonctionnalites() {
        log.info("⚙️ Récupération de toutes les fonctionnalités...");
        return fonctionnaliteRepository.findAll();
    }

    @Override
    public Fonctionnalite getFonctionnaliteById(Integer id) {
        log.info("🔍 Recherche de la fonctionnalité ID={}", id);
        return fonctionnaliteRepository.findById(id).orElse(null);
    }

    @Override
    public void saveFonctionnalite(Fonctionnalite fonctionnalite) {
        log.info("💾 Sauvegarde de la fonctionnalité: {}", fonctionnalite.getLibelle());
        fonctionnaliteRepository.save(fonctionnalite);
    }

    @Override
    public void deleteFonctionnalite(Integer id) {
        log.warn("🗑️ Suppression de la fonctionnalité ID={}", id);
        fonctionnaliteRepository.deleteById(id);
    }

    @Override
    public List<Fonctionnalite> searchFonctionnalites(String searchQuery) {
        log.info("🔍 Recherche de fonctionnalités avec le critère: {}", searchQuery);
        return fonctionnaliteRepository.findByLibelleContainingIgnoreCase(searchQuery);
    }
    
    @Override
    public Integer findFonctionnaliteIdByNomTable(String nomTable) {
        Fonctionnalite f = fonctionnaliteRepository.findByNomTable(nomTable);
        return (f != null ? f.getFonctionnaliteId() : null);
    }

}
