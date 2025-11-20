package mg.md2i.gedi.services;

import mg.md2i.gedi.dto.SearchResult;
import mg.md2i.gedi.entity.ListeDossierConcoursCandidat;
import java.util.List;

/**
 * Définit le contrat pour toutes les opérations de recherche et d'indexation.
 * Le reste de l'application ne dépend que de cette interface.
 */
public interface LuceneService {
    
    /** Indexe un nouveau document de concours. */
    void indexDocument(ListeDossierConcoursCandidat docInfo);
    
    /** Supprime un document de l'index Lucene en utilisant son ID de base de données. */
    void deleteDocument(Long dbId);
    
    /** Met à jour un document existant dans l'index. */
    void updateDocument(ListeDossierConcoursCandidat docInfo);
    
    /**
     * Effectue une recherche textuelle sur l'index.
     * @param queryStr La chaîne de caractères à rechercher.
     * @return Une liste de SearchResult correspondant à la recherche.
     */
    List<SearchResult> search(String queryStr);
}
