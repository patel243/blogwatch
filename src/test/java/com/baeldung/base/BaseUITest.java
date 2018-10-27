package com.baeldung.base;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.common.config.MyApplicationContextInitializer;
import com.baeldung.crawler4j.GitModulesReadmeLinksExtractor;
import com.baeldung.crawler4j.config.CrawlerMainCofig;
import com.baeldung.selenium.config.MainConfig;
import com.baeldung.site.base.SitePage;

@ContextConfiguration(classes = { MainConfig.class, CrawlerMainCofig.class }, initializers = MyApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
public class BaseUITest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SitePage page;

    @Autowired
    protected GitModulesReadmeLinksExtractor gitModulesReadmeLinksExtractor;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindow();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }

}
