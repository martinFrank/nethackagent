package com.github.martinfrank.nethackagent.embedding;

import dev.langchain4j.data.document.Metadata;

import java.util.List;


public class MetadataExtractor {

    public static Metadata createMetadata(String url) {
        String documentName = extractUrl(url);
        List<String> context = List.of(documentName, "KoL","Kingdom of Loathing", "KoL Wiki", "Kingdom of Loathing Wiki");

        Metadata metadata = new Metadata();
        metadata.put("url", url);
        metadata.put("format", "markdown");
        metadata.put("game", "kingdom of loathing");
        metadata.put("topic", documentName);
        metadata.put("context", context.toString());
        return metadata;
    }

    static String extractUrl(String url) {
        int lastOf = url.lastIndexOf("/");
        String pagename = url.substring(lastOf+1);
        pagename = pagename.replaceAll("_", " ");
        pagename = pagename.replaceAll("%22", "\"");
        pagename = pagename.replaceAll("%25", "%");
        pagename = pagename.replaceAll("%25", "%");
        pagename = pagename.replaceAll("%3F", "?");
        pagename = pagename.replaceAll("%27", "'");
        pagename = pagename.replaceAll("%E2%84%A2", "™");
        pagename = pagename.replaceAll("%C3%A8", "è");
        pagename = pagename.replaceAll("%C3%A9", "é");
        return pagename;
    }

}
