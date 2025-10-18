package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.repository.CategorieOuvrageRepository;
import mg.md2i.gedi.repository.CollectionOuvrageRepository;
import mg.md2i.gedi.repository.DomaineBiblioRepository;
import mg.md2i.gedi.repository.DomaineOuvrageRepository;
import mg.md2i.gedi.repository.EditeurRepository;
import mg.md2i.gedi.repository.FiliereBiblioRepository;
import mg.md2i.gedi.repository.MinistereRepository;
import mg.md2i.gedi.repository.OrigineRepository;
import mg.md2i.gedi.repository.PromotionBiblioRepository;
import mg.md2i.gedi.repository.RepertoireRepository;
import mg.md2i.gedi.repository.TypeBulletinInformationRepository;
import mg.md2i.gedi.repository.TypeDocumentRepository;
import mg.md2i.gedi.repository.TypeJournalOfficielRepository;
import mg.md2i.gedi.repository.TypeJournalRepository;
//import mg.md2i.gedi.services.*;

import java.util.List;

public class MasterLookupGestion {

    public static List<DomaineBiblio> domainesBiblio() { return ObjectFactory.getBean(DomaineBiblioRepository.class).findAll(); }
    public static List<DomaineOuvrage> domainesOuvrage() { return ObjectFactory.getBean(DomaineOuvrageRepository.class).findAll(); }
    public static List<CategorieOuvrage> categoriesOuvrage() { return ObjectFactory.getBean(CategorieOuvrageRepository.class).findAll(); }
    public static List<CollectionOuvrage> collectionsOuvrage() { return ObjectFactory.getBean(CollectionOuvrageRepository.class).findAll(); }
    public static List<Editeur> editeurs() { return ObjectFactory.getBean(EditeurRepository.class).findAll(); }
    public static List<FiliereBiblio> filieres() { return ObjectFactory.getBean(FiliereBiblioRepository.class).findAll(); }
    public static List<PromotionBiblio> promotions() { return ObjectFactory.getBean(PromotionBiblioRepository.class).findAll(); }
    public static List<Repertoire> repertoires() { return ObjectFactory.getBean(RepertoireRepository.class).findAll(); }
    public static List<Ministere> ministeres() { return ObjectFactory.getBean(MinistereRepository.class).findAll(); }
    public static List<Origine> origines() { return ObjectFactory.getBean(OrigineRepository.class).findAll(); }
    public static List<TypeDocument> typesDocument() { return ObjectFactory.getBean(TypeDocumentRepository.class).findAll(); }
    public static List<TypeJournal> typesJournal() { return ObjectFactory.getBean(TypeJournalRepository.class).findAll(); }
    public static List<TypeJournalOfficiel> typesJournalOfficiel() { return ObjectFactory.getBean(TypeJournalOfficielRepository.class).findAll(); }
    public static List<TypeBulletinInformation> typesBulletinInformation() { return ObjectFactory.getBean(TypeBulletinInformationRepository.class).findAll(); }
}


