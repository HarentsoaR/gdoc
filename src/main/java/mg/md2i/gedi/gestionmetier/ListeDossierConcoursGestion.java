package mg.md2i.gedi.gestionmetier;

import mg.md2i.gedi.config.ObjectFactory;
import mg.md2i.gedi.entity.ListeDossierConcours;
import mg.md2i.gedi.services.ListeDossierConcoursService;
import java.util.List;
import java.util.ArrayList;

public class ListeDossierConcoursGestion {

    private static ListeDossierConcoursService getService() {
        return ObjectFactory.getBean(ListeDossierConcoursService.class);
    }

    public static List<ListeDossierConcours> findAll() { return getService().getAllActive(); }
    public static ListeDossierConcours findById(Integer id) { return getService().getById(id); }
    public static void save(ListeDossierConcours e) { getService().save(e); }
    public static void delete(Integer id) { getService().softDelete(id); }
    public static List<ListeDossierConcours> findByConcours(Integer concoursId) { return getService().getByConcours(concoursId); }
    public static List<ListeDossierConcours> searchByNom(String q) { return getService().searchByNom(q); }

    public static List<ListeDossierConcours> filterDocuments(Integer documentConcoursId, Integer candidatId) {
        List<ListeDossierConcours> filteredList = new ArrayList<>();
        List<ListeDossierConcours> allDocuments = findAll(); // Or however you get all documents

//        for (ListeDossierConcours document : allDocuments) {
//            boolean matchesDocumentType = (documentConcoursId == null) || (document.getDocumentConcoursId() != null && document.getDocumentConcoursId().equals(documentConcoursId));
//            boolean matchesCandidat = (candidatId == null) || (document.getCandidatId() != null && document.getCandidatId().equals(candidatId));
//
//            if (matchesDocumentType && matchesCandidat) {
//                filteredList.add(document);
//            }
//        }
        return filteredList;
    }


//    public static List<ListeDossierConcours> filterDocuments(Integer documentConcoursId, Integer candidatId) {
//        List<ListeDossierConcours> filteredList = new ArrayList<>();
//        List<ListeDossierConcours> allDocuments = findAll(); // Or however you get all documents
//
//        for (ListeDossierConcours document : allDocuments) {
//            boolean matchesDocumentType = (documentConcoursId == null) || (document.getDocumentConcoursId() != null && document.getDocumentConcoursId().equals(documentConcoursId));
//            boolean matchesCandidat = (candidatId == null) || (document.getCandidatId() != null && document.getCandidatId().equals(candidatId));
//
//            if (matchesDocumentType && matchesCandidat) {
//                filteredList.add(document);
//            }
//        }
//        return filteredList;
//    }
}

