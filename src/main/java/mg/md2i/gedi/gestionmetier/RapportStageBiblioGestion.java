package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.RapportStageBiblio;
import mg.md2i.gedi.services.RapportStageBiblioService;

import java.util.List;

public class RapportStageBiblioGestion {

    private static RapportStageBiblioService getService() {
        return ObjectFactory.getBean(RapportStageBiblioService.class);
    }

    public static List<RapportStageBiblio> findAll() { return getService().getAllActive(); }
    public static RapportStageBiblio findById(Integer id) { return getService().getById(id); }
    public static RapportStageBiblio save(RapportStageBiblio e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<RapportStageBiblio> searchByTitre(String q) { return getService().search(q); }
    public static List<RapportStageBiblio> searchByNom(String q) { return getService().search(q); }
}