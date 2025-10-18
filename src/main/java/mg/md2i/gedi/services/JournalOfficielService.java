package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.JournalOfficiel;
import java.util.Date;
import java.util.List;

public interface JournalOfficielService {
    List<JournalOfficiel> getAllActive();
    JournalOfficiel getById(Integer id);
    JournalOfficiel save(JournalOfficiel entity);
    void softDelete(Integer id);
    List<JournalOfficiel> findByNumero(Integer numero);
    List<JournalOfficiel> findByDateRange(Date start, Date end);
}


