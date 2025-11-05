package com.github.martinfrank.nethackagent.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

    private final Set<String> visited;

    public WebCrawler(Set<String> visited) {
        this.visited = visited;
    }

    public WebCrawler() {
        this(new HashSet<>());
    }

    public void crawl(String url, int depth) {

        if (depth <= 0 || visited.contains(url)) {
            return;
        }

        try {
            logger.info("size = {}  still Crawling: {}", visited.size(), url);
            visited.add(url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .timeout(10000)
                    .get();

            //alle urls dieser Seite crawlen (baer: mit depth-1, damit es nicht ewig tief geht)
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String next = link.absUrl("href");
                //alle? nicht alle! nur Seiten, die von der haputseite abzweigen, ekine external links verfolgen
                if (next.startsWith(url) || next.startsWith(getBaseUrl(url))) {
                    crawl(next, depth - 1);
                }
            }
        } catch (IOException e) {
            logger.error("Fehler beim Laden: {} -> {}", url, e.getMessage());
        }
    }

    public static boolean isFiltered(String url) {
        List<Pattern> goodPatterns = List.of(
                Pattern.compile("https://.*/Category:.*")
                , Pattern.compile("https://.*/.Category:.*"));
        List<Pattern> badPatterns = List.of(
                Pattern.compile("https://.*/.*#.*")
                , Pattern.compile("https://.*/.*:.*")
                , Pattern.compile("https://.*/index.php.*"));

        boolean isGood = goodPatterns.stream().anyMatch(p -> p.matcher(url).matches());
        boolean isBad = badPatterns.stream().anyMatch(p -> p.matcher(url).matches());
        if (isBad && isGood) {
            return false;
        }
        return isBad;
    }

    private String getBaseUrl(String url) {
        int idx = url.indexOf("/", 8); // nach https://
        return (idx != -1) ? url.substring(0, idx) : url;
    }

    public Set<String> getVisited() {
        return visited;
    }
}
