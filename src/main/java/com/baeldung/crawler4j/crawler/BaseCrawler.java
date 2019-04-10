package com.baeldung.crawler4j.crawler;

import java.util.regex.Pattern;

import com.baeldung.common.GlobalConstants;
import com.google.common.util.concurrent.RateLimiter;

import edu.uci.ics.crawler4j.crawler.WebCrawler;

public abstract class BaseCrawler extends WebCrawler {

    protected final static Pattern FILTER_FILE_EXTENTIONS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz|xml|properties|gitignore|sql|sh|bat|ts|json))$");
    protected final static Pattern FILTERS_DIRECTORIES = Pattern.compile(".*(\\/resources|\\/web-inf|\\/commits|\\/blame\\/|\\/webapp\\/).*");
    protected RateLimiter rateLimiter = RateLimiter.create(1);

    public boolean commonPredicate(String pageURL, String referringPageURL) {
        // @formatter:off
        return (pageURL.contains(GlobalConstants.REPO_GITHUB_TUTORIALS_LINK) || pageURL.contains(GlobalConstants.REPO_GITHUB_BAELDUNG_LINK)) 
                && pageURL.contains("/master/") 
                && !FILTER_FILE_EXTENTIONS.matcher(pageURL).matches()
                && !FILTERS_DIRECTORIES.matcher(pageURL).matches();
     // @formatter:on
    }
}