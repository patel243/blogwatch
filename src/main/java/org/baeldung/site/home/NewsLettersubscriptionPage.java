package org.baeldung.site.home;

import org.baeldung.site.base.BlogBaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NewsLettersubscriptionPage extends BlogBaseDriver{
    
    @Value("${site.home.page.newsletter.subscription.url}")
    private String pageURL;
    
    public void clickGetAccessToTheLatestIssuesButton() {
        this.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div/div/div/div/div[3]/div/a/span[2]")).click();
    }
    
    public WebElement findEmailFieldInSubscriptionPopup() {
        return this.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div/div[2]/form/div/div[2]/input"));
    }
    public WebElement findSubscripbeButtonInSubscriptionPopup() {
        return this.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div/div[2]/form/div/div[4]/button"));
    }

    @Override
    protected String getPageURL() {
        return this.pageURL;
    }
    
    

}
