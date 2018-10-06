package com.baeldung.crawler;

import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class BaseCrawler extends WebCrawler {

    public static int urlParsed;

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz|java|xml|properties|gitignore|sql|sh|bat|ts))$");
    private final static Pattern FILTERS_OTHERS = Pattern.compile(".*(\\/test\\/|\\/resources|\\/web-inf|\\/commits|\\/blame\\/).*");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        BaseCrawler.urlParsed++;
        String href = url.getURL().toLowerCase();
        // @formatter:off
        return (href.contains("https://github.com/eugenp/tutorials") || href.contains("https://github.com/eugenp/baeldung"))
                && href.contains("/master/")
                && !FILTERS.matcher(href).matches()
                && !FILTERS_OTHERS.matcher(href).matches();
        // @formatter:on
    }

    @Override
    public abstract void visit(Page page);

    public static void main(String[] args) {
        String href = "https://github.com/eugenp/tutorials/tree/master/patterns/design-patterns";
        System.out.println(FILTERS_OTHERS.matcher(href).matches() && FILTERS.matcher(href).matches());
    }

}