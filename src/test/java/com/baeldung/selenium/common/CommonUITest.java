package com.baeldung.selenium.common;

import static com.baeldung.common.GlobalConstants.TestMetricTypes.FAILED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.GlobalConstants.TestMetricTypes;
import com.baeldung.common.TestMetricsExtension;
import com.baeldung.common.Utils;
import com.baeldung.common.vo.AnchorLinksTestDataVO;
import com.baeldung.common.vo.CourseBuyLinksVO.PurchaseLink;
import com.baeldung.common.vo.EventTrackingVO;
import com.baeldung.common.vo.FooterLinksDataVO;
import com.baeldung.common.vo.LinkVO;
import com.baeldung.crawler4j.crawler.CrawlerForFindingReadmeURLs;
import com.baeldung.utility.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rholder.retry.Retryer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

@ExtendWith(TestMetricsExtension.class)
public class CommonUITest extends BaseUISeleniumTest {

    @Value("${document-readme-having-articles-more-than}")
    private int documentReadmeHavingArticlesMoreThan;

    @Value("${time-out.for.200OK-test}")
    private int timeOutFor200OKTest;

    @Value("${retries.for.200OK-test}")
    private int retriesFor200OKTest;

    @Value("${rss.feed.compare.days}")
    private int rssFeedShouldNotbeOlderThanDays;

    @Value("#{'${site.status.check.url.file.names:course-pages.txt}'.split(',')}")
    private List<String> pageStausCheckUrlFileNames;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenThePagesWithBlankTitle_whenPageLoads_thenItDoesNotContainNotitleText() {
        GlobalConstants.PAGES_WITH_BLANK_TITLE.forEach(url -> {
            page.setUrl(page.getBaseURL() + url);

            page.loadUrl();

            assertFalse(page.getCountOfElementsWithNotitleText() > 0, "page found with 'No Title' in body-->" + url);
        });
    }

