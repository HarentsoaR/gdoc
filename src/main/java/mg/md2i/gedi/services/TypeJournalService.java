package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.TypeJournal;
import java.util.List;

public interface TypeJournalService {
    List<TypeJournal> getAllActive();
    TypeJournal getById(Integer id);
    TypeJournal save(TypeJournal entity);
    void delete(Integer id);
    List<TypeJournal> searchByType(String query);
}


