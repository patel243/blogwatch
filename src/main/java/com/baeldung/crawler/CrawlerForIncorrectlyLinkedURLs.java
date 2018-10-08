package com.baeldung.crawler;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.Page;

public class CrawlerForIncorrectlyLinkedURLs extends BaseCrawler {

    private List<String> flaggedURLs = new ArrayList<>();    

    @Override
    public void visit(Page page) {        
        if (page.getWebURL().getURL().toLowerCase().endsWith("readme.md")) {
            System.out.println(page.getWebURL().getURL());
            this.flaggedURLs.add(page.getWebURL().getURL());
        }
    }

    public List<String> getFlaggedURLs() {
        return flaggedURLs;
    }

    @Override
    public List<String> getMyLocalData() {
        return flaggedURLs;
    }
}
