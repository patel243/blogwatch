package org.baeldung.site.base;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.baeldung.config.SeleniumConfig;
import org.baeldung.config.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class BlogBaseDriver {

    @Autowired
    private SeleniumConfig seleniumConfig;

    @Value("${base.url}")
    private String baseURL;        

    protected String pageURL;

    @PostConstruct
    public void setDriver() {
        this.seleniumConfig.getDriver();
    }

    public void loadPage() {
        this.getWebDriver().get(this.pageURL);
    }

    public void openNewWindow() {
        seleniumConfig.openNewWindow();
    }

    public void openNewWindowAndLoadPage() {
        this.openNewWindow();
        this.loadPage();
    }

    public void closeWindow() {
        this.seleniumConfig.getDriver().close();
    }

    public void quiet() {
        if (null != this.seleniumConfig.getDriver())  {      
            this.seleniumConfig.getDriver().quit();
        }
        
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

    public String getBaseURL() {
        if (StringUtils.isEmpty(this.baseURL)) {
            return GlobalConstants.BAELDUNG_HOME_PAGE_URL;
        }
        return baseURL;
    }

    public boolean isLaunchFlag() {
        return Boolean.parseBoolean(System.getenv(GlobalConstants.LAUNCH_FLAG));
    } 
    

}
