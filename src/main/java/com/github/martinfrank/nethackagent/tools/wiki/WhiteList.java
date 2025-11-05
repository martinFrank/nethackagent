package com.github.martinfrank.nethackagent.tools.wiki;

import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WhiteList {

    private static List<String> list = new ArrayList<>();

    public static boolean isWhiteListed(String url){
        if (list.isEmpty()){
            loadWhiteList();
        }
        return list.contains(url);
    }

    private static void loadWhiteList() {
        try (InputStream inputStream = WhiteList.class
                .getClassLoader()
                .getResourceAsStream("whitelist.txt");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getWhiteList() {
        if (list.isEmpty()){
            loadWhiteList();
        }
        return Collections.unmodifiableList(list);
    }
}
