package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.JournalOfficiel;
import mg.md2i.gedi.services.JournalOfficielService;

import java.util.Date;
import java.util.List;

public class JournalOfficielGestion {

    private static JournalOfficielService getService() {
        return ObjectFactory.getBean(JournalOfficielService.class);
    }

    public static List<JournalOfficiel> findAll() { return getService().getAllActive(); }
    public static JournalOfficiel findById(Integer id) { return getService().getById(id); }
    public static JournalOfficiel save(JournalOfficiel e) { return getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<JournalOfficiel> findByNumero(Integer numero) { return getService().findByNumero(numero); }
    public static List<JournalOfficiel> findByDateRange(Date start, Date end) { return getService().findByDateRange(start, end); }
}


