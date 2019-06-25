package com.baeldung.selenium.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
import com.baeldung.common.vo.EventTrackingVO;
import com.baeldung.common.vo.LinkVO;
import com.baeldung.crawler4j.crawler.CrawlerForFindingReadmeURLs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class CommonUITest extends BaseUISeleniumTest {

    @Value("${document-readme-having-articles-more-than}")
    private int documentReadmeHavingArticlesMoreThan;

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

        assertEquals(301, response.getStatusCode());
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
    @Tag("givenTheGitHubModuleReadme_theArticlesLinkedInTheGitHubMouduleLinkForwardTotheSameGitHubModule")
    public final void givenTheGitHubModuleReadme_theArticlesLinkedInTheGitHubMouduleLinkForwardTotheSameGitHubModule() throws IOException {

        tutorialsRepoCrawlerController.startCrawlingWithAFreshController(CrawlerForFindingReadmeURLs.class, Runtime.getRuntime().availableProcessors());

        List<String> readmeURLs = Utils.getDiscoveredLinks(tutorialsRepoCrawlerController.getDiscoveredURLs());
        Multimap<String, LinkVO> badURLs = ArrayListMultimap.create();

        Map<String, Integer> articleCountByReadme = new HashMap<>();

        readmeURLs.forEach(readmeURL -> {
            try {
                page.setUrl(readmeURL);

                page.loadUrl(); // loads README in browser

                List<LinkVO> urlsInReadmeFile = page.getLinksToTheBaeldungSite(); // get all the articles linked in this README

                // for documenting no of links per README
                if (urlsInReadmeFile.size() > documentReadmeHavingArticlesMoreThan) {
                    articleCountByReadme.put(readmeURL, urlsInReadmeFile.size());
                }

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

        Utils.logResults(articleCountByReadme, "Article count by READMEs");
        Utils.logErrorMessageForInvalidLinksInReadmeFiles(badURLs);
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheCoursePage_whenThePageLoads_thenAGeoIPApiProviderWorks() {
        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);

        page.loadUrl();

        assertTrue("geoIP API provider is not working", page.geoIPProviderAPILoaded());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheBaeldungRSSFeed_whenTheFirstUrlIsHit_thenItPointsToTheBaeldungSite() {
        page.setUrl(GlobalConstants.BAELDUNG_RSS_FEED_URL);

        page.loadUrl();

        
        page.setUrl(page.getTheFirstBaeldungURL());
        page.loadUrl();
        page.getWebDriver().getCurrentUrl();
        
        RequestSpecification requestSpecification = RestAssured.given().redirects().follow(false);
        String feedURL = requestSpecification.get(page.getTheFirstBaeldungURL()).getHeader("Location");
        //requestSpecification = RestAssured.given().redirects().follow(true);
       // Response response = requestSpecification.get(feedURL);

        logger.info("Currently loaded page URL: " + page.getWebDriver().getCurrentUrl());
        logger.info("Currently loaded page title: " + page.getWebDriver().getTitle());
        logger.info("Currently set feed url: " + page.getUrl());
        //assertTrue("The page linked in the RSS feed couldn't be loaded properly", page.getWebDriver().getTitle().toLowerCase().contains("baeldung)"));
        assertTrue("The RSS Feed URL doesn't point to  https://baeldung.com", page.rssFeedURLPointsTotheBaeldungSite(page.getWebDriver().getCurrentUrl()));
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnCoursePages_whenAPageLoads_thenItContainsImportantAnchors() {

        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_RWS_PAGE);
        logger.info("RWS course page loaded");

        page.loadUrl();

        assertTrue("RWS Course page is missing #table(PRICING) anchor in the footer", page.tableAnchorIsVisibleOnThePage());
        assertTrue("RWS Course page is missing #master-class anchor in the footer", page.masterclassAnchorIsVisibleOnThePage());
        assertTrue("RWS Course page is missing #certification-class anchor in the footer", page.certificationclassAnchorIsVisibleOnThePage());

        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_LSS_PAGE);
        logger.info("LSS course page loaded");

        page.loadUrl();

        assertTrue("RWS Course page is missing #table(PRICING) anchor in the footer", page.tableAnchorIsVisibleOnThePage());
        assertTrue("RWS Course page is missing #master-class anchor in the footer", page.masterclassAnchorIsVisibleOnThePage());
        assertTrue("RWS Course page is missing #certification-class anchor in the footer", page.certificationclassAnchorIsVisibleOnThePage());
    }

}
