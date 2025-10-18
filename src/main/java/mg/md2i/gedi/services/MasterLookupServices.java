package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.*;

import java.util.List;

/**
 * Convenience facade for common master-data lookups used across ViewModels.
 */
public interface MasterLookupServices {
    List<DomaineBiblio> domainesBiblio();
    List<DomaineOuvrage> domainesOuvrage();
    List<CategorieOuvrage> categoriesOuvrage();
    List<CollectionOuvrage> collectionsOuvrage();
    List<Editeur> editeurs();
    List<FiliereBiblio> filieres();
    List<PromotionBiblio> promotions();
    List<Repertoire> repertoires();
    List<Ministere> ministeres();
    List<Origine> origines();
    List<TypeDocument> typesDocument();
    List<TypeJournal> typesJournal();
    List<TypeJournalOfficiel> typesJournalOfficiel();
    List<TypeBulletinInformation> typesBulletinInformation();
}


