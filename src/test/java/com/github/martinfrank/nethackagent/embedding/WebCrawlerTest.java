package com.github.martinfrank.nethackagent.embedding;

 import com.github.martinfrank.nethackagent.scraping.WebCrawler;
 import org.junit.jupiter.api.Test;

 import java.util.Set;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;

 import static org.junit.jupiter.api.Assertions.assertFalse;
 import static org.junit.jupiter.api.Assertions.assertTrue;

class WebCrawlerTest {

    @Test
    void testCrawling() {
        WebCrawler crawler = new WebCrawler();
        crawler.crawl("https://wiki.kingdomofloathing.com/Items_by_Name", 3);
        Set<String> urls = crawler.getVisited();
        urls.forEach(System.out::println);
    }

    @Test
    void testFilter() {
        assertFalse(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/Main_Page"));
        assertFalse(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/Category:Food"));
        assertTrue(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/Talk:Main_Page"));
        assertTrue(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/User:Main_Page"));
        assertTrue(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/Ascension#Familiars"));
        assertTrue(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/index.php?title=Template:Editlinks&action=edit"));
        assertTrue(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/Special:SpecialPages"));
        assertTrue(WebCrawler.isFiltered("https://wiki.kingdomofloathing.com/Talk:Deluxe_Mr._Klaw_%22Skill%22_Crane_Game#Likelihood_of_Success"));
    }

    @Test
    public void givenText_whenSimpleRegexMatches_thenCorrect() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo");

        assertTrue(matcher.find());
    }
}