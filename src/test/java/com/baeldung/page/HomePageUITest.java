package com.baeldung.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.logging.Level;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;
import com.baeldung.site.home.HomePageDriver;
import com.baeldung.site.home.NewsLettersubscriptionPage;

@ExtendWith(SpringExtension.class)
public final class HomePageUITest extends BaseUITest {

    @Autowired
    private HomePageDriver homePageDriver;

    @Autowired
    private NewsLettersubscriptionPage newsLettersubscriptionPage;

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenJavaWebWeeklySubscribePopup_thenEmailAndSubscribeElementsExist() throws InterruptedException {
        homePageDriver.loadUrl();
        homePageDriver.clickNewsletterButton();
        Thread.sleep(1000);

        newsLettersubscriptionPage.clickGetAccessToTheLatestIssuesButton();
        logger.info("is displayed-->" + newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());

        assertTrue(newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed());
        assertTrue(newsLettersubscriptionPage.findSubscripbeButtonInSubscriptionPopup().isDisplayed());
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void givenOnHomePage_whenPageLoads_thenJavaWeeklyLinksMatchWithLinkText() {
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
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenHomePageLods_thenItContainsCategoriesInFooterMenu() {
        homePageDriver.loadUrl();

        assertTrue(homePageDriver.findCategoriesContainerInPageFooter().isDisplayed());
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenHomePageLoaded_thenZeroSevereMessagesInBrowserLog() {
        homePageDriver.loadUrl();
        LogEntries browserLogentries = homePageDriver.getWebDriver().manage().logs().get(LogType.BROWSER);
        int items = 0;
        for (LogEntry logEntry : browserLogentries) {
            if (logEntry.getLevel().equals(Level.SEVERE)) {
                logger.debug("Custom-->" + logEntry.getMessage());
                items++;
            }
        }
        assertEquals(0, items);
    }

}
