package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.PeriodiqueDetail;
import java.util.List;

public interface PeriodiqueDetailService {
    List<PeriodiqueDetail> getAllActive();
    PeriodiqueDetail getById(Integer id);
    PeriodiqueDetail save(PeriodiqueDetail entity);
    void softDelete(Integer id);
}


