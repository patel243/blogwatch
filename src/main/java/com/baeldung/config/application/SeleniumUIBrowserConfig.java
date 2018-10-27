package com.baeldung.config.application;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumUIBrowserConfig extends SeleniumConfig {

    static {
        System.setProperty("webdriver.gecko.driver", findFile("/geckodriver.exe"));
    }

    static private String findFile(final String filename) {
        final String paths[] = { "", "bin/win/", "target/classes" }; // drivers are in bin/ directory
        for (final String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }

    @Override
    public void openNewWindow() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        // firefoxOptions.setHeadless(true);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Override
    public void openNewWindowWithEUProxy() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        Proxy proxy = new org.openqa.selenium.Proxy();
        proxy.setHttpProxy(this.getEuProxyServerIP() + ":" + super.getEuProxyServerPort());
        proxy.setSslProxy(this.getEuProxyServerIP() + ":" + super.getEuProxyServerPort());
        firefoxOptions.setProxy(proxy);
        // firefoxOptions.setHeadless(true);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

}
