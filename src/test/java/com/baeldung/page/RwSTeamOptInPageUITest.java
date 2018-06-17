package com.baeldung.page;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;
import com.baeldung.site.course.RwSTeamOptInPageDriver;

public class RwSTeamOptInPageUITest extends BaseUITest {

    @Autowired
    private RwSTeamOptInPageDriver rwsTeamOptInPageDriver;

    @Test
    @Tag(GlobalConstants.TAG_HOURLY)
    public final void givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine() throws InterruptedException {

        rwsTeamOptInPageDriver.loadUrl();
        
        logger.info("forSmallTeam-->rwsTeamOptInPageDriver.clickOnGetAccessLinkforSmallTeam()");
        rwsTeamOptInPageDriver.clickOnGetAccessLinkforSmallTeam();
        logger.info("forSmallTeam-->rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
        assertTrue("Problem with opt-in pop-up for small team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        logger.info("forSmallTeam-->rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
        rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

        logger.info("forMediumTeam-->rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam()");
        rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam();
        logger.info("forMediumTeam-->rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
        assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        logger.info("forMediumTeam-->rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
        rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

        logger.info("forLargeTeam-->rwsTeamOptInPageDriver.clickOnGetAccessLinkforLargeTeam()");
        rwsTeamOptInPageDriver.clickOnGetAccessLinkforLargeTeam();
        logger.info("forLargeTeam-->rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed()");
        assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        logger.info("forLargeTeam-->rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage()");
        rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

    }
}
