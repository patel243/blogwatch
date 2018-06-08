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
    private RwSTeamOptInPageDriver rwsTamOptInPageDriver;

    @Test
    @Tag(GlobalConstants.TAG_HOURLY)
    public final void givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine() throws InterruptedException {

        rwsTamOptInPageDriver.loadUrl();

        rwsTamOptInPageDriver.clickOnGetAccessLinkforSmallTeam();
        Thread.sleep(1000);
        assertTrue("Problem with opt-in pop-up for small team", rwsTamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        rwsTamOptInPageDriver.closePopupOnRwSTeamOptInPage();

        rwsTamOptInPageDriver.clickOnGetAccessLinkforMediumTeam();
        Thread.sleep(1000);
        assertTrue("Problem with opt-in pop-up for medium team", rwsTamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        rwsTamOptInPageDriver.closePopupOnRwSTeamOptInPage();

        rwsTamOptInPageDriver.clickOnGetAccessLinkforLargeTeam();
        Thread.sleep(1000);
        assertTrue("Problem with opt-in pop-up for medium team", rwsTamOptInPageDriver.theSubmitButtonOnthePopupisDisplayed());
        rwsTamOptInPageDriver.closePopupOnRwSTeamOptInPage();

    }
}
