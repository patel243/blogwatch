package com.baeldung.selenium.config;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class browserConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${target.env}")
    private String targetEnv;

    protected WebDriver webDriver;

    public WebDriver getDriver() {
        return webDriver;
    }

    public void setDriver(final WebDriver driver) {
        this.webDriver = driver;
    }

    public String getTargetEnv() {
        return targetEnv;
    }

    public void setTargetEnv(String targetEnv) {
        this.targetEnv = targetEnv;
    }

    public abstract void openNewWindow();

    public abstract void openNewWindowWithProxy(String proxyServerIP, String proxyServerPort);

}
