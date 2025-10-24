package com.github.martinfrank.nethackagent.embedding;

import com.github.martinfrank.nethackagent.OpenAiConfig;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;

import java.util.List;
import java.util.Set;

public class KolWikiScraper {

    public static void main(String[] args) {
        new KolWikiScraper().start();
    }

    public void start() {

        var embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(OpenAiConfig.OPENAI_API_KEY)
                .modelName("text-embedding-3-small")
                .build();

        EmbeddingStore store = EmbeddingFactory.createEmbeddingStore();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .documentSplitter(DocumentSplitters.recursive(500, 100))
                .build();

        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Main_Page";
        WebCrawler crawler = new WebCrawler();
        crawler.crawl(kolWikiHomepage, 16);
        Set<String> urls = crawler.getVisited();
        List<String> urlsWithoutFiles = urls.stream().filter(f -> ! f.contains("File:")).toList();

        for (String url : urlsWithoutFiles) {
            try {
                Document wikiDoc = UrlDocumentLoader.load(url, new TextDocumentParser());
                ingestor.ingest(wikiDoc);
                System.out.println("sucessfully read url " + url);
            } catch (Exception _) {
                System.out.println("error reading url " + url);
            }
        }
    }
}
