package com.github.martinfrank.nethackagent.scraping;

import io.github.furstenheim.CopyDown;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiScraper {

    private final DocumentProvicer documentProvicer;

    public WikiScraper(DocumentProvicer documentProvicer) {
        this.documentProvicer = documentProvicer;
    }

    public WikiScraper() {
        this(new JsoupDocumentProvider());
    }

    public String scrape(String url) {
        try {
            Document doc = documentProvicer.load(url);
            Elements content = getContent(doc);
            removeNeedsContent(content);
            removeUnwantedTags(content);
            replaceLinksWithText(content);
            createTables(content);
            String md = toMarkDown(content.html());
            md = removeCollection(md);
            return md;
        } catch (Exception e) {
            return "Fehler! da hat was nicht geklappt!!";
        }
    }

    private String removeCollection(String document) {
        String searchText = """
                Collection
                ----------
                """;
        int index = document.lastIndexOf(searchText);
        if (index > 0) {
            return document.substring(0, index);
        }
        return document;
    }

    private static void removeNeedsContent(Elements content) {
        Elements elements = content.select(".mw-content-ltr > table:nth-child(1)");
        if (!elements.isEmpty()) {
            elements.forEach(Node::remove);
        }
    }

    private void createTables(Elements content) {
        for (Element table : content.select("table")) {
            String mdTable = htmlTableToMarkdown(table);
            table.replaceWith(new TextNode(mdTable));
        }
    }

    public static String htmlTableToMarkdown(Element table) {
        StringBuilder md = new StringBuilder();
        Elements rows = table.select("tr");

        if (rows.isEmpty()) {
            return "";
        }

        // Kopfzeile aus der ersten Zeile (alle Zellen)
        Elements firstRowCells = rows.getFirst().children();

        md.append("| ");
        for (Element cell : firstRowCells) {
            md.append(cell.text()).append(" | ");
        }
        md.append("\n");

        // Trennlinie mit derselben Spaltenanzahl
        md.append("| ");
        md.append("--- | ".repeat(firstRowCells.size()));
        md.append("\n");

        // Datenzeilen ab 1, alle Zellen (th + td)
        for (int i = 1; i < rows.size(); i++) {
            md.append("| ");
            Elements cells = rows.get(i).children();
            for (Element cell : cells) {
                md.append(cell.text()).append(" | ");
            }
            md.append("\n");
        }

        return md.toString();
    }


    private static String toMarkDown(String html) {
        CopyDown converter = new CopyDown();
        String md = converter.convert(html);

        //io.github.furstenheim.CopyDown hat Probleme mit underscores - hier ist der fix
        Pattern brokenUnderlinePattern = Pattern.compile("\\\\_");
        Matcher matcher = brokenUnderlinePattern.matcher(md);
        String fixedUnterlines = matcher.replaceAll("_");

        //remove needs content:
        return fixedUnterlines.replaceAll("\\| \\|", "| \n|");
    }

    private static void replaceLinksWithText(Elements elements) {
        Elements links = elements.select("a[href]");
        for (Element link : links) {
            String text = link.text();
            String href = link.attr("href");

            //full qualified urls
            if (href.startsWith("/")) {
                href = "(https://wiki.kingdomofloathing.com" + href + ")";
            }

            //remove internal links
            if (href.contains("#")) {
                href = "";
            }

            if (href.contains("File:")) {
                href = "";
            }

            // Ersetze den Link im Dokument durch Text plus URL in Klammern
            link.replaceWith(new TextNode(text + href));
        }
    }

    private static void removeUnwantedTags(Elements elements) {
        List<String> unwantedTags = List.of("script", "style", "nav", "footer", "aside", "figure", "input");
        for (String unwantedTag : unwantedTags) {
            elements.select(unwantedTag).forEach(Node::remove);
        }
    }

    private static Elements getContent(Document doc) {
        //alles weg ausser 'Main Page' (also alles dekoration aussen rum) bzw. 'Page' page
        return doc.selectXpath("//*[@id=\"mw-content-text\"]");
    }
}
