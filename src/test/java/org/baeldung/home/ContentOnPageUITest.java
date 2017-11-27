package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.baeldung.config.MainConfig;
import org.baeldung.site.base.SitePage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MainConfig.class })
public class ContentOnPageUITest {

    @Autowired
    SitePage page;

    @Test
    public final void whenPageLoads_thenContentDivExists() {
        Stream<String> URLs = null;
        try {
            List<String> urlsWithNoContent = new ArrayList<String>();
            File file = new File(getClass().getClassLoader().getResource("url-list-to-check-content.txt").getPath());
            URLs = Files.lines(Paths.get(file.getAbsolutePath()));
            URLs.forEach(URL -> {
                try {                   
                    page.setPageURL(page.getBaseURL() + URL);
                    page.openNewWindowAndLoadPage();
                    assertTrue(page.findContentDiv().isDisplayed());                    
                    page.quiet();
                } catch (Exception e) {
                    urlsWithNoContent.add(page.getBaseURL() + URL);
                    page.quiet();
                }
            });
            if (urlsWithNoContent.size() > 0) {
                Assert.fail("URL with No content--->" + urlsWithNoContent.stream().collect(Collectors.joining("\n")));
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {
            if (null != URLs) {
                URLs.close();
            }
        }
    }
    
    @Test
    public final void whenPageWithPopup_thenPopupHasCloseButton() {
        try {
            System.out.println(page.isLaunchFlag());
            if (page.isLaunchFlag())
            {
                return;
            }
            page.setPageURL(page.getBaseURL() + "/rest-with-spring-series/");
            page.openNewWindowAndLoadPage();                       
            WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 40);
            wait.until(ExpectedConditions.visibilityOf(page.findPopupCloseButton()));            
            page.quiet();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {
            page.quiet();

        }

    }

}
