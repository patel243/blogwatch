package com.baeldung.crawler4j.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.CrawlController;

@Component
public class TutorialsRepoCrawlerController extends BaseCrawlController {

    @Value("#{'${repo.tutorials}'.split(',')}")
    private List<String> urls;

    public TutorialsRepoCrawlerController(CrawlController crawlController) {
        super(crawlController);
    }

    public List<Object> getDiscoveredURLs() {
        return this.getCrawlersLocalData();
    }

    @PostConstruct
    public void setSeedURLs() {
        this.setSeedURLs(urls);
    }

}
