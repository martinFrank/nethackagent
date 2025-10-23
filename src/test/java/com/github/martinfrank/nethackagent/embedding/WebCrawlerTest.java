package com.github.martinfrank.nethackagent.embedding;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WebCrawlerTest {

    @Test
    void testCrawling(){
        WebCrawler crawler = new WebCrawler();
        crawler.crawl("https://kol.coldfront.net/thekolwiki/index.php/Main_Page", 2);
        Set<String> urls = crawler.getVisited();
        urls.forEach(System.out::println);
    }

}