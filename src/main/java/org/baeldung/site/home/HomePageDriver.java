package org.baeldung.site.home;

import org.baeldung.site.base.BlogBaseDriver;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomePageDriver extends BlogBaseDriver {
    
    @Value("${site.home.page.url}")
    private String homePageURL;

    public void clickNewsletterButton() {
        this.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div[2]/div/div/div/div[2]/div[2]/div[2]/div/div/a/span[2]")).click();
    }  
    
    @Override
    public String getPageURL() {
        return this.homePageURL;
    }
    
}
