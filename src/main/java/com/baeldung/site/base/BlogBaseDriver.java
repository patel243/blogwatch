package com.baeldung.site.base;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.config.GlobalConstants;
import com.baeldung.config.application.SeleniumConfig;
import com.google.common.util.concurrent.RateLimiter;

public abstract class BlogBaseDriver {
    
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeleniumConfig seleniumConfig;

    @Autowired
    private RateLimiter rateLimiter;

    @Value("${base.url}")
    private String baseURL;

    protected String url;

    @PostConstruct
    public void setDriver() {
        this.seleniumConfig.getDriver();
    }

    public void loadUrl() {
        this.getWebDriver().get(this.url);
    }

    public void loadUrlWithThrottling() {
        rateLimiter.acquire();
        this.getWebDriver().get(this.url);
    }

    public void openNewWindow() {
        seleniumConfig.openNewWindow();
    }
    
    public void openNewWindowWithEUProxy() {
        seleniumConfig.openNewWindowWithEUProxy();
    }

    public void openNewWindowAndLoadPage() {
        this.openNewWindow();
        this.loadUrl();
    }

    public void closeWindow() {
        this.seleniumConfig.getDriver().close();
    }

    public void quiet() {
        if (null != this.seleniumConfig.getDriver()) {
            this.seleniumConfig.getDriver().quit();
        }

    }

    public String getTitle() {
        return this.seleniumConfig.getDriver().getTitle();
    }

    public WebDriver getWebDriver() {
        return seleniumConfig.getDriver();
    }

    public String getUrl() {
        return url;
    }
    
    public String getUrlWithNewLineFeed() {
        return "\n"+ url;
    }

    protected abstract void setUrl(String url);

    public String getBaseURL() {
        return baseURL;
    }

    public boolean isLaunchFlag() {
        return Boolean.parseBoolean(System.getenv(GlobalConstants.LAUNCH_FLAG));
    }

    public WebElement findCategoriesContainerInThePageFooter() {
        return this.getWebDriver().findElement(By.id("menu-categories"));
    }

    public List<WebElement> findAboutMenuInThePageFooter() {
        return this.getWebDriver().findElements(By.xpath("//h4[@class = 'widgettitle' and translate(text(),'ABOUT','about') = 'about']"));
    }

    public void configureImplicitWait(long time, TimeUnit unit) {
        getWebDriver().manage().timeouts().implicitlyWait(time, unit);
    }

    public List<WebElement> getAllScriptTags() {
        return this.getWebDriver().findElements(By.tagName("script"));
    }

    public JavascriptExecutor getJavaScriptExecuter() {
        return (JavascriptExecutor) this.getWebDriver();
    }

    public String getRelativeUrl() {
        return this.getUrl().toString().substring(this.getBaseURL().toString().length());
    }

}
