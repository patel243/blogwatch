package com.baeldung.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Configuration
@ComponentScan("com.baeldung.crawler")
@PropertySource({ "classpath:blog.properties" })
public class CrawlerMainCofig {

    @Bean
    public CrawlConfig crawlConfig() {
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder("crawl/root");
        crawlConfig.setPolitenessDelay(1000);
        crawlConfig.setMaxDepthOfCrawling(-1);
        crawlConfig.setMaxPagesToFetch(-1);
        crawlConfig.setIncludeBinaryContentInCrawling(false);
        crawlConfig.setResumableCrawling(false);
        crawlConfig.setMaxDownloadSize(Integer.MAX_VALUE);
        return  crawlConfig;
    }
    
    @Bean
    public PageFetcher pageFetcher() {
        return  new PageFetcher(crawlConfig());       
    }
    
    @Bean
    public RobotstxtConfig robotstxtConfig() {
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        return  robotstxtConfig;
    }   
    
    @Bean
    public RobotstxtServer robotstxtServer() {
        return new RobotstxtServer(robotstxtConfig(), pageFetcher());
    }
    
    @Bean
    public CrawlController crawlController() throws Exception {
        return new CrawlController(crawlConfig(), pageFetcher(), robotstxtServer());
    }

}
