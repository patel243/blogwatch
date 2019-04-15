package com.baeldung.crawler4j.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.baeldung.crawler4j.crawler.BaseCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public abstract class BaseCrawlController {

    protected static final Logger logger = LoggerFactory.getLogger(BaseCrawlController.class);

    CrawlController crawlController;

    List<String> seedURLs;

    @Value("${base.url}")
    private String baseURL;

    @Autowired
    ApplicationContext applicationContext;

    /**
     *  start crawl with thread count supplied in numberOfCrawlers
     * @param configClass
     * @param numberOfCrawlers
     */
    public void startCrawler(Class<? extends BaseCrawler> configClass, int numberOfCrawlers) {
        addSeedURLsForCrawl();
        this.crawlController.start(configClass, numberOfCrawlers);
    }

    /**
     *  reset the controller - deletes the storage folder
     *  start crawl with thread count supplied in numberOfCrawlers
     * @param configClass
     * @param numberOfCrawlers
     */
    public void startCrawlingWithAFreshController(Class<? extends BaseCrawler> configClass, int numberOfCrawlers) {        
        crawlController = applicationContext.getBean(CrawlController.class);
        addSeedURLsForCrawl();
        this.crawlController.start(configClass, numberOfCrawlers);
    }

    private void addSeedURLsForCrawl() {
        if (CollectionUtils.isNotEmpty(seedURLs)) {
            seedURLs.forEach(this.crawlController::addSeed);
        }
    }

    protected List<String> getSeedURLs() {
        return seedURLs;
    }

    protected void setSeedURLs(List<String> seedURLs) {
        this.seedURLs = seedURLs;
    }

    protected List<Object> getCrawlersLocalData() {
        return this.crawlController.getCrawlersLocalData();
    }

    public void shutdownCrawler() {
        this.crawlController.shutdown();
        this.crawlController.waitUntilFinish();
    }

    public String getBaseURL() {
        return baseURL;
    }

}
