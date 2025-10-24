package com.github.martinfrank.nethackagent.embedding;

 import com.github.martinfrank.nethackagent.embedding.WebCrawler;
 import org.junit.jupiter.api.Test;

 import java.util.List;
 import java.util.Set;

class WebCrawlerTest {

//    @Test
    void testCrawling() {
        WebCrawler crawler = new WebCrawler();
        crawler.crawl("https://kol.coldfront.net/thekolwiki/index.php/Main_Page", 16);
        Set<String> urls = crawler.getVisited();
        List<String> urlsWithoutFiles = urls.stream().filter(f -> ! f.contains("File:")).toList();
        urlsWithoutFiles.forEach(System.out::println);
    }
}