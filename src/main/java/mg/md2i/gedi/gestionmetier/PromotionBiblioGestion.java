package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.PromotionBiblio;
import mg.md2i.gedi.repository.PromotionBiblioRepository;

import java.util.List;

public class PromotionBiblioGestion {

    private static PromotionBiblioRepository repo() {
        return ObjectFactory.getBean(PromotionBiblioRepository.class);
    }

    public static List<PromotionBiblio> findAll() { return repo().findAll(); }
    public static PromotionBiblio findById(Integer id) { return repo().findById(id).orElse(null); }
    public static PromotionBiblio save(PromotionBiblio e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<PromotionBiblio> searchByLibelle(String q) { return repo().findByNum(Integer.parseInt(q)); }
}