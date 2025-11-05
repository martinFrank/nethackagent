package com.github.martinfrank.nethackagent.tools.wiki;

import com.github.martinfrank.nethackagent.embedding.WikiDocumentService;
import com.github.martinfrank.nethackagent.embedding.WikiMdDocument;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.document.DocumentSource;
import dev.langchain4j.data.document.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
                    mit diesem Tool kannst du direkt auf externe Links von Kingdom of loathing zugreifen. Verwende
                    dieses tool, um den Inhalt der Url auszulesen. 
                    """)
    public String lookupPage(String url) throws IOException {
        logger.info("lookupPage: {}", url);
        WikiMdDocument mdDocument = websiteService.loadUrl(url);
        logger.info("mdDocument: {}", mdDocument);
        return mdDocument.text();
    }

//    private static final Logger logger = LoggerFactory.getLogger(WikiTool.class);
//
//    @Tool(name = "WikiTool",
//            value = """
//                    mit diesem Tool kannst du Fachartikel zu Kingdom of loathing nachlesen. Verwende dieses tool, um
//                    Details zu einem Thema nachzuschlagen. Die Gelesenen Artikel werden auch in dein Embedding
//                    eingetragen, damit diese Informationen dauerhaft verfügbar sind.
//                    """)
//    public String lookupPage(String url) throws IOException {
//        logger.info("readWebsite {}", url);
//        if (!WhiteList.isWhiteListed(url)) {
//            logger.info("website {} is not witelisted", url);
//            return "webseite ist nicht white-listed";
//        }
//
//        Optional<WebsiteEntity> result = websiteService.findByUrl(url);
//        if (result.isPresent()) {
//            return result.get().toJson();
//        }
//
//        WebsiteEntity newEntry = scrapeWebsite(url);
//        websiteService.save(newEntry);
//        saveSummaryInEmbedding(newEntry);
//        return newEntry.toJson();
//    }
//
//    private void saveSummaryInEmbedding(WebsiteEntity newEntry) {
//        EmbeddingModel embeddingModel = llmModelService.getEmbeddingModel();
//        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();
//        DocumentSource source = createDocument(newEntry.getSummary(), newEntry.getUrl());
//        Document doc = DocumentLoader.load(source, new TextDocumentParser());
//        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
//                .embeddingStore(store)
//                .embeddingModel(embeddingModel)
//                .documentSplitter(DocumentSplitters.recursive(500, 100))
//                .build();
//        ingestor.ingest(doc);
//    }
//
//    private WebsiteEntity scrapeWebsite(String url) throws IOException {
//        org.jsoup.nodes.Document doc = Jsoup.connect(url)
//                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
//                        "(KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
//                .timeout(10000)
//                .get();
//
//        SystemMessage systemMessage = new SystemMessage("""
//                Du bist ein erfahrener Text-Zusammenfasser. Deine Aufgabe ist es, die wesentlichen Inhalte der folgenden
//                Website klar, prägnant und verständlich zusammenzufassen. Achte darauf, dass die Zusammenfassung in gut
//                lesbarem Deutsch verfasst ist und die wichtigsten Themen, Fakten und Aussagen der Website abdeckt, ohne
//                überflüssige Details. Erstelle eine kurze Übersicht, die sowohl den Kerninhalt als auch relevante
//                Schwerpunkte wiedergibt
//                """);
//        UserMessage htmlMessage = new UserMessage(doc.text());
//        String summary = llmModelService.getChatModel().chat(systemMessage, htmlMessage).aiMessage().text();
//        logger.info("summary of {} : {}", url, summary);
//        WebsiteEntity result = new WebsiteEntity();
//        result.setSummary(summary);
//        result.setUrl(url);
//        result.setContent(doc.html());
//        return result;
//    }


    public static DocumentSource createDocument(String content, String url) {
        return new DocumentSource() {
            @Override
            public InputStream inputStream() {
                return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public Metadata metadata() {
                Metadata metadata = new Metadata();
                metadata.put("url", url);
                return metadata;
            }
        };
    }
}
