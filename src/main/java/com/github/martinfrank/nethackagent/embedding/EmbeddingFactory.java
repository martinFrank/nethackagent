package com.github.martinfrank.nethackagent.embedding;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.quarkiverse.langchain4j.pgvector.PgVectorEmbeddingStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmbeddingFactory {

    public static EmbeddingStore<TextSegment> createEmbeddingStore() {
        Properties config = readConfig();
        String host = readHost(config);
        int port = readPort(config);
        String database = readDatabase(config);
        String user = readUser(config);
        String pass = readPass(config);
        String table = readTable(config);
        int dimension = readDimension(config);

        return PgVectorEmbeddingStore.builder()
                .host(readHost(config))
                .port(readPort(config))
                .database(readDatabase(config))
                .user(readUser(config))
                .password(readPass(config))
                .table(readTable(config))
                .dimension(readDimension(config))
                .build();
    }

    public static String readHost(Properties config) {
        String dburl = config.get("spring.datasource.url").toString();
        int start = dburl.indexOf("//") + 2;
        int end = dburl.lastIndexOf(":");
        return dburl.substring(start, end);
    }

    public static int readPort(Properties config) {
        String dburl = config.get("spring.datasource.url").toString();
        int start = dburl.lastIndexOf(":") + 1;
        int end = dburl.lastIndexOf("/");
        return Integer.parseInt(dburl.substring(start, end));
    }

    public static String readDatabase(Properties config) {
        String dburl = config.get("spring.datasource.url").toString();
        int start = dburl.lastIndexOf("/") + 1;
        return dburl.substring(start);
    }

    public static String readUser(Properties config) {
        return config.get("spring.datasource.username").toString();
    }

    public static String readPass(Properties config) {
        return config.get("spring.datasource.password").toString();
    }

    public static String readTable(Properties config) {
        String selection = config.get("langchain4j.model.selector").toString();
        return switch (selection){
            case "ollama" -> config.get("ollama.embedding.table").toString();
            case "openapi" -> config.get("openapi.embedding.table").toString();
            default -> throw new IllegalArgumentException("unsupported model (not one of [ollama,openapi]");
        };
    }

    public static int readDimension(Properties config) {
        String selection = config.get("langchain4j.model.selector").toString();
        return switch (selection){
            case "ollama" -> Integer.parseInt(config.get("ollama.embedding.dimension").toString());
            case "openapi" -> Integer.parseInt(config.get("openapi.embedding.dimension").toString());
            default -> throw new IllegalArgumentException("unsupported model (not one of [ollama,openapi]");
        };
    }

    public static Properties readConfig() {
        Properties props = new Properties();
        try (InputStream is = EmbeddingFactory.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
