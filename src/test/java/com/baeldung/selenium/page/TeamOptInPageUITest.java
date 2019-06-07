package com.baeldung.selenium.page;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.common.GlobalConstants;
import com.baeldung.selenium.common.BaseUISeleniumTest;
import com.baeldung.site.TeamOptInPageDriver;

public class TeamOptInPageUITest extends BaseUISeleniumTest {

    @Autowired
    private TeamOptInPageDriver teamOptInPageDriver;

    int retryCount = 0;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindow();
        retryCount = 0;
    }

    @Test
    //@Tag(GlobalConstants.TAG__EVERY_30_MINUTES)
    @Tag("team-optin-popup-test")
    @Tag(GlobalConstants.TAG_HOURLY)    
    public final void givenOnTheTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine() throws InterruptedException, IOException {
        try {
            logger.info("Starting givenOnTheTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine");
            teamOptInPageDriver.loadUrl();
            Thread.sleep(15000);

            logger.info("forSmallTeam - teamOptInPageDriver.clickOnGetAccessLinkforSmallTeam()");
            teamOptInPageDriver.clickOnGetAccessLinkforSmallTeam();
            logger.info("forSmallTeam - teamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for small team", teamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
            logger.info("forSmallTeam - teamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
            teamOptInPageDriver.closePopupOnRwSTeamOptInPage();

            logger.info("forMediumTeam - rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam()");
            teamOptInPageDriver.clickOnGetAccessLinkforMediumTeam();
            logger.info("forMediumTeam - teamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for medium team", teamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
            logger.info("forMediumTeam - teamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
            teamOptInPageDriver.closePopupOnRwSTeamOptInPage();
            
            logger.info("forLargeTeam - teamOptInPageDriver.clickOnGetAccessLinkforLargeTeam()");
            teamOptInPageDriver.clickOnGetAccessLinkforLargeTeam();
            logger.info("forLargeTeam - teamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for medium team", teamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
            logger.info("forLargeTeam - teamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
            teamOptInPageDriver.closePopupOnRwSTeamOptInPage();
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                if (5 == retryCount) {
                    logger.debug("5 retries completed with SocketTimeoutException");
                    fail(e.getMessage());
                } else {
                    retryCount++;
                    givenOnTheTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine();

                }
            } else {
                fail(e.getMessage());
            }
        }

    }
   
}
