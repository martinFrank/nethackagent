package com.github.martinfrank.nethackagent.scraping;

import com.github.martinfrank.nethackagent.ollama.OllamaModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class KolWikiScraper {

    private static final Logger logger = LoggerFactory.getLogger(KolWikiScraper.class);

    private final OllamaModelFactory modelFactory;

    @Autowired
    public KolWikiScraper(OllamaModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public void initWiki() {
        logger.info("start scraping kol wiki main page");
        ArrayList<String> lines = new ArrayList<>();
        try(InputStream inputStream = KolWikiScraper.class
                .getClassLoader()
                .getResourceAsStream("scrapes.txt")){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

//        List<String> importantPages = List.of(
//                "https://wiki.kingdomofloathing.com/Ascension"
//                , "https://wiki.kingdomofloathing.com/Category:Booze"
////                , "https://wiki.kingdomofloathing.com/Clans"
//                , "https://wiki.kingdomofloathing.com/Class"
//                , "https://wiki.kingdomofloathing.com/Elements"
////                , "https://wiki.kingdomofloathing.com/Category:Familiars"
//                , "https://wiki.kingdomofloathing.com/Category:Food"
////                , "https://wiki.kingdomofloathing.com/Category:Items"
//                , "https://wiki.kingdomofloathing.com/Locations"
//                , "https://wiki.kingdomofloathing.com/Game_Mechanics"
////                , "https://wiki.kingdomofloathing.com/Monster_Compendium"
//                , "https://wiki.kingdomofloathing.com/Quest_Spoilers"
////                , "https://wiki.kingdomofloathing.com/PvP"
//                , "https://wiki.kingdomofloathing.com/Safe_Adventuring"
//                , "https://wiki.kingdomofloathing.com/Category:Skills"
////                , "https://wiki.kingdomofloathing.com/Tattoo"
////                , "https://wiki.kingdomofloathing.com/Trophy"
////                , "https://wiki.kingdomofloathing.com/Zapping"
//        );
//
//        //inkl. parent ^^
//        Set<String> urls = new HashSet<>(Collections.singleton("https://wiki.kingdomofloathing.com/Quest_Spoilers"));
//        for (String  url: urls) {
//            WebCrawler crawler = new WebCrawler();
//            crawler.crawl(url, 3);
//            urls.addAll(crawler.getVisited());
//        }
//
//        urls.forEach(System.out::println);
//
//        List<String> theUrls = Arrays.asList(urls.split("\n"));
//        if(theUrls.size() < 1000){
//            throw new IllegalArgumentException();
//        }

//        EmbeddingModel embeddingModel = modelFactory.createEmbeddingModel();
//        WikiPageScraperTool scraperTool = new WikiPageScraperTool(embeddingModel);
//        for(String url: lines) {
//            scraperTool.scrape(url);
//        }

    }
}
