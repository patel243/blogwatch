package com.baeldung.home;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.config.GlobalConstants;
import com.baeldung.config.MainConfig;
import com.baeldung.site.guide.SpringMicroservicesGuidePage;
import com.baeldung.site.home.HomePageDriver;
import com.baeldung.site.home.NewsLettersubscriptionPage;
import com.jayway.restassured.RestAssured;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MainConfig.class })
public final class HomePageUITest {

    @Autowired
    private HomePageDriver homePageDriver;

    @Autowired
    private NewsLettersubscriptionPage newsLettersubscriptionPage;

    @Autowired
    private SpringMicroservicesGuidePage springMicroservicesGuidePage;

    @BeforeEach
    public void loadNewWindow() {
        homePageDriver.openNewWindow();
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenJavaWebWeeklySubscribePopup_thenEmailAndSubscribeElementsExist() throws InterruptedException {
        homePageDriver.loadPage();
        homePageDriver.clickNewsletterButton();
        Thread.sleep(1000);
        newsLettersubscriptionPage.clickGetAccessToTheLatestIssuesButton();
        System.out.print("is displayed-->" + newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());
        assertTrue(newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());
        assertTrue(newsLettersubscriptionPage.findSubscripbeButtonInSubscriptionPopup().isDisplayed());
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void javaWeeklyLinksMatchWithLinkText() {
        homePageDriver.loadPage();
        List<WebElement> javaWeeklyElements = this.homePageDriver.getAllJavaWeeklyIssueLinkElements();
        String expectedLink;
        String issueNumber;
        for (WebElement webElement : javaWeeklyElements) {
            issueNumber = webElement.getText().replaceAll("\\D+", "");
            if (issueNumber.length() > 0) {
                expectedLink = (this.homePageDriver.getPageURL() + "/java-weekly-") + issueNumber;
                System.out.println("expectedLink-->" + expectedLink);
                System.out.println("actual  Link-->" + webElement.getAttribute("href"));
                assertTrue(expectedLink.equals(webElement.getAttribute("href").toString()));
            }
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void givenOnTheMicroservicesGuidePage_whenOptinPopupIsLoaded_thenItContainsImages() {
        this.springMicroservicesGuidePage.loadPage();
        springMicroservicesGuidePage.clickAccessTheGuideButton();
        assertEquals(200, RestAssured.given().get(springMicroservicesGuidePage.findFirstImagePath()).getStatusCode());
        assertEquals(200, RestAssured.given().get(springMicroservicesGuidePage.find2ndImagePath()).getStatusCode());
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenHomePageLods_thenItContainsCategoriesInFooterMenu() {
        homePageDriver.loadPage();
        assertTrue(homePageDriver.findCategoriesContainerInPageFooter().isDisplayed());
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenHomePageLoaded_thenZeroSevereMessagesInBrowserLog() {
        homePageDriver.loadPage();
        LogEntries browserLogentries = homePageDriver.getWebDriver().manage().logs().get(LogType.BROWSER);
        int items = 0;
        for (LogEntry logEntry : browserLogentries) {
            if (logEntry.getLevel().equals(Level.SEVERE)) {
                // System.out.println("Custom-->"+logEntry.getMessage());
                items++;
            }
        }
        assertEquals(0, items);
    }

    @AfterEach
    public void closeWindow() {
        homePageDriver.quiet();
    }

}
