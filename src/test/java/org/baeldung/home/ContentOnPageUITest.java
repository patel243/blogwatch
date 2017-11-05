package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.config.MainConfig;
import org.baeldung.site.base.SitePage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MainConfig.class })
public class ContentOnPageUITest {

    @Autowired
    SitePage blogPage;

    @Test
    public final void whenPageLoads_thenContentDivExists() {

        List<String> URLs = new ArrayList<String>();
        URLs.add("/rest-with-spring-series/");
        URLs.add("/persistence-with-spring-series/");
        try {
            for (String URL : URLs) {
                blogPage.setPageURL(blogPage.getBaseURL() + URL);
                blogPage.openNewWindowAndLoadPage();
                assertTrue(blogPage.findContentDiv().isDisplayed());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        } finally {
            blogPage.quiet();
        }
    }

}
