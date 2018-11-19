package com.baeldung.selenium.base;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.common.config.CommonConfig;
import com.baeldung.common.config.MyApplicationContextInitializer;
import com.baeldung.crawler4j.GitModulesReadmeLinksExtractor;
import com.baeldung.crawler4j.config.CrawlerMainCofig;
import com.baeldung.crawler4j.controller.TutorialsRepoCrawlerController;
import com.baeldung.selenium.config.SeleniumMainConfig;
import com.baeldung.site.base.SitePage;

@ContextConfiguration(classes = { CommonConfig.class, SeleniumMainConfig.class, CrawlerMainCofig.class }, initializers = MyApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
public class BaseUISeleniumTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SitePage page;

    @Autowired
    protected GitModulesReadmeLinksExtractor gitModulesReadmeLinksExtractor;
    
    @Autowired
    protected TutorialsRepoCrawlerController tutorialsRepoCrawlerController;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindow();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }

}
