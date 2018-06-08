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

        rwsTeamOptInPageDriver.clickOnGetAccessLinkforSmallTeam();
        Thread.sleep(1000);
        assertTrue("Problem with opt-in pop-up for small team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

        rwsTeamOptInPageDriver.clickOnGetAccessLinkforMediumTeam();
        Thread.sleep(1000);
        assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

        rwsTeamOptInPageDriver.clickOnGetAccessLinkforLargeTeam();
        Thread.sleep(1000);
        assertTrue("Problem with opt-in pop-up for medium team", rwsTeamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        rwsTeamOptInPageDriver.closePopupOnRwSTeamOptInPage();

    }
}
