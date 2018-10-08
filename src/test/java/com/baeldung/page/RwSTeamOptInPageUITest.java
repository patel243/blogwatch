package com.baeldung.page;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;
import com.baeldung.site.course.RwSTeamOptInPageDriver;

public class RwSTeamOptInPageUITest extends BaseUITest {

    @Autowired
    private RwSTeamOptInPageDriver rwsTeamOptInPageDriver;

    int retryCount = 0;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindow();
        retryCount = 0;
    }

    @Test
    @Tag(GlobalConstants.TAG__EVERY_30_MINUTES)
    public final void givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine() throws InterruptedException, IOException {
        try {
            logger.info("Starting givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine");
            rwsTeamOptInPageDriver.loadUrl();
            Thread.sleep(10000);

            logger.info("forSmallTeam - rwsTeamOptInPageDriver.clickOnGetAccessLinkforSmallTeam()");
            rwsTeamOptInPageDriver.clickOnGetAccessLinkforSmallTeam();
            logger.info("forSmallTeam - rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for small team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
            logger.info("forSmallTeam - rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
            rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

            logger.info("forMediumTeam - rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam()");
            rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam();
            logger.info("forMediumTeam - rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
            logger.info("forMediumTeam - rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
            rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

            logger.info("forLargeTeam - rwsTeamOptInPageDriver.clickOnGetAccessLinkforLargeTeam()");
            rwsTeamOptInPageDriver.clickOnGetAccessLinkforLargeTeam();
            logger.info("forLargeTeam - rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
            logger.info("forLargeTeam - rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
            rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                if (5 == retryCount) {
                    logger.debug("5 retries completed with SocketTimeoutException");
                    fail(e.getMessage());
                } else {
                    retryCount++;
                    givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine();

                }
            } else {
                fail(e.getMessage());
            }
        }

    }

    @Test
    @Tag(GlobalConstants.TAG__EVERY_30_MINUTES)
    public final void givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsDoOpen() throws InterruptedException {
        try {
            logger.info("Starting givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsDoOpen");

            rwsTeamOptInPageDriver.loadUrl();
            Thread.sleep(10000);
            logger.info("forSmallTeam - rwsTeamOptInPageDriver.clickOnGetAccessLinkforSmallTeam()");
            rwsTeamOptInPageDriver.clickOnGetAccessLinkforSmallTeam();
            logger.info("forSmallTeam - rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for small team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());

            rwsTeamOptInPageDriver.loadUrl();
            Thread.sleep(10000);
            logger.info("forMediumTeam - rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam()");
            rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam();
            logger.info("forMediumTeam - rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());

            rwsTeamOptInPageDriver.loadUrl();
            Thread.sleep(10000);
            logger.info("forLargeTeam - rwsTeamOptInPageDriver.clickOnGetAccessLinkforLargeTeam()");
            rwsTeamOptInPageDriver.clickOnGetAccessLinkforLargeTeam();
            logger.info("forLargeTeam - rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
            assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                if (5 == retryCount) {
                    logger.debug("5 retries completed with SocketTimeoutException");
                    fail(e.getMessage());
                } else {
                    retryCount++;
                    givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsDoOpen();

                }
            } else {
                fail(e.getMessage());
            }
        }

    }
}
