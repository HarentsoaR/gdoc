package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.DetailBulletin;
import java.util.List;

public interface DetailBulletinService {
    List<DetailBulletin> getAllActive();
    DetailBulletin getById(Integer id);
    DetailBulletin save(DetailBulletin entity);
    void softDelete(Integer id);
}


