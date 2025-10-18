package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.EncyclopediJurisclasseur;
import mg.md2i.gedi.services.EncyclopediJurisclasseurService;

import java.util.List;

public class EncyclopediJurisclasseurGestion {

    private static EncyclopediJurisclasseurService getService() {
        return ObjectFactory.getBean(EncyclopediJurisclasseurService.class);
    }

    public static List<EncyclopediJurisclasseur> findAll() { return getService().getAllActive(); }
    public static EncyclopediJurisclasseur findById(Integer id) { return getService().getById(id); }
    public static EncyclopediJurisclasseur save(EncyclopediJurisclasseur e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<EncyclopediJurisclasseur> searchByTitre(String q) { return getService().searchByTitre(q); }
}


