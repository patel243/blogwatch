package com.baeldung.crawler;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.config.context.MyApplicationContextInitializer;
import com.baeldung.crawler.config.CrawlerMainCofig;

@ContextConfiguration(classes = { CrawlerMainCofig.class }, initializers = MyApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
public class TutorialRepoUITest {
    
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    TutorialsRepoCrawlerController tutorialsRepoCrawlerController;
    
    @Test
    public final void test1() {
        System.out.println(new Date());
        tutorialsRepoCrawlerController.startCrawler(CrawlerForIncorrectlyLinkedURLs.class,4);
        //List<String> urls = tutorialsRepoCrawlerController.getFlaggedURL();
        System.out.println("============================================================================");
        for(Object object : tutorialsRepoCrawlerController.getFlaggedURL()) {
            List<String> urlList = (List<String>) object;
            System.out.println("List Size:"+ urlList.size());
            urlList.forEach( s->System.out.println(s));
        }        
        
        System.out.println("Parsed pages:" + BaseCrawler.urlParsed);
        System.out.println("Visited pages:" + CrawlerForIncorrectlyLinkedURLs.visitedPages);
        System.out.println(new Date());
    }

}
