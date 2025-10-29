package com.github.martinfrank.nethackagent.tools.wiki;

import com.github.martinfrank.nethackagent.OpenAiConfig;
import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WikiTool {


    @Tool(
            name = "WikiTool",
            value = """
            diese Tool kann Detail-Informationen zum Spiel liefern. Es ist ein online
            Tool, das Informationen aus der "Kingdom of Loathing" Wiki Seite
            holen kann. Es kann Informationen über Items, Skills und Adventure liefern.
            Dazu wird der name des Skills, bzw name des Items bzw, name des Adventures
            als searchTerm verwendet.
            
            Das Resultat wird direkt in das Embedding geschrieben und steht dir
            somit direkt zur Verfügung. Der Rückgabewert ist true, falls das
            embedding funktioniert hat, der Rückgabewert ist fals, wenn es Probleme
            gegeben hat.
            """
    )
    public boolean kolWikiSearch(String searchTerm) {
        System.out.println("KoL Search Tool: searching for '"+searchTerm+"'");
//        String preparedSearchTerm = searchTerm.replace("\\s*", "%20");
//        String url = "https://wiki.kingdomofloathing.com/index.php?search="+searchTerm;
        String startUrl = "https://kol.coldfront.net/thekolwiki/index.php?search="+searchTerm+"&title=Special%3ASearch&go=Go";
        System.out.println("startUrl "+startUrl);


        var embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(OpenAiConfig.OPENAI_API_KEY)
                .modelName("text-embedding-3-small")
                .build();

        EmbeddingStore store = EmbeddingFactory.createEmbeddingStore();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .documentSplitter(DocumentSplitters.recursive(500, 100))
                .build();

        System.out.println("ready to read!!");

        try {

            String url = getFinalRedirectedUrl(startUrl);
            System.out.println("url "+url);

            Document wikiDoc = UrlDocumentLoader.load(url, new TextDocumentParser());
            ingestor.ingest(wikiDoc);
            return true;
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
        return false;
    }


    public static String getFinalRedirectedUrl(String url) throws IOException {
        HttpURLConnection connection;
        String newUrl = url;

        while (true) {
            connection = (HttpURLConnection) new URL(newUrl).openConnection();
            connection.setInstanceFollowRedirects(false); // automatische Weiterleitung ausschalten
            connection.connect();

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_MOVED_PERM ||
                    status == HttpURLConnection.HTTP_MOVED_TEMP ||
                    status == HttpURLConnection.HTTP_SEE_OTHER) {

                String location = connection.getHeaderField("Location");
                newUrl = location.startsWith("http") ? location : new URL(new URL(newUrl), location).toString();
            } else {
                break;
            }
        }

        return newUrl;
    }

}
