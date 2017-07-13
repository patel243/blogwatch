package org.baeldung.config;

import org.openqa.selenium.WebDriver;

public abstract class SeleniumConfig {
    
    protected WebDriver driver;
    
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(final WebDriver driver) {
        this.driver = driver;
    }
    
    public abstract void openNewWindow();


}
