package org.baeldung.home;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.baeldung.config.MainConfig;
import org.baeldung.site.base.SitePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
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
    
    @Before
    public void loadNewWindow() {
        page.openNewWindow();
    }

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
                    page.loadPage();
                    assertTrue(page.findContentDiv().isDisplayed());                                        
                } catch (Exception e) {
                    urlsWithNoContent.add(page.getBaseURL() + URL);                    
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
            page.loadPage();                       
            WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 40);
            wait.until(ExpectedConditions.visibilityOf(page.findPopupCloseButton()));                        
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } 
    }
    
    //<pre> tags in article generates HTML table with div having value either 1 or blank or space
    @Test
    public final void whenPageLods_thenNoEmptyDivs() {
        
        Stream<String> URLs = null;
        try {
            List<String> urlsWithEmptyDivs = new ArrayList<String>();
            File file = new File(getClass().getClassLoader().getResource("url-list-to-check-content.txt").getPath());
            URLs = Files.lines(Paths.get(file.getAbsolutePath()));            
            URLs.forEach(URL -> {
                try {              
                    System.out.println("Page="+URL);
                    page.setPageURL(page.getBaseURL() + URL);
                    page.loadPage();
                    List<WebElement>  potentiallyEmptyDivs = page.findPotentiallyEmptyDivs();
                    potentiallyEmptyDivs.forEach(webElement->{
                        //System.out.println("value="+webElement.getText()+"=");
                       // assertFalse(webElement.getText().equals(GlobalConstants.NUMBER_ONE));
                        assertFalse(StringUtils.isBlank(webElement.getText().trim()));               
                    });                                     
                } catch (Exception e) {
                    urlsWithEmptyDivs.add(page.getBaseURL() + URL);                   
                }
            });
            if (urlsWithEmptyDivs.size() > 0) {
                Assert.fail("URL with No content--->" + urlsWithEmptyDivs.stream().collect(Collectors.joining("\n")));
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {           
            if (null != URLs) {
                URLs.close();
            }
        }                
    }
    
    @After
    public void closeWindow() {
        page.quiet();
    }    
}
