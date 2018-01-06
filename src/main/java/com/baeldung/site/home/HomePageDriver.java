package com.baeldung.site.home;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baeldung.site.base.BlogBaseDriver;
import com.google.common.collect.Streams;

@Component
public class HomePageDriver extends BlogBaseDriver {

    public void clickNewsletterButton() {
        this.getWebDriver().findElement(By.xpath("//a[contains(., 'NEWSLETTER')]")).click();
    }

    public List<WebElement> getAllJavaWeeklyIssueLinkElements() {
        return Streams.concat(this.getWebDriver().findElements(By.partialLinkText("Java Weekly,")).stream(), this.getWebDriver().findElements(By.partialLinkText("The Java ﻿Weekly")).stream()).collect(Collectors.toList());
    }

    @Override
    @Value("${base.url}")
    protected void setUrl(String url) {
        this.url = url;
    }

}
