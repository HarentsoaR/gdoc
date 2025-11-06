package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.dto.SearchResult;
import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.gestionmetier.*;
import mg.md2i.gedi.services.LuceneService;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LuceneServiceImpl implements LuceneService {

    private static final Logger log = LoggerFactory.getLogger(LuceneServiceImpl.class);
    private static final String INDEX_DIR = System.getProperty("user.home") + "/gedi_storage/lucene-index";
    private final Directory indexDirectory;
    private final StandardAnalyzer analyzer;

    public LuceneServiceImpl() {
        try {
            Path indexPath = Paths.get(INDEX_DIR);
            if (Files.notExists(indexPath)) {
                Files.createDirectories(indexPath);
            }
            this.indexDirectory = FSDirectory.open(indexPath);
            this.analyzer = new StandardAnalyzer();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Lucene service", e);
        }
    }

    private IndexWriter getWriter() throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        return new IndexWriter(indexDirectory, config);
    }

    private org.apache.lucene.document.Document createLuceneDoc(ListeDossierConcoursCandidat docInfo) {
        if (docInfo == null || docInfo.getListeDossierConcoursCandidatId() == null) {
            throw new IllegalArgumentException("Cannot index null or ID-less entity");
        }

        Candidat candidat = Optional.ofNullable(docInfo.getCandidatId()).map(CandidatGestion::findById).orElse(null);
        Concours concours = Optional.ofNullable(candidat).map(Candidat::getConcoursId).map(ConcoursGestion::findById).orElse(null);
        DocumentConcours docType = Optional.ofNullable(docInfo.getDocumentConcoursId()).map(DocumentConcoursGestion::findById).orElse(null);
        
        Filiere filiere = Optional.ofNullable(concours)
                                .map(Concours::getPromotion)
                                .map(Promotion::getFiliere)
                                .orElse(null);

        Promotion promotion = Optional.ofNullable(concours).map(Concours::getPromotion).orElse(null);
        CentreExamen centre = Optional.ofNullable(candidat).map(Candidat::getCentreExamenId).map(CentreExamenGestion::findById).orElse(null);

        String dbId = String.valueOf(docInfo.getListeDossierConcoursCandidatId());
        String filePath = Optional.ofNullable(docInfo.getRemarqueFacultatif()).orElse("");
        String candidateFullName = Optional.ofNullable(candidat).map(c -> (c.getNom() + " " + c.getPrenom()).trim()).orElse("");
        String concoursDisplayInfo = Optional.ofNullable(concours).map(Concours::getDisplayInfo).orElse("");
        String docTypeName = Optional.ofNullable(docType).map(DocumentConcours::getLibelle).orElse("");
        String filiereName = Optional.ofNullable(filiere).map(Filiere::getLibelle).orElse("");
        String promotionName = Optional.ofNullable(promotion).map(Promotion::getLibelle).orElse("");
        String centreExamenName = Optional.ofNullable(centre).map(CentreExamen::getLibelle).orElse("");

        org.apache.lucene.document.Document luceneDoc = new org.apache.lucene.document.Document();
        luceneDoc.add(new StringField("dbId", dbId, Field.Store.YES));
        luceneDoc.add(new StoredField("filePath", filePath));
        luceneDoc.add(new TextField("candidateFullName", candidateFullName, Field.Store.YES));
        luceneDoc.add(new TextField("concoursDisplayInfo", concoursDisplayInfo, Field.Store.YES));
        luceneDoc.add(new TextField("docTypeName", docTypeName, Field.Store.YES));
        luceneDoc.add(new TextField("filiere", filiereName, Field.Store.YES));
        luceneDoc.add(new TextField("promotion", promotionName, Field.Store.YES));
        luceneDoc.add(new TextField("centreExamen", centreExamenName, Field.Store.YES));

        return luceneDoc;
    }

    @Override
    public List<SearchResult> search(String queryStr) {
        List<SearchResult> results = new ArrayList<>();
        if (queryStr == null || queryStr.trim().isEmpty()) {
            return results;
        }

        try (DirectoryReader reader = DirectoryReader.open(indexDirectory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            String[] terms = queryStr.trim().toLowerCase().split("\\s+");
            
            for (String term : terms) {
                if (!term.isEmpty()) {
                    BooleanQuery.Builder termSubQuery = new BooleanQuery.Builder();
                    termSubQuery.add(new WildcardQuery(new Term("candidateFullName", "*" + term + "*")), BooleanClause.Occur.SHOULD);
                    termSubQuery.add(new WildcardQuery(new Term("concoursDisplayInfo", "*" + term + "*")), BooleanClause.Occur.SHOULD);
                    termSubQuery.add(new WildcardQuery(new Term("docTypeName", "*" + term + "*")), BooleanClause.Occur.SHOULD);
                    booleanQuery.add(termSubQuery.build(), BooleanClause.Occur.MUST);
                }
            }
            
            TopDocs hits = searcher.search(booleanQuery.build(), 200);
            
            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                org.apache.lucene.document.Document d = searcher.doc(scoreDoc.doc);
                String dbIdRaw = d.get("dbId");
                
                Long dbIdLong;
                try {
                    dbIdLong = Long.parseLong(dbIdRaw);
                } catch (NumberFormatException e) {
                    log.error("Corrupted index data. Lucene doc #{} has non-numeric dbId: '{}'. Skipping.", scoreDoc.doc, dbIdRaw);
                    continue;
                }

                results.add(new SearchResult(
                    dbIdLong,
                    d.get("candidateFullName"),
                    d.get("concoursDisplayInfo"),
                    d.get("docTypeName"),
                    d.get("filePath"),
                    d.get("filiere"),
                    d.get("promotion"),
                    d.get("centreExamen")
                ));
            }
        } catch (Exception e) {
            log.error("An error occurred during Lucene search for query '{}'", queryStr, e);
        }
        return results;
    }

    @Override
    public List<SearchResult> getSuggestions(String queryStr) {
        List<SearchResult> allResults = search(queryStr);
        Map<String, SearchResult> distinctResults = new LinkedHashMap<>();
        for (SearchResult result : allResults) {
            distinctResults.putIfAbsent(result.getCandidateFullName(), result);
        }
        return new ArrayList<>(distinctResults.values());
    }

    @Override
    public void indexDocument(ListeDossierConcoursCandidat docInfo) {
        if (docInfo == null) return;
        try (IndexWriter writer = getWriter()) {
            writer.addDocument(createLuceneDoc(docInfo));
        } catch (Exception e) {
            log.error("Failed to index doc {}: {}", docInfo.getListeDossierConcoursCandidatId(), e.getMessage(), e);
        }
    }

    @Override
    public void updateDocument(ListeDossierConcoursCandidat docInfo) {
        if (docInfo == null || docInfo.getListeDossierConcoursCandidatId() == null) return;
        try (IndexWriter writer = getWriter()) {
            writer.updateDocument(new Term("dbId", String.valueOf(docInfo.getListeDossierConcoursCandidatId())), createLuceneDoc(docInfo));
        } catch (Exception e) {
            log.error("Failed to update index for dbId={}", docInfo.getListeDossierConcoursCandidatId(), e);
        }
    }

    @Override
    public void deleteDocument(Long dbId) {
        if (dbId == null) return;
        try (IndexWriter writer = getWriter()) {
            writer.deleteDocuments(new Term("dbId", String.valueOf(dbId)));
        } catch (IOException e) {
            log.error("Failed to delete index entry for dbId={}", dbId, e);
        }
    }

    public void reindexAll(List<ListeDossierConcoursCandidat> allEntities) {
        if (allEntities == null) return;
        try (IndexWriter writer = getWriter()) {
            writer.deleteAll();
            for (ListeDossierConcoursCandidat entity : allEntities) {
                try {
                    writer.addDocument(createLuceneDoc(entity));
                } catch (IllegalArgumentException e) {
                    log.warn("Skipped invalid entity during reindex: {}", e.getMessage());
                }
            }
        } catch (IOException e) {
            log.error("Critical error during reindexAll()", e);
        }
    }
}