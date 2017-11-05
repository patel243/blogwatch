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
        
    public void clickNewsletterButton() {
        this.getWebDriver().findElement(By.xpath("//*[@id='tve_editor']/div/div[2]/div/div/div[1]/div/div[1]/div[2]/div[3]/div/div[2]/div/div/div/a/span[2]/span")).click();
    } 
    
    public List<WebElement> getAllJavaWeeklyIssueLinkElements(){
        return Streams.concat(this.getWebDriver().findElements(By.partialLinkText("Java Weekly,")).stream(), 
                              this.getWebDriver().findElements(By.partialLinkText("The Java ﻿Weekly")).stream())
                       .collect(Collectors.toList());        
      }
        
	@Override
	@Value("${site.home.page.url}")
	protected void setPageURL(String pageURL) {
		this.pageURL = pageURL;		
	}    
    
}
