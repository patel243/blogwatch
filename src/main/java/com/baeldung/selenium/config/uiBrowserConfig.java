package com.baeldung.selenium.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;

public class uiBrowserConfig extends browserConfig {

    @Override
    public void openNewWindow() {
        if (GlobalConstants.TARGET_ENV_WINDOWS.equalsIgnoreCase(this.getTargetEnv())) {
            System.setProperty("webdriver.gecko.driver", Utils.findFile("/geckodriver.exe", this.getTargetEnv()));
        } else {
            System.setProperty("webdriver.gecko.driver", Utils.findFile("/geckodriver", this.getTargetEnv()));
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        // firefoxOptions.setHeadless(true);
        webDriver = new FirefoxDriver(firefoxOptions);
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Override
    public void openNewWindowWithProxy(String proxyServerIP, String proxyServerPort, String proxyUserName, String proxyPassword) {
        logger.info("Proxy Authentication is not implemented for UI Browser");

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        Proxy proxy = new org.openqa.selenium.Proxy();
        proxy.setHttpProxy(proxyServerIP + ":" + proxyServerPort);
        proxy.setSslProxy(proxyServerIP + ":" + proxyServerPort);

        firefoxOptions.setProxy(proxy);
        // firefoxOptions.setHeadless(true);
        webDriver = new FirefoxDriver(firefoxOptions);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

}
