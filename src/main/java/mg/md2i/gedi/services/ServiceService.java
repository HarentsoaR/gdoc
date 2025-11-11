package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Services;

import java.util.List;

public interface ServiceService {

    List<Services> getAllServices();
    Services getServiceById(Integer id);
    void saveService(Services services);
    void deleteService(Integer id);
    List<Services> searchServices(String query);
    
}
