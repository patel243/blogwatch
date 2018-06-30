package com.baeldung.page;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.logging.Level;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;
import com.baeldung.site.home.HomePageDriver;
import com.baeldung.site.home.NewsLettersubscriptionPage;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public final class HomePageUITest extends BaseUITest {

    @Autowired
    private HomePageDriver homePageDriver;

    @Autowired
    private NewsLettersubscriptionPage newsLettersubscriptionPage;

    @Test
    @Tag(GlobalConstants.TAG_HOURLY)
    @Disabled
    public final void givenOnTheJavaWebWeeklyPage_whenSubscriptionPopupLoads_thenItContainsSubscriptionElements() throws InterruptedException {
        homePageDriver.loadUrl();
        homePageDriver.clickNewsletterButton();
        Thread.sleep(10000);

        newsLettersubscriptionPage.clickGetAccessToTheLatestIssuesButton();

        Thread.sleep(10000);

        logger.info("URL-->" + homePageDriver.getWebDriver().getCurrentUrl());

        logger.info("Email field found on the page-->" + newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup());

        logger.info("Email field displayed-->" + newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());

        assertTrue(newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());
        assertTrue(newsLettersubscriptionPage.findSubscripbeButtonInSubscriptionPopup().isDisplayed());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheHomePage_whenPageLoads_thenJavaWeeklyLinksMatchWithTheLinkText() {
        homePageDriver.loadUrl();
        List<WebElement> javaWeeklyElements = this.homePageDriver.getAllJavaWeeklyIssueLinkElements();

        String expectedLink;
        String issueNumber;
        for (WebElement webElement : javaWeeklyElements) {
            issueNumber = webElement.getText().replaceAll("\\D+", "");
            if (issueNumber.length() > 0) {
                expectedLink = (this.homePageDriver.getUrl() + "/java-weekly-") + issueNumber;
                logger.debug("expectedLink-->" + expectedLink);
                logger.debug("actual  Link-->" + webElement.getAttribute("href"));

                assertTrue(expectedLink.equals(webElement.getAttribute("href").toString()));
            }
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheHomePage_whenPageLoads_thenItContainsCategoriesInTheFooterMenu() {
        homePageDriver.loadUrl();

        assertTrue(homePageDriver.findCategoriesContainerInThePageFooter().isDisplayed());
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheHomePage_whenHomePageLoaded_thenNoSevereMessagesInBrowserLog() {
        homePageDriver.loadUrl();

        LogEntries browserLogentries = homePageDriver.getWebDriver().manage().logs().get(LogType.BROWSER);

        for (LogEntry logEntry : browserLogentries) {
            if (logEntry.getLevel().equals(Level.SEVERE)) {
                fail("Error with Severe Level-->" + logEntry.getMessage());
            }
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheHomePageUrlWithoutWWWPrefix_whenUrlIsHit_thenItRedirectsToWWW() {
        Response response = RestAssured.given().redirects().follow(false).head(GlobalConstants.BAELDUNG_HOME_PAGE_URL_WITHOUT_WWW_PREFIX);

        assertEquals(301, response.getStatusCode());
        assertEquals(GlobalConstants.BAELDUNG_HOME_PAGE_URL_WITH_WWW_PREFIX, response.getHeader("Location").replaceAll("/$", ""));
    }

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheHomePage_whenPageLoads_thenItHasOneAboutMenuInTheFooter() {
        homePageDriver.loadUrl();
        assertTrue(homePageDriver.findAboutMenuInThePageFooter().size() == 1);
    }

    @Test
    @Tag(GlobalConstants.TAG_WEEKLY)
    @Tag("dripSurvey")
    @Disabled //disabled as suggested in BAEL-7332
    /**
     * Tests fails on windows in headless mode. It works fine with UI browser in windows.
     * The test works fine on Jenkins (Cloudbees) with headless mode. On Jenkins, it run on Linux. 
     */
    public final void givenOnTheHomePage_whenTheSurveyStarts_thenTheSelectValueIsPostedToTheDrip() throws InterruptedException {

        // impersonate subscriber
        homePageDriver.loadUrl();
        homePageDriver.getWebDriver().get(homePageDriver.getBaseURL() + GlobalConstants.DRIP_SUBSCRIPTION_QUERY_STRING);

        // remove custom field
        String subscriberData = homePageDriver.removeDripCutomJobRoleFieldAndGetSubscriberDetails();
        logger.info(subscriberData);

        // assert that the custom field is removed
        assertFalse(subscriberData.contains(GlobalConstants.DRIP_CUTOM_FIELD), "Failed to remove drip cutom field");

        // impersonate subscriber again to load the survey
        homePageDriver.getWebDriver().get(homePageDriver.getBaseURL() + GlobalConstants.DRIP_SUBSCRIPTION_QUERY_STRING);
        Thread.sleep(2000);

        // click on the Architect job role
        homePageDriver.getArchitectItemInTheSurveyPopup().click();

        Thread.sleep(2000);

        // impersonate subscriber again. This is required if the previous step throws JS error.
        homePageDriver.getWebDriver().get(homePageDriver.getBaseURL() + GlobalConstants.DRIP_SUBSCRIPTION_QUERY_STRING);

        subscriberData = homePageDriver.getDripSubscriberDetails();
        logger.info(subscriberData);

        assertTrue(subscriberData.contains(GlobalConstants.DRIP_CUTOM_FIELD_VALUE_ARCHITECT), "Failed to set job role thorugh the survey");
    }

}
