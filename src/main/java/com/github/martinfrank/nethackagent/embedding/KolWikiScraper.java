package com.github.martinfrank.nethackagent.embedding;

import com.github.martinfrank.nethackagent.OpenAiConfig;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.document.transformer.jsoup.HtmlToTextDocumentTransformer;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;

import java.util.HashSet;
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

        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .documentSplitter(DocumentSplitters.recursive(500, 100))
                .build();

        List<String> importantPages = List.of(
                "https://kol.coldfront.net/thekolwiki/index.php/Ascension"
                , "https://kol.coldfront.net/thekolwiki/index.php/Category:Booze"
//                , "https://kol.coldfront.net/thekolwiki/index.php/Clans"
                , "https://kol.coldfront.net/thekolwiki/index.php/Class"
                , "https://kol.coldfront.net/thekolwiki/index.php/Elements"
//                , "https://kol.coldfront.net/thekolwiki/index.php/Category:Familiars"
                , "https://kol.coldfront.net/thekolwiki/index.php/Category:Food"
//                , "https://kol.coldfront.net/thekolwiki/index.php/Category:Items"
                , "https://kol.coldfront.net/thekolwiki/index.php/Locations"
                , "https://kol.coldfront.net/thekolwiki/index.php/Game_Mechanics"
//                , "https://kol.coldfront.net/thekolwiki/index.php/Monster_Compendium"
                , "https://kol.coldfront.net/thekolwiki/index.php/Naughty_Sorceress_Quest"
//                , "https://kol.coldfront.net/thekolwiki/index.php/PvP"
                , "https://kol.coldfront.net/thekolwiki/index.php/Quest_Spoilers"
                , "https://kol.coldfront.net/thekolwiki/index.php/Safe_Adventuring"
                , "https://kol.coldfront.net/thekolwiki/index.php/Category:Skills"
//                , "https://kol.coldfront.net/thekolwiki/index.php/Tattoo"
//                , "https://kol.coldfront.net/thekolwiki/index.php/Trophy"
//                , "https://kol.coldfront.net/thekolwiki/index.php/Zapping"
        );

        Set<String> urls = new HashSet<>();
        for (String  url: importantPages) {
            WebCrawler crawler = new WebCrawler(urls);
            crawler.crawl(url, 3);
            urls.addAll(crawler.getVisited());
        }
        List<String> urlsWithoutFiles = urls.stream().filter(f -> ! f.contains("File:")).toList();

        for (int i = 0; i < urlsWithoutFiles.size(); i++) {
            String url = urlsWithoutFiles.get(i);
            System.out.print((i+1)+"/"+urlsWithoutFiles.size()+":" + url);
            try {
                System.out.print(" --> reading");
                Document wikiDoc = UrlDocumentLoader.load(url, new TextDocumentParser());
                System.out.print(" --> parsing");
                Document transformed = new HtmlToTextDocumentTransformer().transform(wikiDoc);
                System.out.print(" --> ingesting");
                ingestor.ingest(transformed);
                System.out.println(" success");
            } catch (Exception _) {
                System.out.println(" failed");
            }
        }
    }
}
