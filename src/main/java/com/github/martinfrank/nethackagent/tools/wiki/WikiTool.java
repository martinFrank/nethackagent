package com.github.martinfrank.nethackagent.tools.wiki;

import com.github.martinfrank.nethackagent.embedding.WikiDocumentService;
import com.github.martinfrank.nethackagent.embedding.WikiMdDocument;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WikiTool {

    private static final Logger logger = LoggerFactory.getLogger(WikiTool.class);
    private final WikiDocumentService websiteService;

    @Autowired
    public WikiTool(WikiDocumentService websiteService) {
        this.websiteService = websiteService;
    }

    @Tool(name = "WikiTool",
            value = """
                    mit diesem Tool kannst du direkt auf externe Wiki-Links von Kingdom of Loathing zugreifen. Verwende
                    dieses tool, um den Inhalt der Url auszulesen.
                    """)
    public String lookupPage(String url) {
        logger.info("using WikiTool({})", url);
        WikiMdDocument mdDocument = websiteService.loadUrl(url);
        logger.debug(mdDocument.text());
        return mdDocument.text();
    }

}
