package com.github.martinfrank.nethackagent.scraping;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WikiScraperTest {

//    @Test
    void testScraping() {
        WikiScraper scraper = new WikiScraper();
//        String content = scraper.scrape("https://wiki.kingdomofloathing.com/Elements");
        String content = scraper.scrape("https://wiki.kingdomofloathing.com/Quest_Spoilers");
        System.out.println("relevant content length: " + content.length());
        System.out.println(content);
    }

}