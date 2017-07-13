package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.baeldung.config.MainConfig;
import org.baeldung.site.home.HomePageDriver;
import org.baeldung.site.home.NewsLettersubscriptionPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
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
        homePageDriver.openNewWindowAndLoadPage();
        
        homePageDriver.clickNewsletterButton();
        
        newsLettersubscriptionPage.clickGetAccessToTheLatestIssuesButton();
        assertTrue(newsLettersubscriptionPage.findEmailFieldInSubscriptionPopup().isDisplayed()); 
        assertTrue(newsLettersubscriptionPage.findSubscripbeButtonInSubscriptionPopup().isDisplayed()); 
        
        homePageDriver.quiet();
    }
    
    @Test
    public final void javaWeeklyLinksMatchWithLinkText() {
        homePageDriver.openNewWindowAndLoadPage();
        
        List<WebElement> javaWeeklyElements =  this.homePageDriver.getAllJavaWeeklyIssueLinkElements();
        String expectedLink;
        String issueNumber;
        for (WebElement webElement: javaWeeklyElements ){
            issueNumber = webElement.getText().replaceAll("\\D+","");
            if (issueNumber.length()>0){
                expectedLink= (this.homePageDriver.getPageURL()+"java-weekly-")+issueNumber;
                assertTrue(expectedLink.equals(webElement.getAttribute("href").toString()));
                
                //System.out.println("expectedLink-->"+expectedLink);
                //System.out.println("actual  Link-->"+webElement.getAttribute("href"));
            }
        }
        
        homePageDriver.quiet();
        
    }       

}
