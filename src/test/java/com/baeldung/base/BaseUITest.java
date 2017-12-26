package com.baeldung.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.baeldung.config.context.MainConfig;
import com.baeldung.config.context.MyApplicationContextInitializer;
import com.baeldung.site.base.SitePage;

@ContextConfiguration(classes = { MainConfig.class }, initializers = MyApplicationContextInitializer.class)
public class BaseUITest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SitePage page;

    @BeforeEach
    public void loadNewWindow() {
        page.openNewWindow();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }

}
