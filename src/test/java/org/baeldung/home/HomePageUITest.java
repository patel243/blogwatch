package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import org.baeldung.config.MainConfig;
import org.baeldung.site.home.HomePageDriver;
import org.baeldung.site.home.NewsLettersubscriptionPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MainConfig.class })
public final class HomePageUITest {

    @Autowired
    HomePageDriver homePageDriver;
    
    @Autowired
    NewsLettersubscriptionPage newsLettersubscriptionPage;
    
    @Test
    public final void whenJavaWebWeeklySubscribePopup_thenEmailAndSubscribeElementsExist() {
        homePageDriver.loadPage();
        homePageDriver.clickNewsletterButton();
        
        newsLettersubscriptionPage.clickGetAccessToTheLatestIssuesButton();
        assertTrue(newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed()); 
        assertTrue(newsLettersubscriptionPage.findSubscripbeButtonInSubscriptionPopup().isDisplayed()); 

        homePageDriver.quiet();
    }

}
