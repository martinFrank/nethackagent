package com.github.martinfrank.nethackagent.embedding;

import com.github.martinfrank.nethackagent.tools.wiki.WhiteList;
import org.junit.jupiter.api.Test;

import java.util.List;

class MetadataExtractorTest {

    @Test
    public void test_createMetadataMetadata() {
        List<String> urls = WhiteList.getWhiteList();
        for(String url: urls) {
            String documentName = MetadataExtractor.extractUrl(url);
            System.out.println(documentName);
            if (documentName.contains("%") && !documentName.equals("%monster%")){ //%monster% ist eine ausnahme
                throw new IllegalArgumentException();
            }
        }

    }

}