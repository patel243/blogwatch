package com.baeldung.crawler4j;

import java.util.regex.Pattern;

import com.baeldung.GlobalConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class BaseCrawler extends WebCrawler {    

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz|java|xml|properties|gitignore|sql|sh|bat|ts))$");
    private final static Pattern FILTERS_OTHERS = Pattern.compile(".*(\\/test\\/|\\/resources|\\/web-inf|\\/commits|\\/blame\\/|\\/webapp\\/).*");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {        
        String href = url.getURL().toLowerCase();
        String referringPageURL = referringPage.getWebURL().getURL(); 
        // @formatter:off
        return (href.contains("https://github.com/eugenp/tutorials") || href.contains("https://github.com/eugenp/baeldung"))
                && href.contains("/master/")
                && !FILTERS.matcher(href).matches()
                && !FILTERS_OTHERS.matcher(href).matches()
                && !referringPageURL.contains(GlobalConstants.README_FILE_NAME_LOWERCASE);
        // @formatter:on
    }

    @Override
    public abstract void visit(Page page);    

}