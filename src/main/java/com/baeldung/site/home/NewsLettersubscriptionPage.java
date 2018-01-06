package com.baeldung.site.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baeldung.site.base.BlogBaseDriver;

@Component
public class NewsLettersubscriptionPage extends BlogBaseDriver {

    public void clickGetAccessToTheLatestIssuesButton() {
        this.getWebDriver().findElement(By.xpath("//a[contains(., 'Get access to the latest issues')]")).click();
    }

    public WebElement findEmailFieldInSubscriptionPopup() {
        return this.getWebDriver().findElement(By.xpath(".//*[@id='drip-email']"));
    }

    public WebElement findSubscripbeButtonInSubscriptionPopup() {
        return this.getWebDriver().findElement(By.xpath("//button[@type='submit' and contains(., 'SUBSCRIBE')]"));
    }

    @Override
    @Value("${site.home.page.newsletter.subscription.url}")
    protected void setUrl(String url) {
        this.url = this.getBaseURL() + url;
    }

}
