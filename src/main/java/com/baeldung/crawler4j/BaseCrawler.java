package com.baeldung.crawler4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.WebCrawler;

public abstract class BaseCrawler extends WebCrawler {

    protected List<String> discoveredURLs = new ArrayList<>();

    protected final static Pattern FILTER_FILE_EXTENTIONS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz|java|xml|properties|gitignore|sql|sh|bat|ts|json))$");
    protected final static Pattern FILTERS_DIRECTORIES = Pattern.compile(".*(\\/test\\/|\\/resources|\\/web-inf|\\/commits|\\/blame\\/|\\/webapp\\/).*");    

    @Override
    public List<String> getMyLocalData() {
        return discoveredURLs;
    }
}