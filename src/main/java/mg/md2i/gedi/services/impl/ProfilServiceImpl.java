package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Profil;
import mg.md2i.gedi.repository.ProfilRepository;
import mg.md2i.gedi.services.ProfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("profilServiceImpl")
public class ProfilServiceImpl implements ProfilService {

    private static final Logger log = LoggerFactory.getLogger(ProfilServiceImpl.class);

    @Autowired
    private ProfilRepository profilRepository;

    @Override
    public List<Profil> getAllProfils() {
        log.info("🧩 Récupération de tous les profils...");
        return profilRepository.findAll();
    }

    @Override
    public Profil getProfilById(Integer id) {
        log.info("🔍 Recherche du profil ID={}", id);
        return profilRepository.findById(id).orElse(null);
    }

    @Override
    public void saveProfil(Profil profil) {
        log.info("💾 Sauvegarde du profil: {}", profil.getLibelle());
        profilRepository.save(profil);
    }

    @Override
    public void deleteProfil(Integer id) {
        log.warn("🗑️ Suppression du profil ID={}", id);
        profilRepository.deleteById(id);
    }

    @Override
    public List<Profil> searchProfils(String query) {
        log.info("🔎 Recherche des profils contenant '{}'", query);
        return profilRepository.findByLibelleContainingIgnoreCase(query);
    }

    @Override
    public List<Profil> findProfilsByServiceId(Integer serviceId) {
        log.info("🔎 Recherche des profils pour le service ID={}", serviceId);
        return profilRepository.findByServiceId(serviceId);
    }
}
