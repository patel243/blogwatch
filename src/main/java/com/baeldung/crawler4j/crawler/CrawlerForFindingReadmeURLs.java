package com.baeldung.crawler4j.crawler;

import com.baeldung.GlobalConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlerForFindingReadmeURLs extends BaseCrawler {

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        String referringPageURL = referringPage.getWebURL().getURL();
        // @formatter:off
        return super.commonPredicate(href, referringPageURL)
                && !referringPageURL.contains(GlobalConstants.README_FILE_NAME_LOWERCASE);
        // @formatter:on
    }

    @Override
    public void visit(Page page) {
        if (page.getWebURL().getURL().toLowerCase().endsWith(GlobalConstants.README_FILE_NAME_LOWERCASE)) {
            System.out.println(page.getWebURL().getURL());
            this.discoveredURLs.add(page.getWebURL().getURL());
        }
    }

}
