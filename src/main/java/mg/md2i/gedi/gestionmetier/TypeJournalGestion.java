package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.TypeJournal;
import mg.md2i.gedi.repository.TypeJournalRepository;

import java.util.List;

public class TypeJournalGestion {

    private static TypeJournalRepository repo() {
        return ObjectFactory.getBean(TypeJournalRepository.class);
    }

    public static List<TypeJournal> findAll() { return repo().findAll(); }
    public static TypeJournal findById(Integer id) { return repo().findById(id).orElse(null); }
    public static TypeJournal save(TypeJournal e) { return repo().save(e); }
    public static void delete(Integer id) { repo().deleteById(id); }
    public static List<TypeJournal> searchByLibelle(String q) { return repo().findByTypeJournalContainingIgnoreCase(q); }
}