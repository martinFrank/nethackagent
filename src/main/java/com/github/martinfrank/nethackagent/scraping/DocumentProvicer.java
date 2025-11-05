package com.github.martinfrank.nethackagent.scraping;

import org.jsoup.nodes.Document;

public interface DocumentProvicer {

    Document load(String url) throws Exception;
}
