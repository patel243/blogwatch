package org.baeldung.config;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

public abstract class SeleniumConfig {
    
	 @Value("${target.env}")
	 private String targetEnv;
	 
    protected WebDriver driver;
    
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(final WebDriver driver) {
        this.driver = driver;
    }
           
    public String getTargetEnv() {
		return targetEnv;
	}

	public void setTargetEnv(String targetEnv) {
		this.targetEnv = targetEnv;
	}

	public abstract void openNewWindow();


}
