package com.baeldung.crawler4j.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CodeCrawlerController extends BaseCrawlController {

    public List<Object> getDiscoveredJacaConstructs() {
        return this.getCrawlersLocalData();
    }

    public void setSeedURL(String url) {
        this.setSeedURLs(Arrays.asList(new String[] { url }));
    }
}