    @Test
    @Tag(GlobalConstants.TAG_SKIP_METRICS)
    public final void givenAListOfUrls_whenAUrlLoads_thenItReturns200OK() throws IOException {

        logger.info("Configured retires: {}", retriesFor200OKTest);
        logger.info("configure timeout for REST Assured: {}", timeOutFor200OKTest);
        logger.info("Input files:{}", pageStausCheckUrlFileNames);

        Multimap<String, Integer> badURLs = ArrayListMultimap.create();
        RestAssuredConfig restAssuredConfig = TestUtils.getRestAssuredCustomConfig(timeOutFor200OKTest);
        Retryer<Boolean> retryer = Utils.getGuavaRetryer(retriesFor200OKTest);

        try (Stream<String> alURls = Utils.fetchFilesAsList(pageStausCheckUrlFileNames)) {
            alURls.forEach(URL -> {
                TestUtils.sleep(400);
                String fullURL = page.getBaseURL() + URL;
                logger.info("Verifying 200OK on: {}", fullURL);

                TestUtils.hitURLUsingGuavaRetryer(restAssuredConfig, fullURL, badURLs, retryer);

            });
        }

        if (badURLs.size() > 0) {
            recordMetrics(badURLs.keySet().size(), FAILED);
            fail("200OK Not received from following URLs:\n" + Utils.http200OKTestResultBuilder(badURLs));
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

    @ParameterizedTest(name = " {displayName} - on {0}")
    @MethodSource("com.baeldung.utility.TestUtils#gaCodeTestDataProvider")
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenAGoogleAnalyticsEnabledPage_whenAnalysingThePageSource_thenItHasTrackingCode(String url) {
        page.setUrl(page.getBaseURL() + url);

        page.loadUrl();

        assertTrue(page.getAnalyticsScriptCount() == 1, "GA script count is not equal to 1");
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenBaeldungFeedUrl_whenUrlIsHit_thenItRedirectsToFeedburner() {
        Response response = RestAssured.given().redirects().follow(false).get(GlobalConstants.BAELDUNG_FEED_URL);

        assertTrue(response.getStatusCode() == 301 || response.getStatusCode() == 302, "HTTP staus code is not 301 or 302. Returned status code is: " + response.getStatusCode());
        assertTrue(response.getHeader("Location").replaceAll("/$", "").trim().toLowerCase().contains(GlobalConstants.BAELDUNG_FEED_FEEDBURNER_URL),
                "Location header doesn't contain feeds.feedburner.com/baeldung. Returned Location header is: " + response.getHeader("Location").replaceAll("/$", "").toLowerCase());
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
    @Tag(GlobalConstants.TAG_SKIP_METRICS)
    public final void givenOnTheCoursePage_whenPageLoads_thenTrackingIsSetupCorrectly() throws JsonProcessingException, IOException {

        Multimap<String, String> badURLs = ArrayListMultimap.create();
        Multimap<String, List<EventTrackingVO>> testData = Utils.getCoursePagesGATrackingTestData(objectMapper);
        for (String urlKey : testData.keySet()) {
            page.setUrl(page.getBaseURL() + urlKey);

            page.loadUrl();
            for (List<EventTrackingVO> eventTrackingVOs : testData.get(urlKey)) {
                for (EventTrackingVO eventTrackingVO : eventTrackingVOs) {
                    logger.debug("Asserting: " + eventTrackingVO.getTrackingCodes() + " for on " + page.getBaseURL() + urlKey);
                    if (!page.findDivWithEventCalls(eventTrackingVO.getTrackingCodes())) {
                        badURLs.put(page.getBaseURL() + urlKey, "Button/link: " + eventTrackingVO.getLinkText() + "\nTracking code: " + eventTrackingVO.getTrackingCodes());
                    }
                }
                if (!page.findEventGenerationScript()) {
                    badURLs.put(page.getBaseURL() + urlKey, "event generation script not found");
                }
            }

        }

        if (badURLs.size() > 0) {
            recordMetrics(badURLs.size(), FAILED);
            triggerTestFailure(Utils.gaTrackingSetopResultBuilder(badURLs), "Couldn't find the tracking code  on the below pages");
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

        assertTrue(page.getDripScriptCount() == 1, "Drip script count is not equal to 1");
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenAPageWithTheDripScript_whenThePageLoads_thenThePageHasTheDripScrip() {
        page.setUrl(page.getBaseURL() + GlobalConstants.PAGE_WITH_DRIP_SCRPT);

        page.loadUrl();

        assertTrue(page.getDripScriptCount() == 1, "Drip script count is not equal to 1");
    }

    @Test
    @Tag(GlobalConstants.TAG_GITHUB_RELATED)
    public final void givenAGitHubModuleReadme_whenAnalysingTheReadme_thenLinksToAndFromGithubMatch() throws IOException {

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

        if (badURLs.size() > 0) {
            recordMetrics(badURLs.size(), TestMetricTypes.FAILED);
            Utils.logResults(articleCountByReadme, "Article count by READMEs");

            failTestWithLoggingTotalNoOfFailures("\nwe found issues with following READMEs" + Utils.getErrorMessageForInvalidLinksInReadmeFiles(badURLs));

        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheCoursePage_whenThePageLoads_thenAGeoIPApiProviderWorks() {
        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);

        page.loadUrl();

        assertTrue(page.geoIPProviderAPILoaded(), "geoIP API provider is not working");
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheBaeldungRSSFeed_whenTheFirstUrlIsHit_thenItPointsToTheBaeldungSite() {
        page.setUrl(GlobalConstants.BAELDUNG_RSS_FEED_URL);

        page.loadUrl();

        page.setUrl(page.getTheFirstBaeldungURL());
        page.loadUrl();

        // RequestSpecification requestSpecification = RestAssured.given().redirects().follow(false);
        // String feedURL = requestSpecification.get(page.getTheFirstBaeldungURL()).getHeader("Location");
        // requestSpecification = RestAssured.given().redirects().follow(true);
        // Response response = requestSpecification.get(feedURL);

        logger.info("Currently loaded page URL: " + page.getWebDriver().getCurrentUrl());
        logger.info("Currently loaded page title: " + page.getWebDriver().getTitle());
        logger.info("Currently set feed url: " + page.getUrl());
        // assertTrue("The page linked in the RSS feed couldn't be loaded properly", page.getWebDriver().getTitle().toLowerCase().contains("baeldung)"));
        assertTrue(page.rssFeedURLPointsTotheBaeldungSite(page.getWebDriver().getCurrentUrl()), "The RSS Feed URL doesn't point to  https://baeldung.com");
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheBaeldungRSSFeed_whenAnalysingFeed_thenItIsUptoDate() {
        page.setUrl(GlobalConstants.BAELDUNG_RSS_FEED_URL);

        page.loadUrl();

        MatcherAssert.assertThat("Baeldung RSS Feed should not be older than " + rssFeedShouldNotbeOlderThanDays + "days", page.getAgeOfTheFirstPostIntheFeed(), Matchers.lessThanOrEqualTo(rssFeedShouldNotbeOlderThanDays));
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnCoursePages_whenAPageLoads_thenItContainsImportantAnchors() {

        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_RWS_PAGE);
        logger.info("RWS course page loaded");

        page.loadUrl();

        assertTrue(page.tableAnchorIsVisibleOnThePage(), "RWS Course page is missing #table(PRICING) anchor in the footer");
        assertTrue(page.masterclassAnchorIsVisibleOnThePage(), "RWS Course page is missing #master-class anchor in the footer");
        assertTrue(page.certificationclassAnchorIsVisibleOnThePage(), "RWS Course page is missing #certification-class anchor in the footer");

        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_LSS_PAGE);
        logger.info("LSS course page loaded");

        page.loadUrl();

        assertTrue(page.tableAnchorIsVisibleOnThePage(), "RWS Course page is missing #table(PRICING) anchor in the footer");
        assertTrue(page.masterclassAnchorIsVisibleOnThePage(), "RWS Course page is missing #master-class anchor in the footer");
        assertTrue(page.certificationclassAnchorIsVisibleOnThePage(), "RWS Course page is missing #certification-class anchor in the footer");
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheBaeldungMediaKitURL_whenPageLoads_thenItReturns200OK() throws IOException {

        URL url = new URL(GlobalConstants.BAELDUNG_MEDIA_KIT_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        assertEquals(200, httpURLConnection.getResponseCode());

    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    @Tag(GlobalConstants.TAG_SKIP_METRICS)
    public final void givenURLsWithAnchorsLinkingWithinSamePage_whenAnaysingPage_thenAnHtmlElementExistsForEachAnchor() throws JsonProcessingException, IOException {

        List<AnchorLinksTestDataVO> AnchorLinksTestDataVOs = Utils.getAnchorLinksTestData(objectMapper);
        Multimap<String, String> badURLs = ArrayListMultimap.create();

        for (AnchorLinksTestDataVO anchorLinksTestData : AnchorLinksTestDataVOs) {
            page.setUrl(page.getBaseURL() + anchorLinksTestData.getUrl());

            page.loadUrl();

            for (String anchorLink : anchorLinksTestData.getAnchorsLinks()) {
                if (!page.findElementForAnchor(anchorLink)) {
                    badURLs.put(page.getUrl(), "\n" + anchorLink);
                }
            }
        }

        if (badURLs.size() > 0) {
            recordMetrics(badURLs.keySet().size(), FAILED);
            triggerTestFailure(badURLs, "Matching HTML element not found for the following Anchor Links");
        }
    }

    @ParameterizedTest(name = " {displayName} - Test {0} redirects to {1}")
    @MethodSource("com.baeldung.utility.TestUtils#redirectsTestDataProvider")
    @Tag("redirectsTest")
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenTheListOfRedirectedUrls_whenAUrlLoads_thenItRedirectsSuccesfully(String url, String redirectedTo) {
        String fullUrl = url;
        if(!url.contains("http://")) {
            fullUrl = page.getBaseURL() + url;
        }
        Response response = RestAssured.given().redirects().follow(false).get(fullUrl);

        assertTrue(Utils.addTrailingSlasIfNotExists(response.getHeader("Location").toLowerCase()).equals(Utils.addTrailingSlasIfNotExists(redirectedTo.toLowerCase())), url + " doesn't redirec to " + redirectedTo);
    }

    @Test
    public final void givenTheContactForm_whenAMessageIsSubmitted_thenItIsSentSuccessfully() throws InterruptedException {

        // load contact form
        page.setUrl(page.getBaseURL() + GlobalConstants.CONTACT_US_FORM_URL);
        page.loadUrl();

        page.acceptCookie();

        // fill and submit form
        page.getWebDriver().findElement(By.name("your-name")).sendKeys("Selenium Test on " + LocalDate.now());
        page.getWebDriver().findElement(By.name("your-email")).sendKeys("support@baeldung.com");
        page.getWebDriver().findElement(By.name("your-message")).sendKeys("Test message from Selenium");
        // page.acceptCookie();
        page.getWebDriver().findElement(By.xpath("//input[contains(@value, 'Send your message')]")).click();

        // verify
        WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Thank you for your message. It has been sent')]")));
        logger.info("message sent successfully");

    }

    @ParameterizedTest(name = " {displayName} - verify footer links on {0}")
    @MethodSource("com.baeldung.utility.TestUtils#pagesAnchorLinksTestDataProvider()")
    @Tag(GlobalConstants.TAG_SITE_SMOKE_TEST)
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenURLsWithFooterLinks_whenAnaysingFooterLinks_thenTheAnchorTestAndAnchorLinksExist(String url, String footerTag, List<FooterLinksDataVO.link> footerLinks) throws JsonProcessingException, IOException {

        page.setUrl(page.getBaseURL() + url);

        page.loadUrl();

        logger.info("Inspection footer links on {}", page.getBaseURL() + url);

        for (FooterLinksDataVO.link link : footerLinks) {

            assertTrue(page.anchorAndAnchorLinkAvailable(footerTag, link), String.format("Countn't find Anchor Text:%s and Anchor Link: %s, on %s", link.getAnchorText(), link.getAnchorLink(), url));
        }

    }

    @ParameterizedTest(name = " {displayName} - on {0}")
    @MethodSource("com.baeldung.utility.TestUtils#consoleLogTestDataProvider")
    @Tag(GlobalConstants.TAG_DAILY)
    @Tag(GlobalConstants.TAG_SITE_SMOKE_TEST)
    public final void givenAPage_whenThePageLoads_thenNoSevereMessagesInTheBrowserConsoleLog(String url) {
        page.setUrl(page.getBaseURL() + url);

        page.loadUrl();

        LogEntries browserLogentries = page.getWebDriver().manage().logs().get(LogType.BROWSER);

        for (LogEntry logEntry : browserLogentries) {
            if (logEntry.getLevel().equals(Level.SEVERE)) {
                fail("Error with Severe Level-->" + logEntry.getMessage());
            }
        }
    }

    @ParameterizedTest(name = " {displayName} - verify purchase links on {0}")
    @MethodSource("com.baeldung.utility.TestUtils#pagesPurchaseLinksTestDataProvider()")
    //@Tag(GlobalConstants.TAG_SITE_SMOKE_TEST)
    //@Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheCoursePage_whenAnaysingThePage_thenThePurchaseLinksAreSetupCorrectly(String courseUrl, List<PurchaseLink> purchaseLinks) throws JsonProcessingException, IOException {

        String fullURL = page.getBaseURL() + courseUrl;

        page.setUrl(fullURL);

        page.loadUrl();

        RestAssuredConfig restAssuredConfig = TestUtils.getRestAssuredCustomConfig(5000);
        
        for (PurchaseLink link : purchaseLinks) {

            assertTrue(page.anchorAndAnchorLinkAvailable(link), String.format("Countn't find Purchse link with anchor Text:%s and anchor Link: %s, on %s", link.getAnchorText(), link.getAnchorLink(), fullURL));            
            assertTrue(TestUtils.veirfyRedirect(restAssuredConfig, link.getAnchorLink(), link.getRedirectedTo()), link.getAnchorText() + " (" + link.getAnchorLink() + ") on " + fullURL + " doesn't redirec to " + link.getRedirectedTo());
        }

    }

}
