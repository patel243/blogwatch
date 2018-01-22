package com.baeldung.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
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
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

@ExtendWith(SpringExtension.class)
public class CommonUITest extends BaseUITest {

    @Test
    @Tag(GlobalConstants.TAG_WEEKY)
    public final void givenTheSampleArticleList_whenArticleLoads_thenIthasContent() throws IOException {
        try (Stream<String> sampleArticlesList = Utils.fetchSampleArtilcesList()) {
            sampleArticlesList.forEach(URL -> {
                page.setUrl(page.getBaseURL() + URL);

                page.loadUrlWithThrottling();

                assertTrue("Page found with no content div. URL->" + URL, page.findContentDiv().isDisplayed());
            });
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)    
    public final void givenArticleWithPopup_whenPopupOpens_thenPopupHasCloseButton() {
        if (page.isLaunchFlag()) {
            return;
        }
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_WITH_POPUP);

        page.loadUrl();
        logger.info("Testing popup close button on-->"+page.getBaseURL() + GlobalConstants.ARTICLE_WITH_POPUP);

        WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 60);
        wait.until(ExpectedConditions.visibilityOf(page.findPopupCloseButton()));
    }

    // <pre> tags in article generates HTML table with div having value either 1 or blank or space
    @Test
    @Tag(GlobalConstants.TAG_MONTHLY)
    public final void givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv() throws IOException {
        List<String> urlsWithNoContent = new ArrayList<String>();
        try (Stream<String> allArticlesList = Utils.fetchAllArtilcesList()) {
            allArticlesList.forEach(URL -> {
                page.setUrl(page.getBaseURL() + URL);

                page.loadUrlWithThrottling();

                if (page.findEmptyDivs().size() > 0) {
                    urlsWithNoContent.add(URL);
                    logger.info("Page found with empty DIV. URL-->" + URL);
                }
            });
        }

        if (urlsWithNoContent.size() > 0) {
            fail("URL with No content--->" + urlsWithNoContent.stream().collect(Collectors.joining("\n")));
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenThePagesWithBlankTitle_whenPageLoads_thenItDoesNotContainNotitleText() {
        GlobalConstants.PAGES_WITH_BLANK_TITLE.forEach(url -> {
            page.setUrl(page.getBaseURL() + url);

            page.loadUrl();

            assertFalse("page found with 'No Title' in body-->" + url, page.pagesWithNotitleTextInBody().size() > 0);
        });
    }

    @Test
    @Tag(GlobalConstants.TAG_BI_MONTHLY)
    public final void givenAllArticlesURLs_whenArticleLoads_thenItReturns200OK() throws IOException {
        List<String> badURls = new ArrayList<String>();
        try (Stream<String> allArticlesList = Utils.fetchAllArtilcesList()) {
            allArticlesList.forEach(URL -> {
                if (HttpStatus.SC_OK != RestAssured.given().get(page.getBaseURL() + URL).getStatusCode()) {
                    badURls.add(URL);
                }

            });
        }

        if (badURls.size() > 0) {
            fail("200OK Not received from URLs--->" + badURls.stream().collect(Collectors.joining("\n")));
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_BI_MONTHLY)
    public final void givenAllPagesURLs_whenPageLoads_thenItReturns200OK() throws IOException {
        List<String> badURls = new ArrayList<String>();
        try (Stream<String> allArticlesList = Utils.fetchAllPagesList()) {
            allArticlesList.forEach(URL -> {
                if (HttpStatus.SC_OK != RestAssured.given().get(page.getBaseURL() + URL).getStatusCode()) {
                    badURls.add(URL);
                }                
            });
        }
        
        if (badURls.size() > 0) {
            fail("200OK Not received from URLs--->" + badURls.stream().collect(Collectors.joining("\n")));
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheArticleWithSeries_whenArticleLoads_thenPluginLoadsProperly() {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_WITH_SERIES);

        page.loadUrl();

        assertTrue(page.seriesPluginElementDisplayed());

    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheArticleWithPersistenceEBookDownload_whenPageLoads_thenFooterImageIsDisplayed() {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_WITH_PESISTENCE_EBOOK_DOWNLOAD);

        page.loadUrl();

        page.getPathOfPersistenceEBookImages().forEach(image -> {
            assertEquals(200, RestAssured.given().get(image.getAttribute("src")).getStatusCode());
        });

    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheArticleWithGoogleAnalytics_whenArticleLoads_thenArticleHasAnalyticsCode() {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_WITH_GOOGLE_ANALYTICS);

        page.loadUrl();

        List<WebElement> allScriptTags = page.getAllScriptTags();
        AtomicInteger analyticsCodeCount = new AtomicInteger();
        allScriptTags.forEach(scriptTag -> {
            if (scriptTag.getAttribute("innerHTML").contains(GlobalConstants.GOOGLE_ANALYTICS_CODE_SEARCH_STRING)) {
                analyticsCodeCount.getAndIncrement();
            }
        });

        assertTrue("Analytics code count-->" + analyticsCodeCount.get(), analyticsCodeCount.get() == 1);
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenThePageWithGoogleAnalytics_whenPageLoads_thenPageHasAnalyticsCode() {
        page.setUrl(page.getBaseURL() + GlobalConstants.PAGE_WITH_GOOGLE_ANALYTICS);

        page.loadUrl();

        List<WebElement> allScriptTags = page.getAllScriptTags();
        AtomicInteger analyticsCodeCount = new AtomicInteger();
        allScriptTags.forEach(scriptTag -> {
            if (scriptTag.getAttribute("innerHTML").contains(GlobalConstants.GOOGLE_ANALYTICS_CODE_SEARCH_STRING)) {
                analyticsCodeCount.getAndIncrement();
            }
        });

        assertTrue("Analytics code count-->" + analyticsCodeCount.get(), analyticsCodeCount.get() == 1);
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenBaeldungFeedUrl_whenUrlIsHit_thenItRedirectsToFeedburner() {
        Response response = RestAssured.given().redirects().follow(false).get(GlobalConstants.BAELDUNG_FEED_URL);

        assertEquals(302, response.getStatusCode());
        assertEquals(GlobalConstants.BAELDUNG_FEED_FEEDBURNER_URL, response.getHeader("Location").replaceAll("/$", ""));
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheCategoryPage_whenPageLoads_thenItContainsNoindexRobotsMeta() {
        page.setUrl(page.getBaseURL() + GlobalConstants.CATEGORY_URL);

        page.loadUrl();

        assertTrue(page.metaWithRobotsNoindexEists());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheTagArticle_whenArticleLoads_thenItContainsNoindexRobotsMeta() {
        page.setUrl(page.getBaseURL() + GlobalConstants.TAG_ARTICLE_URL);

        page.loadUrl();

        assertTrue(page.metaWithRobotsNoindexEists());
    }

}
