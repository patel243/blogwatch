package org.baeldung.home;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.logging.Level;

import org.baeldung.config.GlobalConstants;
import org.baeldung.config.MainConfig;
import org.baeldung.site.guide.SpringMicroservicesGuidePage;
import org.baeldung.site.home.HomePageDriver;
import org.baeldung.site.home.NewsLettersubscriptionPage;
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

import com.jayway.restassured.RestAssured;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MainConfig.class })
public final class HomePageUITest {

    @Autowired
    HomePageDriver homePageDriver;

    @Autowired
    NewsLettersubscriptionPage newsLettersubscriptionPage;

    @Autowired
    SpringMicroservicesGuidePage springMicroservicesGuidePage;

    @BeforeEach
    public void loadNewWindow() {
        homePageDriver.openNewWindow();
    }

    @Test    
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenJavaWebWeeklySubscribePopup_thenEmailAndSubscribeElementsExist() {

        try {
            homePageDriver.loadPage();

            homePageDriver.clickNewsletterButton();
            Thread.sleep(1000);
            newsLettersubscriptionPage.clickGetAccessToTheLatestIssuesButton();
            System.out.print("is displayed-->" + newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());
            assertTrue(newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());
            assertTrue(newsLettersubscriptionPage.findSubscripbeButtonInSubscriptionPopup().isDisplayed());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void javaWeeklyLinksMatchWithLinkText() {
        try {
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
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void givenOnTheMicroservicesGuidePage_whenOptinPopupIsLoaded_thenItContainsImages() {
        try {
            this.springMicroservicesGuidePage.loadPage();
            springMicroservicesGuidePage.clickAccessTheGuideButton();
            assertEquals(200, RestAssured.given().get(springMicroservicesGuidePage.findFirstImagePath()).getStatusCode());
            assertEquals(200, RestAssured.given().get(springMicroservicesGuidePage.find2ndImagePath()).getStatusCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenHomePageLods_thenItContainsCategoriesInFooterMenu() {
        try {
            homePageDriver.loadPage();
            assertTrue(homePageDriver.findCategoriesContainerInPageFooter().isDisplayed());
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            springMicroservicesGuidePage.quiet();
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenHomePageLoaded_thenZeroSevereMessagesInBrowserLog() {
        try {
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
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @AfterEach
    public void closeWindow() {
        homePageDriver.quiet();
    }

}
