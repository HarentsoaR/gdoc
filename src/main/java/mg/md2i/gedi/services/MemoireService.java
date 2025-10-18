package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Memoire;
import java.util.List;

public interface MemoireService {
    List<Memoire> getAllActive();
    Memoire getById(Integer id);
    Memoire save(Memoire entity);
    void softDelete(Integer id);
    List<Memoire> search(String query);
}


