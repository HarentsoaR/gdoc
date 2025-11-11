package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.entity.Services;
import mg.md2i.gedi.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionMetierService {

    @Autowired
    private ServiceService serviceService;

    public List<Services> getAllServices() {
        return serviceService.getAllServices();
    }

    public Services getServiceById(Integer id) {
        return serviceService.getServiceById(id);
    }

    public void saveService(Services services) {
        serviceService.saveService(services);
    }

    public void deleteService(Integer id) {
        serviceService.deleteService(id);
    }

    public List<Services> searchServices(String query) {
        return serviceService.searchServices(query);
    }

    // You can add more specific business logic methods here for Service
}
