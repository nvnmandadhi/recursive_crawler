package com.naveen.app;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class Crawler {

    private Set<String> links = new HashSet<>();

    Set<String> getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth > 0))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                final var document = Jsoup.connect(URL).get();
                final var linksOnPage = document.select("a[href]");

                depth--;
                for (final var page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (Exception e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
        return links;
    }

}
