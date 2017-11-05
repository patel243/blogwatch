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
    
    protected String pageURL;

    @PostConstruct
    public void setDriver() {       
        this.seleniumConfig.getDriver();
    }
    
    public void loadPage(){
        this.getWebDriver().get(this.pageURL);       
    }
    
    public void openNewWindow(){
        seleniumConfig.openNewWindow();
    }
    public void openNewWindowAndLoadPage(){
        this.openNewWindow();
        this.loadPage();
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

	public String getPageURL() {
		return pageURL;
	}

	protected abstract void setPageURL(String pageURL);
    
}
