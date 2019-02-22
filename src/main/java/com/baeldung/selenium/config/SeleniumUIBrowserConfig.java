package com.baeldung.selenium.config;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.baeldung.GlobalConstants;

public class SeleniumUIBrowserConfig extends SeleniumConfig {

    /*static {        
        System.setProperty("webdriver.gecko.driver", findFile("/geckodriver.exe"));
    }*/

    static private String findFile(final String filename, final String envTarget) {
        final String paths[] = { "", "bin/" + envTarget + "/", "target/classes" }; // drivers are in bin/ directory
        for (final String path : paths) {
            File file = new File(path + filename);
            if (file.exists()) {
                if (GlobalConstants.TARGET_ENV_LINUX.equals(envTarget)) {
                    file.setExecutable(true, false);
                    file.setReadable(true, false);
                    file.setWritable(true, false);
                }
                return path + filename;
            }
        }
        return "";
    }

    @Override
    public void openNewWindow() {
        if (GlobalConstants.TARGET_ENV_WINDOWS.equalsIgnoreCase(this.getTargetEnv())) {
            System.setProperty("webdriver.gecko.driver", findFile("/geckodriver.exe", this.getTargetEnv()));
        } else {
            System.setProperty("webdriver.gecko.driver", findFile("/geckodriver", this.getTargetEnv()));
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        // firefoxOptions.setHeadless(true);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Override
    public void openNewWindowWithProxy(String proxyServerIP, String proxyServerPort) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        Proxy proxy = new org.openqa.selenium.Proxy();
        proxy.setHttpProxy(proxyServerIP + ":" + proxyServerPort);
        proxy.setSslProxy(proxyServerIP + ":" + proxyServerPort);
        firefoxOptions.setProxy(proxy);
        // firefoxOptions.setHeadless(true);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

}
