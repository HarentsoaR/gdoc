package mg.md2i.gedi.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.md2i.gedi.entity.Services;
import mg.md2i.gedi.repository.ServiceRepository;
import mg.md2i.gedi.services.ServiceService;

@Service
public class ServiceServiceImpl implements ServiceService {

    private static final Logger log = LoggerFactory.getLogger(ServiceServiceImpl.class);

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<Services> getAllServices() {
        log.info("🧩 Récupération de tous les services...");
        return serviceRepository.findAll();
    }

    @Override
    public Services getServiceById(Integer id) {
        log.info("🔍 Recherche du service ID={}", id);
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public void saveService(Services service) {
        log.info("💾 Sauvegarde du service: {}", service.getLibelle());
        serviceRepository.save(service);
    }

    @Override
    public void deleteService(Integer id) {
        log.warn("🗑️ Suppression du service ID={}", id);
        serviceRepository.deleteById(id);
    }

    @Override
    public List<Services> searchServices(String query) {
        log.info("🔎 Recherche des services contenant '{}'", query);
        return serviceRepository.findByLibelleContainingIgnoreCase(query);
    }
}
