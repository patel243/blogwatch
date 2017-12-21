package org.baeldung.home;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.baeldung.config.GlobalConstants;
import org.baeldung.config.MainConfig;
import org.baeldung.site.base.SitePage;
import org.baeldung.util.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MainConfig.class })
public class ContentOnPageUITest {

    @Autowired
    SitePage page;

    @BeforeEach
    public void loadNewWindow() {
        page.openNewWindow();
    }

    @Test
    @Tag(GlobalConstants.TAG_MULTI_URL)
    public final void whenPageLoads_thenContentDivExists() throws IOException {
        List<String> urlsWithNoContent = new ArrayList<String>();
        try (Stream<String> sampleArticlesList = Utils.fetchSampleArtilcesList()) {
            sampleArticlesList.forEach(URL -> {
                try {
                    System.out.println(URL);
                    page.setPageURL(page.getBaseURL() + URL);
                    page.loadPageWithThrottling();
                    assertTrue(page.findContentDiv().isDisplayed());
                } catch (Exception e) {
                    urlsWithNoContent.add(page.getBaseURL() + URL);
                }
            });
            if (urlsWithNoContent.size() > 0) {
                fail("URL with No content--->" + urlsWithNoContent.stream().collect(Collectors.joining("\n")));
            }
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenPageWithPopup_thenPopupHasCloseButton() {
        if (page.isLaunchFlag()) {
            return;
        }
        page.setPageURL(page.getBaseURL() + "/rest-with-spring-series/");
        page.loadPage();
        WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 40);
        wait.until(ExpectedConditions.visibilityOf(page.findPopupCloseButton()));
    }

    // <pre> tags in article generates HTML table with div having value either 1 or blank or space
    @Test
    @Tag(GlobalConstants.TAG_MULTI_URL)
    public final void whenPageLods_thenNoEmptyDivs() throws IOException {
        try (Stream<String> sampleArticlesList = Utils.fetchSampleArtilcesList()) {
            sampleArticlesList.forEach(URL -> {
                try {
                    page.setPageURL(page.getBaseURL() + URL);
                    page.loadPageWithThrottling();
                    List<WebElement> potentiallyEmptyDivs = page.findPotentiallyEmptyDivs();
                    potentiallyEmptyDivs.forEach(webElement -> {
                        // System.out.println("value="+webElement.getText()+"=");
                        // assertFalse(webElement.getText().equals(GlobalConstants.NUMBER_ONE));
                        assertFalse(StringUtils.isBlank(webElement.getText().trim()));
                    });
                } catch (Exception e) {
                    //
                }
            });
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void givePageWithNoTitle_whenPageLoads_thenItDoesNotContainNotitleText() {
        page.setPageURL(page.getBaseURL() + "/java-weekly-sponsorship/");
        page.loadPage();
        List<WebElement> pageWithNoTitleInBody = page.pagesWithNotitleTextInBody();
        if (pageWithNoTitleInBody.size() > 0) {
            fail("Page found with '[No Title]: ID' text in body, URL:->" + page.getPageURL());
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_ALL_URLS)
    public final void givenAlltheArticleURLs_whenArtilesLoads_thenPageLoadsSuccessfully() throws IOException {
        page.configureImplicitWait(0, TimeUnit.MICROSECONDS);
        List<String> badURLs = new ArrayList<String>();
        try (Stream<String> allArticlesList = Utils.fetchAllArtilcesList()) {
            allArticlesList.forEach(URL -> {
                try {
                    page.setPageURL(page.getBaseURL() + URL);
                    page.loadPageWithThrottling();
                    if (page.findPageNotFoundElement().isDisplayed()) {
                        badURLs.add(page.getBaseURL() + URL);
                    }
                } catch (NoSuchElementException e) {
                    //
                } catch (Exception e) {
                    badURLs.add(page.getBaseURL() + URL);
                }
            });
            if (badURLs.size() > 0) {
                fail("URLs found with potential 404--->" + badURLs.stream().collect(Collectors.joining("\n")));
            }
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_ALL_URLS)
    public final void givenAllthePageURUsL_whenPageLoads_thenPageLoadsSuccessfully() throws IOException {
        page.configureImplicitWait(0, TimeUnit.MICROSECONDS);
        List<String> badURLs = new ArrayList<String>();
        try (Stream<String> allArticlesList = Utils.fetchAllPagesList()) {
            allArticlesList.forEach(URL -> {
                try {
                    page.setPageURL(page.getBaseURL() + URL);
                    page.loadPageWithThrottling();
                    if (page.findPageNotFoundElement().isDisplayed()) {
                        badURLs.add(page.getBaseURL() + URL);
                    }
                } catch (NoSuchElementException e) {
                    //
                } catch (Exception e) {
                    badURLs.add(page.getBaseURL() + URL);
                }
            });
            if (badURLs.size() > 0) {
                fail("URLs found with potential 404--->" + badURLs.stream().collect(Collectors.joining("\n")));
            }
        }
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }
}
