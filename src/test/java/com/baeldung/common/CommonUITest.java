package com.baeldung.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;
import com.baeldung.util.Utils;

@ExtendWith(SpringExtension.class)
public class CommonUITest extends BaseUITest {

    @Test
    @Tag(GlobalConstants.TAG_SAMPLE_ARTICLES)
    public final void whenPageLoads_thenContentDivExists() throws IOException {
        try (Stream<String> sampleArticlesList = Utils.fetchSampleArtilcesList()) {
            sampleArticlesList.forEach(URL -> {
                // System.out.println(URL);
                page.setUrl(page.getBaseURL() + URL);
                page.loadUrlWithThrottling();
                if (!page.findContentDiv().isDisplayed()) {
                    fail("Page found with without content div. URL->" + URL);
                }                
            });
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenPageWithPopup_thenPopupHasCloseButton() {
        String url = "/rest-with-spring-series/";
        if (page.isLaunchFlag()) {
            return;
        }
        page.setUrl(page.getBaseURL() + url);
        page.loadUrl();
        WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 40);
        wait.until(ExpectedConditions.visibilityOf(page.findPopupCloseButton()));
    }

    // <pre> tags in article generates HTML table with div having value either 1 or blank or space
    @Test
    @Tag(GlobalConstants.TAG_SAMPLE_ARTICLES)
    public final void whenPageLods_thenNoEmptyDivs() throws IOException {
        try (Stream<String> sampleArticlesList = Utils.fetchSampleArtilcesList()) {
            sampleArticlesList.forEach(URL -> {
                page.setUrl(page.getBaseURL() + URL);
                page.loadUrlWithThrottling();
                List<WebElement> potentiallyEmptyDivs = page.findPotentiallyEmptyDivs();
                potentiallyEmptyDivs.forEach(webElement -> {
                    // System.out.println("value="+webElement.getText()+"=");
                    // assertFalse(webElement.getText().equals(GlobalConstants.NUMBER_ONE));                    
                    if(StringUtils.isBlank(webElement.getText().trim())) {
                        fail("Page found with empty DIV. URL-->"+ URL);
                    }
                });
            });
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void givePageWithNoTitle_whenPageLoads_thenItDoesNotContainNotitleText() {
        String url = "/java-weekly-sponsorship/";
        page.setUrl(page.getBaseURL() + url);
        page.loadUrl();
        List<WebElement> pageWithNoTitleInBody = page.pagesWithNotitleTextInBody();
        if (pageWithNoTitleInBody.size() > 0) {
            fail("Page found with '[No Title]: ID' text in body, URL:->" + page.getUrl());
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_ALL_ARTICLE)
    public final void givenAllArticlesURLs_whenArticleLoads_thenItDoesNotThrow404() throws IOException {
        page.configureImplicitWait(0, TimeUnit.MICROSECONDS);
        try (Stream<String> allArticlesList = Utils.fetchAllArtilcesList()) {
            allArticlesList.forEach(URL -> {
                page.setUrl(page.getBaseURL() + URL);
                page.loadUrlWithThrottling();
                if (page.pageNotFoundElementDisplayed()) {
                    fail("Article with potential 404-->"+ URL);
                }                
            });
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_ALL_PAGES)
    public final void givenAllPagesURLs_whenPageLoads_thenItDoesNotThrow404() throws IOException {
        page.configureImplicitWait(0, TimeUnit.MICROSECONDS);
        try (Stream<String> allArticlesList = Utils.fetchAllPagesList()) {
            allArticlesList.forEach(URL -> {
                page.setUrl(page.getBaseURL() + URL);
                page.loadUrlWithThrottling();
                if (page.pageNotFoundElementDisplayed()) {
                    fail("Page with potential 404-->"+ URL);
                }                 
            });
        }
    }

}
