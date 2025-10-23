package com.github.martinfrank.nethackagent.embedding;

import com.github.martinfrank.nethackagent.OpenAiConfig;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkiverse.langchain4j.pgvector.PgVectorEmbeddingStore;

import java.util.ArrayList;
import java.util.List;

public class KolWikiScraper {

    public static void main(String[] args) {
        new KolWikiScraper().start();
    }

    private List<String> processedUrls = new ArrayList<>();

    private static final String KOL_WIKI_HOMEPAGE = "https://kol.coldfront.net/thekolwiki/index.php/Main_Page";

    public void start() {

        var embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(OpenAiConfig.OPENAI_API_KEY)
                .modelName("text-embedding-3-small")
                .build();

        EmbeddingStore store = PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5432)
                .database("nethackagent")
                .user("postgres")
                .password("postgres_secret")
                .table("kol_embeddings")
                .dimension(1536)
                .build();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .build();

        Document wikiDoc = UrlDocumentLoader.load(KOL_WIKI_HOMEPAGE, new TextDocumentParser() );
        ingestor.ingest(wikiDoc);
    }

}
