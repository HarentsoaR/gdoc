package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.TypeBulletinInformation;
import java.util.List;

public interface TypeBulletinInformationService {
    List<TypeBulletinInformation> getAllActive();
    TypeBulletinInformation getById(Integer id);
    TypeBulletinInformation save(TypeBulletinInformation entity);
    void delete(Integer id);
    List<TypeBulletinInformation> searchByLibelle(String query);
}


