package com.github.martinfrank.nethackagent.embedding;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;

public class WikiMdDocument implements Document {

    private final String url;
    private final String mdContent;
    private final Metadata metadata;

    public WikiMdDocument(String url, String mdContent, Metadata metadata) {
        this.url = url;
        this.mdContent = mdContent;
        this.metadata = metadata;
    }

    @Override
    public String text() {
        return mdContent;
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }
}
