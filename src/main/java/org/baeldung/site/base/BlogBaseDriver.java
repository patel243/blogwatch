package org.baeldung.site.base;

import javax.annotation.PostConstruct;

import org.baeldung.config.SeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BlogBaseDriver {

    @Autowired
    private SeleniumConfig seleniumConfig;

    @PostConstruct
    public void setDriver() {
        this.seleniumConfig.getDriver();
    }
    
    public void loadPage(){
        this.getWebDriver().get(this.getPageURL());
    }

    public void closeWindow() {
        this.seleniumConfig.getDriver().close();
    }

    public void quiet() {
        this.seleniumConfig.getDriver().quit();
    }

    public String getTitle() {
        return this.seleniumConfig.getDriver().getTitle();
    }

    public WebDriver getWebDriver() {
        return seleniumConfig.getDriver();
    }

    protected abstract String getPageURL();

}
