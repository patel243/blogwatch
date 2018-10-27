package com.baeldung.config.application;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class SeleniumConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${target.env}")
    private String targetEnv;

    @Value("${proxy.eu.server.ip}")
    private String euProxyServerIP;
    
    @Value("${proxy.eu.server.port}")
    private String euProxyServerPort;

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

    public String getEuProxyServerIP() {
        return euProxyServerIP;
    }
       
    public void setEuProxyServerIP(String euProxyServerIP) {
        this.euProxyServerIP = euProxyServerIP;
    }

    public String getEuProxyServerPort() {
        return euProxyServerPort;
    }

    public void setEuProxyServerPort(String euProxyServerPort) {
        this.euProxyServerPort = euProxyServerPort;
    }

    public abstract void openNewWindow();

    public abstract void openNewWindowWithEUProxy();

}
