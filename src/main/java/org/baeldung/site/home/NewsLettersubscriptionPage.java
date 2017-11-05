package org.baeldung.site.home;

import org.baeldung.site.base.BlogBaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NewsLettersubscriptionPage extends BlogBaseDriver{           
    
    public void clickGetAccessToTheLatestIssuesButton() {
        this.getWebDriver().findElement(By.xpath(".//*[@id='tve_editor']/div[1]/div/div/div/div/div[3]/div/a/span[2]")).click();
    }
    
    public WebElement findEmailFieldInSubscriptionPopup() {
        return this.getWebDriver().findElement(By.xpath(".//*[@id='drip-email']"));
    }
    public WebElement findSubscripbeButtonInSubscriptionPopup() {
        return this.getWebDriver().findElement(By.xpath(".//*[@id='tve_editor']/div[3]/div[1]/form/div[1]/div/div/div[2]/div/div/button"));
    }

	@Override
	@Value("${site.home.page.newsletter.subscription.url}")
	protected void setPageURL(String pageURL) {
		this.pageURL = pageURL;		
	}
    
    
    

}
