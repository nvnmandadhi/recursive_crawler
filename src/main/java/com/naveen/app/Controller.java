package com.naveen.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RestController
public class Controller {

    private final Crawler crawler;

    @Autowired
    public Controller(Crawler crawler) {
        this.crawler = crawler;
    }

    @GetMapping("/")
    public String home() throws IOException {
        final var pageLinks = crawler.getPageLinks("https://docs.oracle.com/en/java/javase/11/docs/api/java.base/module-summary.html", 10);
        final var file = new File("/Users/naveenmandadhi/Desktop/apps/java/recursive_crawler/src/main/java/com/naveen/app/links.txt");
        final var fileWriter = new FileWriter(file);
        final var bufferedWriter = new BufferedWriter(fileWriter);
        pageLinks.parallelStream().forEach(str -> {
            try {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return String.format("Wrote %d links", pageLinks.size());
    }
}
