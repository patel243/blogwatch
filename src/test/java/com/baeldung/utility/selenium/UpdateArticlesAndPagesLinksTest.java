package com.baeldung.utility.selenium;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.common.config.CommonConfig;
import com.baeldung.common.config.MyApplicationContextInitializer;
import com.baeldung.selenium.BlogLinksExtractor;
import com.baeldung.selenium.config.SeleniumMainConfig;
import com.baeldung.site.SitePage;

@ContextConfiguration(classes = { CommonConfig.class, SeleniumMainConfig.class }, initializers = MyApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UpdateArticlesAndPagesLinksTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    BlogLinksExtractor blogLinksExtractor;

    @Autowired
    protected SitePage page;

    @BeforeAll
    public void initialize() {
        page.openNewWindow();
    }                            

    @Test
    public final void updateLinksTest() throws IOException, JDOMException {
        blogLinksExtractor.createArticlesList(page.getWebDriver());
        blogLinksExtractor.createPagesList();
    }

    @AfterAll
    public void cleanup() {
       page.quiet();
    }

}