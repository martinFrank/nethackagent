package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.tools.wiki.WhiteList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestFileReader {

    public static String read(String filename) {
        List<String> content = readLines(filename);
        return String.join("", content);
    }


    public static List<String> readLines(String filename) {
        List<String> content = new ArrayList<>();
        try (InputStream inputStream = WhiteList.class
                .getClassLoader()
                .getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }
}
