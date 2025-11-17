package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.FonctionnaliteProfil;
import mg.md2i.gedi.repository.FonctionnaliteProfilRepository;
import mg.md2i.gedi.services.FonctionnaliteProfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FonctionnaliteProfilServiceImpl implements FonctionnaliteProfilService {

    private static final Logger log = LoggerFactory.getLogger(FonctionnaliteProfilServiceImpl.class);

    @Autowired
    private FonctionnaliteProfilRepository fonctionnaliteProfilRepository;

    @Override
    public List<FonctionnaliteProfil> getFonctionnaliteProfilsByProfilId(Integer profilId) {
        log.info("⚙️ Récupération des FonctionnaliteProfils pour le profil ID={}", profilId);
        return fonctionnaliteProfilRepository.findByProfilId(profilId);
    }

    @Override
    public FonctionnaliteProfil getFonctionnaliteProfilByProfilIdAndFonctionnaliteId(Integer profilId, Integer fonctionnaliteId) {
        log.info("🔍 Recherche de FonctionnaliteProfil pour profil ID={} et fonctionnalité ID={}", profilId, fonctionnaliteId);
        return fonctionnaliteProfilRepository.findByProfilIdAndFonctionnaliteId(profilId, fonctionnaliteId);
    }

    @Override
    public void saveFonctionnaliteProfil(FonctionnaliteProfil fonctionnaliteProfil) {
        log.info("💾 Sauvegarde de FonctionnaliteProfil pour profil ID={} et fonctionnalité ID={}", fonctionnaliteProfil.getProfilId(), fonctionnaliteProfil.getFonctionnaliteId());
        fonctionnaliteProfilRepository.save(fonctionnaliteProfil);
    }

    @Override
    public void deleteFonctionnaliteProfil(Integer id) {
        log.warn("🗑️ Suppression de FonctionnaliteProfil ID={}", id);
        fonctionnaliteProfilRepository.deleteById(id);
    }

    @Override
    public void deleteAllFonctionnaliteProfilsByProfilId(Integer profilId) {
        log.warn("🗑️ Suppression de tous les FonctionnaliteProfils pour le profil ID={}", profilId);
        List<FonctionnaliteProfil> fonctionnaliteProfils = fonctionnaliteProfilRepository.findByProfilId(profilId);
        fonctionnaliteProfilRepository.deleteAll(fonctionnaliteProfils);
    }
}
