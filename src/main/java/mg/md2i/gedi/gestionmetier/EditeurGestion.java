package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.Editeur;
import mg.md2i.gedi.repository.EditeurRepository;

import java.util.List;

public class EditeurGestion {

    private static EditeurRepository repo() {
        return ObjectFactory.getBean(EditeurRepository.class);
    }

    public static List<Editeur> findAll() { return repo().findAll(); }
    public static Editeur findById(Integer id) { return repo().findById(id).orElse(null); }
    public static Editeur save(Editeur e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<Editeur> searchByNom(String q) { return repo().findByEditeurContainingIgnoreCase(q); }
}