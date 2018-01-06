package com.baeldung.site.guide;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baeldung.site.base.BlogBaseDriver;

@Component
public class SpringMicroservicesGuidePage extends BlogBaseDriver {

    public void clickAccessTheGuideButton() {
        this.getWebDriver().findElement(By.xpath("//a[contains(., 'ACCESS THE GUIDE')]")).click();
    }

    public List<WebElement> findImages() {
        return this.getWebDriver().findElements(By.xpath("//*[@id='tve_editor']//img"));
    }

    @Override
    @Value("${site.guide.spring.microservices}")
    protected void setUrl(String pageURL) {
        this.url = this.getBaseURL() + pageURL;
    }

}
