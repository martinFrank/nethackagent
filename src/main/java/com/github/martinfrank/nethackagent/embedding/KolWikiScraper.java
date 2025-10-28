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

//        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Main_Page";
//        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Category:Items";
//        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Elements#google_vignette";
//        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Items_by_Name_(A)";
//        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Beginner%27s_guide";
//        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Game_Mechanics";
        String kolWikiHomepage = "https://kol.coldfront.net/thekolwiki/index.php/Quest_Spoilers";
        WebCrawler crawler = new WebCrawler();
        crawler.crawl(kolWikiHomepage, 2);
        Set<String> urls = crawler.getVisited();
        List<String> urlsWithoutFiles = urls.stream().filter(f -> ! f.contains("File:")).toList();

        for (int i = 0; i < urlsWithoutFiles.size(); i++) {
            String url = urlsWithoutFiles.get(i);
            try {
                Document wikiDoc = UrlDocumentLoader.load(url, new TextDocumentParser());
                ingestor.ingest(wikiDoc);
                System.out.println(i+"/"+urlsWithoutFiles.size()+": sucessfully read url " + url);
            } catch (Exception _) {
                System.out.println("error reading url " + url);
            }
        }
    }
}
