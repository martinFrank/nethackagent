package com.github.martinfrank.nethackagent.embedding;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkiverse.langchain4j.pgvector.PgVectorEmbeddingStore;

public class EmbeddingFactory {

    public static EmbeddingStore createEmbeddingStore(){
        return PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5432)
                .database("nethackagent")
                .user("postgres")
                .password("postgres_secret")
                .table("kol_embeddings")
                .dimension(1536)
                .build();
    }


}
