package org.baeldung.site.home;

import java.util.List;
import java.util.stream.Collectors;

import org.baeldung.site.base.BlogBaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Streams;

@Component
public class HomePageDriver extends BlogBaseDriver {
    
    @Value("${site.home.page.url}")
    private String homePageURL;

    public void clickNewsletterButton() {
        this.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div[2]/div/div/div/div[2]/div[2]/div[2]/div/div/a/span[2]")).click();
    } 
    
    public List<WebElement> getAllJavaWeeklyIssueLinkElements(){
        return Streams.concat(this.getWebDriver().findElements(By.partialLinkText("Java Weekly,")).stream(), 
                              this.getWebDriver().findElements(By.partialLinkText("The Java ﻿Weekly")).stream())
                       .collect(Collectors.toList());        
      }
    
    @Override
    public String getPageURL() {
        return this.homePageURL;
    }    
    
}
