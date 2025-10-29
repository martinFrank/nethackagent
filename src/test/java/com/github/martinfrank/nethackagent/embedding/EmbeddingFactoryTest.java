package com.github.martinfrank.nethackagent.embedding;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class EmbeddingFactoryTest {

    @Test
    void testProperties(){
        Properties config = EmbeddingFactory.readConfig();
        System.out.println(EmbeddingFactory.readHost(config));
        System.out.println(EmbeddingFactory.readPort(config));
        System.out.println(EmbeddingFactory.readDatabase(config));
        System.out.println(EmbeddingFactory.readUser(config));
        System.out.println(EmbeddingFactory.readPass(config));
        System.out.println(EmbeddingFactory.readTable(config));
        System.out.println(EmbeddingFactory.readDimension(config));
    }
}