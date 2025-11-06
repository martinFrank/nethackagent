package com.github.martinfrank.nethackagent.embedding;

import com.github.martinfrank.nethackagent.LlmModelService;
import com.github.martinfrank.nethackagent.scraping.WikiScraper;
import com.github.martinfrank.nethackagent.tools.wiki.WhiteList;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.document.splitter.DocumentByRegexSplitter;
import dev.langchain4j.data.document.splitter.DocumentBySentenceSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WikiIngestor {

    private static final Logger logger = LoggerFactory.getLogger(WikiIngestor.class);
    private final LlmModelService llmModelService;
    private final WikiDocumentService documentService;

    @Autowired
    public WikiIngestor(LlmModelService llmModelService, WikiDocumentService documentService) {
        this.llmModelService = llmModelService;
        this.documentService = documentService;
    }

    public void ingest(String url) {
        logger.info("ingest {}", url);
        if (!WhiteList.isWhiteListed(url)) {
            logger.warn("website {} is not witelisted", url);
            throw new IllegalArgumentException("website " + url + " is not witelisted...");
        }

        //get the md document
        WikiScraper scraper = new WikiScraper();
        String mdDocument = scraper.scrape(url);
        logger.info("mdDokument from url:  {}", mdDocument);

        //make the document
        Metadata metaData = MetadataExtractor.createMetadata(url);
        WikiMdDocument document = new WikiMdDocument(url, mdDocument, metaData);

        //speichern in DB
        documentService.save(document);

        //break it down and ingest!
        List<Document> splits = DocumentSplitter.split(mdDocument, url, metaData, 500, 100);
        EmbeddingStoreIngestor ingestor = createIngestorModel();
        ingestor.ingest(splits);
        logger.info("finished ingesting {} documents", splits.size());
    }

    public void ingestFromDb(String url) {
        logger.info("ingest {}", url);
        if (!WhiteList.isWhiteListed(url)) {
            logger.warn("website {} is not witelisted", url);
            throw new IllegalArgumentException("website " + url + " is not witelisted...");
        }

        WikiMdDocument document = documentService.loadUrl(url);
        EmbeddingStoreIngestor ingestor = createIngestorModel();
        ingestor.ingest(document);
    }


    private EmbeddingStoreIngestor createIngestorModel() {
        EmbeddingModel embeddingModel = llmModelService.getEmbeddingModel();
        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();

        DocumentBySentenceSplitter sentenceSplitter = new DocumentBySentenceSplitter(3000, 500);
        DocumentByRegexSplitter regexSplitter = new DocumentByRegexSplitter(
                "(?m)^#{1,6}\\s", "\n\n", 3000, 500, sentenceSplitter
        );

        return EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .documentSplitter(regexSplitter)
                .build();
    }
}
