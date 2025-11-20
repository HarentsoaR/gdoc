package mg.md2i.gedi.services.impl;

import mg.md2i.gedi.dto.SearchResult;
import mg.md2i.gedi.entity.*;
import mg.md2i.gedi.gestionmetier.*;
import mg.md2i.gedi.enums.DocumentValidationEtat;
import mg.md2i.gedi.services.LuceneService;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
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
import java.util.stream.Collectors;

public class LuceneServiceImpl implements LuceneService {

    private static final Logger log = LoggerFactory.getLogger(LuceneServiceImpl.class);
    private static final String INDEX_DIR = System.getProperty("user.home") + "/gedi_storage/lucene-index";
    private static final String[] TEXT_FIELDS = new String[]{
            "candidateFullName",
            "concoursDisplayInfo",
            "docTypeName",
            "filiere",
            "promotion",
            "centreExamen",
            "etatDocumentLabel",
            "numeroEnregistrement",
            "numInscription",
            "searchBlob"
    };
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

    private String normalize(String value) {
        return value == null ? "" : value.toLowerCase();
    }

    private List<String> tokenizeQuery(String queryStr) {
        List<String> tokens = new ArrayList<>();
        if (queryStr == null) {
            return tokens;
        }
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        for (char c : queryStr.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (Character.isWhitespace(c) && !inQuotes) {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }
        if (current.length() > 0) {
            tokens.add(current.toString());
        }
        return tokens;
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
        String numeroEnregistrement = Optional.ofNullable(candidat).map(Candidat::getNumeroEnregistrement).orElse("");
        String numeroInscription = Optional.ofNullable(candidat)
                .map(Candidat::getNumInscription)
                .map(String::valueOf)
                .orElse("");
        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(docInfo.getEtatDocument());

        org.apache.lucene.document.Document luceneDoc = new org.apache.lucene.document.Document();
        luceneDoc.add(new StringField("dbId", dbId, Field.Store.YES));
        luceneDoc.add(new StoredField("filePath", filePath));
        luceneDoc.add(new TextField("candidateFullName", candidateFullName, Field.Store.YES));
        luceneDoc.add(new TextField("concoursDisplayInfo", concoursDisplayInfo, Field.Store.YES));
        luceneDoc.add(new TextField("docTypeName", docTypeName, Field.Store.YES));
        luceneDoc.add(new TextField("filiere", filiereName, Field.Store.YES));
        luceneDoc.add(new TextField("promotion", promotionName, Field.Store.YES));
        luceneDoc.add(new TextField("centreExamen", centreExamenName, Field.Store.YES));
        luceneDoc.add(new TextField("etatDocumentLabel", etat.getLabel(), Field.Store.YES));
        luceneDoc.add(new StringField("numeroEnregistrement", normalize(numeroEnregistrement), Field.Store.YES));
        luceneDoc.add(new StringField("numInscription", normalize(numeroInscription), Field.Store.YES));
        String searchBlob = String.join(" ",
                candidateFullName,
                concoursDisplayInfo,
                docTypeName,
                filiereName,
                promotionName,
                centreExamenName,
                numeroEnregistrement,
                numeroInscription,
                etat.getLabel());
        luceneDoc.add(new TextField("searchBlob", searchBlob.toLowerCase(), Field.Store.NO));

        return luceneDoc;
    }

    @Override
    public List<SearchResult> search(String queryStr) {
        List<SearchResult> results = new ArrayList<>();
        if (queryStr == null || queryStr.trim().isEmpty()) {
            return results;
        }

        Map<String, List<String>> criteria = new LinkedHashMap<>();
        List<String> keywords = new ArrayList<>();
        for (String raw : tokenizeQuery(queryStr.trim())) {
            if (raw.contains(":")) {
                String[] parts = raw.split(":", 2);
                if (parts.length == 2) {
                    String field = resolveFieldKey(parts[0]);
                    String value = parts[1];
                    if (field != null && value != null && !value.trim().isEmpty()) {
                        criteria.computeIfAbsent(field, k -> new ArrayList<>()).add(value.trim().toLowerCase());
                        continue;
                    }
                }
            }
            if (!raw.isEmpty()) {
                keywords.add(raw);
            }
        }

        try (DirectoryReader reader = DirectoryReader.open(indexDirectory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            boolean hasClause = false;

            String freeText = keywords.stream().collect(Collectors.joining(" "));
            Query textQuery = buildFullTextQuery(freeText);
            if (textQuery != null) {
                booleanQuery.add(textQuery, BooleanClause.Occur.SHOULD);
                hasClause = true;
            }

            for (String term : keywords) {
                Query tokenQuery = buildTokenClause(term);
                if (tokenQuery != null) {
                    booleanQuery.add(tokenQuery, BooleanClause.Occur.MUST);
                    hasClause = true;
                }
            }

            for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
                for (String value : entry.getValue()) {
                    Query clause = new WildcardQuery(new Term(entry.getKey(), "*" + value + "*"));
                    booleanQuery.add(clause, BooleanClause.Occur.MUST);
                    hasClause = true;
                }
            }

            if (!hasClause) {
                return results;
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

                ListeDossierConcoursCandidat entity = ListeDossierConcoursCandidatGestion.findById(dbIdLong.intValue());
                if (entity == null) {
                    continue;
                }
                SearchResult result = buildSearchResult(entity);
                if (result != null) {
                    results.add(result);
                }
            }
        } catch (Exception e) {
            log.error("An error occurred during Lucene search for query '{}'", queryStr, e);
        }
        return results;
    }

    private Query buildFullTextQuery(String freeText) {
        if (freeText == null || freeText.trim().isEmpty()) {
            return null;
        }
        try {
            MultiFieldQueryParser parser = new MultiFieldQueryParser(TEXT_FIELDS, analyzer);
            parser.setAllowLeadingWildcard(true);
            parser.setDefaultOperator(QueryParser.Operator.OR);
            return parser.parse(QueryParser.escape(freeText));
        } catch (ParseException e) {
            log.debug("Lucene parser fallback for '{}': {}", freeText, e.getMessage());
            return null;
        }
    }

    private Query buildTokenClause(String term) {
        if (term == null || term.trim().isEmpty()) {
            return null;
        }
        String lower = term.toLowerCase();
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        for (String field : TEXT_FIELDS) {
            builder.add(new WildcardQuery(new Term(field, "*" + lower + "*")), BooleanClause.Occur.SHOULD);
            builder.add(new PrefixQuery(new Term(field, lower)), BooleanClause.Occur.SHOULD);
            if (lower.length() >= 4) {
                builder.add(new FuzzyQuery(new Term(field, lower), 1), BooleanClause.Occur.SHOULD);
            }
        }
        return builder.build();
    }

    private SearchResult buildSearchResult(ListeDossierConcoursCandidat entity) {
        if (entity == null) {
            return null;
        }
        Candidat candidat = Optional.ofNullable(entity.getCandidat())
                .orElseGet(() -> Optional.ofNullable(entity.getCandidatId()).map(CandidatGestion::findById).orElse(null));
        Concours concours = Optional.ofNullable(candidat)
                .map(Candidat::getConcoursId)
                .map(ConcoursGestion::findById)
                .orElse(null);
        DocumentConcours docType = Optional.ofNullable(entity.getDocumentConcours())
                .orElseGet(() -> Optional.ofNullable(entity.getDocumentConcoursId()).map(DocumentConcoursGestion::findById).orElse(null));
        Promotion promotion = Optional.ofNullable(concours).map(Concours::getPromotion).orElse(null);
        Filiere filiere = Optional.ofNullable(promotion).map(Promotion::getFiliere).orElse(null);
        CentreExamen centre = Optional.ofNullable(candidat)
                .map(Candidat::getCentreExamenId)
                .map(CentreExamenGestion::findById)
                .orElse(null);

        String candidateFullName = Optional.ofNullable(candidat)
                .map(c -> (c.getNom() + " " + c.getPrenom()).trim())
                .orElse("-");
        String concoursLabel = Optional.ofNullable(concours).map(Concours::getDisplayInfo).orElse("-");
        String docTypeLabel = Optional.ofNullable(docType).map(DocumentConcours::getLibelle).orElse("-");
        String filePath = Optional.ofNullable(entity.getRemarqueFacultatif()).orElse("");
        String filiereLabel = Optional.ofNullable(filiere).map(Filiere::getLibelle).orElse("");
        String promotionLabel = Optional.ofNullable(promotion).map(Promotion::getLibelle).orElse("");
        String centreLabel = Optional.ofNullable(centre).map(CentreExamen::getLibelle).orElse("");
        String numeroEnregistrement = Optional.ofNullable(candidat).map(Candidat::getNumeroEnregistrement).orElse("");
        String numeroInscription = Optional.ofNullable(candidat)
                .map(Candidat::getNumInscription)
                .map(String::valueOf)
                .orElse("");
        DocumentValidationEtat etat = DocumentValidationEtat.fromCode(entity.getEtatDocument());

        return new SearchResult(
                entity.getListeDossierConcoursCandidatId() != null ? entity.getListeDossierConcoursCandidatId().longValue() : null,
                Optional.ofNullable(candidat).map(Candidat::getCandidatId).orElse(null),
                Optional.ofNullable(concours).map(Concours::getConcoursId).orElse(null),
                Optional.ofNullable(centre).map(CentreExamen::getCentreExamenId).orElse(null),
                candidateFullName,
                concoursLabel,
                docTypeLabel,
                filePath,
                filiereLabel,
                promotionLabel,
                centreLabel,
                numeroEnregistrement,
                numeroInscription,
                etat
        );
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

    private String resolveFieldKey(String rawKey) {
        if (rawKey == null) return null;
        switch (rawKey.toLowerCase()) {
            case "filiere":
            case "fil":
                return "filiere";
            case "promotion":
            case "promo":
                return "promotion";
            case "centre":
            case "centreexamen":
            case "centre-examen":
                return "centreExamen";
            case "type":
            case "document":
            case "piece":
            case "doctype":
                return "docTypeName";
            case "candidat":
            case "nom":
                return "candidateFullName";
            case "concours":
                return "concoursDisplayInfo";
            case "etat":
            case "statut":
                return "etatDocumentLabel";
            case "numero":
            case "num":
            case "dossier":
            case "enregistrement":
                return "numeroEnregistrement";
            case "inscription":
            case "numinscription":
            case "num-inscription":
                return "numInscription";
            default:
                return null;
        }
    }
}
