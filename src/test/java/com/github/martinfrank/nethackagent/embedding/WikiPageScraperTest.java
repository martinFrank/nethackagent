package com.github.martinfrank.nethackagent.embedding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class WikiPageScraperTest {

    @Test
    void urlTest() throws IOException {
        org.jsoup.nodes.Document jsoupDoc = Jsoup.connect("https://wiki.kingdomofloathing.com/Main_Page")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .timeout(10000).get();

        Elements links = jsoupDoc.select("a[href]");
        System.out.println(jsoupDoc.body().html());

        System.out.println("------------------------------------");

        // Alle hrefs bearbeiten, z.B. relativen Pfad ergänzen
        for (Element link : links) {
            String href = link.attr("href");
            // Beispielbearbeitung: absoluten Pfad erzeugen
            if (href.startsWith("/")) {
                href = "https://wiki.kingdomofloathing.com" + href;
                link.attr("href", href);
            }
        }

        System.out.println(jsoupDoc.body().html());
    }

}