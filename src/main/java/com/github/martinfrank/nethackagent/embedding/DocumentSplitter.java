package com.github.martinfrank.nethackagent.embedding;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;

import java.util.ArrayList;
import java.util.List;

public class DocumentSplitter {

    public static List<Document> split (String mdDoc, String url, Metadata metadata, int maxSize, int overlap){
        List<Document> chunks = new ArrayList<>();

        if (mdDoc == null || mdDoc.isEmpty() || overlap < 0 || overlap >= maxSize) {
            return chunks; // Leere Liste oder ungültige Parameter
        }

        int start = 0;
        int length = mdDoc.length();

        while (start < length) {
            // Bestimme die Endposition des aktuellen Chunks
            int end = Math.min(start + maxSize, length);

            // Extrahiere das Substring für den Chunk
            String chunkText = mdDoc.substring(start, end);

            // Erzeuge neues Document-Objekt von LangChain4j mit dem Chunk-Text
            Document chunkDoc = new WikiMdDocument(chunkText, url, metadata);
            chunks.add(chunkDoc);

            // Verschiebe den Startindex für nächsten Chunk
            // Dabei wird overlap berücksichtigt: Start verschiebt sich um (maxSize - overlap)
            start += (maxSize - overlap);
        }

        return chunks;
    }

}
