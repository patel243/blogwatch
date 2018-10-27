package com.baeldung.crawler4j;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.GlobalConstants;

import edu.uci.ics.crawler4j.crawler.Page;

public class CrawlerForIncorrectlyLinkedURLs extends BaseCrawler {

    private List<String> flaggedURLs = new ArrayList<>();    

    @Override
    public void visit(Page page) {        
        if (page.getWebURL().getURL().toLowerCase().endsWith(GlobalConstants.README_FILE_NAME_LOWERCASE)) {
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
