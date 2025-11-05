package com.github.martinfrank.nethackagent.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupDocumentProvider implements DocumentProvicer {

    @Override
    public Document load(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .timeout(10000)
                .get();
    }
}
