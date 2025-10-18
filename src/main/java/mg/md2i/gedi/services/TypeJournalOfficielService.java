package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.TypeJournalOfficiel;
import java.util.List;

public interface TypeJournalOfficielService {
    List<TypeJournalOfficiel> getAll();
    TypeJournalOfficiel getById(Integer id);
    TypeJournalOfficiel save(TypeJournalOfficiel entity);
    void delete(Integer id);
    List<TypeJournalOfficiel> searchByTitre(String query);
}


