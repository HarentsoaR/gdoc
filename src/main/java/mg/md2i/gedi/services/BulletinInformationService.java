package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.BulletinInformation;
import java.util.List;

public interface BulletinInformationService {
    List<BulletinInformation> getAllActive();
    BulletinInformation getById(Integer id);
    BulletinInformation save(BulletinInformation entity);
    void softDelete(Integer id);
    List<BulletinInformation> search(String query);
}


