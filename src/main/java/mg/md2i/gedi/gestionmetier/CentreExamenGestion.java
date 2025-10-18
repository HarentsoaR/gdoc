package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.CentreExamen;
import mg.md2i.gedi.services.CentreExamenService;
import java.util.List;

public class CentreExamenGestion {

    private static CentreExamenService getService() {
        return ObjectFactory.getBean(CentreExamenService.class);
    }

    public static List<CentreExamen> findAll() { 
        return getService().getAllActive(); 
    }
    
    public static CentreExamen findById(Integer id) { 
        return getService().getById(id); 
    }
    
    public static void save(CentreExamen e) { 
        getService().save(e); 
    }
    
    public static void delete(Integer id) { 
        getService().softDelete(id); 
    }
}
