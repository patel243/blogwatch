package com.baeldung.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.baeldung.GlobalConstants;
import com.baeldung.base.BaseUISeleniumTest;
import com.baeldung.crawler4j.crawler.CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme;
import com.baeldung.util.Utils;
import com.baeldung.vo.EventTrackingVO;
import com.baeldung.vo.LinkVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class CommonUITest extends BaseUISeleniumTest {

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

        assertTrue("GA script count is not equal to 1", page.getAnalyticsScriptCount() == 1);
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenThePageWithGoogleAnalytics_whenPageLoads_thenPageHasAnalyticsCode() {
        page.setUrl(page.getBaseURL() + GlobalConstants.PAGE_WITH_GOOGLE_ANALYTICS);

        page.loadUrl();

        assertTrue("GA script count is not equal to 1", page.getAnalyticsScriptCount() == 1);
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

        Multimap<String, List<EventTrackingVO>> testData = Utils.getCoursePagesBuyLinksTestData();
        for (String urlKey : testData.keySet()) {
            page.setUrl(page.getBaseURL() + urlKey);

            page.loadUrl();
            for (List<EventTrackingVO> eventTrackingVOs : testData.get(urlKey)) {
                for (EventTrackingVO eventTrackingVO : eventTrackingVOs) {
                    logger.debug("Asserting: " + eventTrackingVO.getTrackingCodes() + " for on " + page.getBaseURL() + urlKey);
                    assertTrue("Didn't find the tracking code on the button/link: " + eventTrackingVO.getLinkText() + "  on " + page.getBaseURL() + urlKey, page.findDivWithEventCalls(eventTrackingVO.getTrackingCodes()));
                }
                assertTrue("event generation script not found on -->" + page.getBaseURL() + urlKey, page.findEventGenerationScript());
            }

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

    @Test
    @Tag(GlobalConstants.TAG_MONTHLY)
    public final void givenTheGitHubModuleReadme_theArticlesLinkedInTheGitHubMouduleLinkForwardTotheSameGitHubModule() throws IOException {

        // default value for crawler.refreshReadmeLinks property is false. So it won't refresh the README links
        gitModulesReadmeLinksExtractor.findAndUpdateLinksToReadmeFiles();

        ListIterator<String> readmeURLs = Utils.fetchGitHubModulesReadmeLinks(); // loads links to all READMEs from file
        Multimap<String, LinkVO> badURLs = ArrayListMultimap.create();

        readmeURLs.forEachRemaining(readmeURL -> {
            try {
                page.setUrl(readmeURLs.next());

                page.loadUrl(); // loads README in browser

                List<LinkVO> urlsInReadmeFile = page.getLinksToTheBaeldungSite(); // get all the articles linked in this README
                String reamdmeParentURL = Utils.getTheParentOfReadme(readmeURL);
                urlsInReadmeFile.forEach(link -> {
                    page.setUrl(link.getLink());
                    page.loadUrlWithThrottling(); // loads an article in the browser
                    if (!page.getWebDriver().getPageSource().toLowerCase().contains(reamdmeParentURL.toLowerCase())) {
                        badURLs.put(readmeURL, link);
                    }

                });
            } catch (Exception e) {
                logger.debug("Error while processing " + readmeURL + " \nError message" + e.getMessage());
            }
        });

        Utils.logErrorMessageForInvalidLinksInReadmeFiles(badURLs);
    }

    @Test
    @Tag("empty-or-none-readme")
    public final void givenTheGitHubModule_theModuleHasANonEmptyReadme() throws IOException {
        page.quiet();
        tutorialsRepoCrawlerController.startCrawler(CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme.class, Runtime.getRuntime().availableProcessors());
        List<String> modulesWithNoneOrEmptyReadme = Utils.getDiscoveredLinks(tutorialsRepoCrawlerController.getDiscoveredURLs());
        if (modulesWithNoneOrEmptyReadme.size() > 0) {
            fail("Modules found with missing or empty READMs \n" + modulesWithNoneOrEmptyReadme.stream().collect(Collectors.joining("\n")));

        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheCoursePage_whenThePageLoads_thenAGeoIPApiProviderWorks() {
        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);

        page.loadUrl();

        assertTrue("geoIP API provider is not working", page.geoIPProviderAPILoaded());
    }

}
