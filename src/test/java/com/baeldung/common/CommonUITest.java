package com.baeldung.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;
import com.baeldung.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Multimap;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class CommonUITest extends BaseUITest {

    @Test
    @Tag(GlobalConstants.TAG_WEEKLY)
    @Disabled // the test is covered in the AllArticlesUITest.java
    public final void givenTheSampleArticleList_whenArticleLoads_thenIthasContent() throws IOException {
        try (Stream<String> sampleArticlesList = Utils.fetchSampleArtilcesList()) {
            sampleArticlesList.forEach(URL -> {
                page.setUrl(page.getBaseURL() + URL);

                page.loadUrlWithThrottling();

                assertTrue("Page found with no content div. URL->" + URL, page.isContentDivDisplayed());
            });
        }
    }

    @Ignore
    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenArticleWithPopup_whenPopupOpens_thenPopupHasCloseButton() {
        if (page.isLaunchFlag()) {
            return;
        }
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_WITH_POPUP);

        page.loadUrl();
        logger.info("Testing popup close button on-->" + page.getBaseURL() + GlobalConstants.ARTICLE_WITH_POPUP);

        WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 60);
        wait.until(ExpectedConditions.visibilityOf(page.findPopupCloseButton()));
    }

    // <pre> tags in article generates HTML table with div having value either 1 or blank or space
    @Test
    @Tag(GlobalConstants.TAG_MONTHLY)
    @Disabled
    public final void givenAllTheArticles_whenArticleLoads_thenArticleHasNoEmptyDiv() throws IOException {
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

            assertFalse("page found with 'No Title' in body-->" + url, page.getCountOfElementsWithNotitleText() > 0);
        });
    }

    @Test
    @Tag(GlobalConstants.TAG_WEEKLY)
    public final void givenAllArticlesURLs_whenArticleLoads_thenItReturns200OK() throws IOException {
        List<String> badURls = new ArrayList<String>();
        try (Stream<String> allArticlesList = Utils.fetchAllArtilcesList()) {
            allArticlesList.forEach(URL -> {
                if (HttpStatus.SC_OK != RestAssured.given().head(page.getBaseURL() + URL).getStatusCode()) {
                    badURls.add(URL);
                }

            });
        }

        if (badURls.size() > 0) {
            fail("200OK Not received from URLs--->" + badURls.stream().collect(Collectors.joining("\n")));
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_WEEKLY)
    public final void givenAllPagesURLs_whenPageLoads_thenItReturns200OK() throws IOException {
        List<String> badURls = new ArrayList<String>();
        try (Stream<String> allArticlesList = Utils.fetchAllPagesList()) {
            allArticlesList.forEach(URL -> {
                if (HttpStatus.SC_OK != RestAssured.given().head(page.getBaseURL() + URL).getStatusCode()) {
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
            assertEquals(200, RestAssured.given().head(image.getAttribute("src")).getStatusCode());
        });

    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheArticleWithGoogleAnalytics_whenArticleLoads_thenArticleHasAnalyticsCode() {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_WITH_GOOGLE_ANALYTICS);

        page.loadUrl();

        assertTrue(page.getAnalyticsScriptCount() == 1);
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenThePageWithGoogleAnalytics_whenPageLoads_thenPageHasAnalyticsCode() {
        page.setUrl(page.getBaseURL() + GlobalConstants.PAGE_WITH_GOOGLE_ANALYTICS);

        page.loadUrl();

        assertTrue(page.getAnalyticsScriptCount() == 1);
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

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    @Tag(GlobalConstants.GA_TRACKING)
    public final void givenOnTheCoursePage_whenPageLoads_thenTrackingIsSetupCorrectly() throws JsonProcessingException, IOException {

        Multimap<String, List<String>> testData = Utils.getCoursePagesBuyLinksTestData();
        for (String urlKey : testData.keySet()) {
            page.setUrl(page.getBaseURL() + urlKey);

            page.loadUrl();

            for (List<String> trackingCodes : testData.get(urlKey)) {
                assertTrue("couldn't find custom ga call: " + trackingCodes + " with ga-custom-event class for url-->" + urlKey, page.findAnchorWithGAEventCall(trackingCodes));
            }
            assertTrue("ga-custom-event script not found on -->" + urlKey, page.findGACustomScript());
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    @Disabled
    public final void givenTheArticleWithFixedWidget_whenArticleLoads_thenStopIDIsConfiguredCorrectly() {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_FOR_FIXED_WIDGET_TEST);

        page.loadUrl();

        assertTrue(page.fixedWidgetStopIDIsProvidedAsFooter());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    @Disabled
    public final void givenThePageWithFixedWidget_whenPageLoads_thenStopIDIsConfiguredCorrectly() {
        page.setUrl(page.getBaseURL() + GlobalConstants.PAGE_FOR_FIXED_WIDGET_TEST);

        page.loadUrl();

        assertTrue(page.fixedWidgetStopIDIsProvidedAsFooter());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    @Disabled
    // disabled as discussed here - https://baeldung.slack.com/archives/DBJAJDG67/p1537350093000100
    public final void givenTheArticleWithTheStickySidebar_whenTheArticleLoads_thenTheContainerClassProptertyIsConfiguredCorrectly() {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_FOR_STICKY_SIDEBAR_TEST);

        page.loadUrl();

        assertTrue(page.stickySidebarContainerClassPropertyIsSetupAsContent());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    @Disabled
    // disabled as discussed here - https://baeldung.slack.com/archives/DBJAJDG67/p1537350093000100
    public final void givenThePageWithTheStickySidebar_whenThePageLoads_thenTheContainerClassProptertyIsConfiguredCorrectly() {
        page.setUrl(page.getBaseURL() + GlobalConstants.PAGE_FOR_STICKY_SIDEBAR_TEST);

        page.loadUrl();

        assertTrue(page.stickySidebarContainerClassPropertyIsSetupAsContent());
    }

    @Test
    @Tag("screenShotTest")
    public final void screenShotTest() throws IOException {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_FOR_STICKY_SIDEBAR_TEST);

        page.loadUrl();

        File srcFile = ((TakesScreenshot) page.getWebDriver()).getScreenshotAs(OutputType.FILE);
        System.out.println("File:" + srcFile);
        System.out.println(srcFile.getAbsolutePath());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenAnArticleWithTheDripScript_whenTheArticleLoads_thenTheArticleHasTheDripScrip() {
        page.setUrl(page.getBaseURL() + GlobalConstants.ARTICLE_WITH_DRIP_SCRIPT);

        page.loadUrl();

        assertTrue("Drip script count is not equal to 1", page.getDripScriptCount() == 1);
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenAPageWithTheDripScript_whenThePageLoads_thenThePageHasTheDripScrip() {
        page.setUrl(page.getBaseURL() + GlobalConstants.PAGE_WITH_DRIP_SCRPT);

        page.loadUrl();

        assertTrue("Drip script count is not equal to 1", page.getDripScriptCount() == 1);
    }

}
