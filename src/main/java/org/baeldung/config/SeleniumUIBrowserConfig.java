package org.baeldung.config;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumUIBrowserConfig extends SeleniumConfig {

    static {
        System.setProperty("webdriver.gecko.driver", findFile("/geckodriver.exe"));
    }

    static private String findFile(final String filename) {
        final String paths[] = { "", "bin/", "target/classes" }; // drivers are in bin/ directory
        for (final String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }
    
    @Override
    public void openNewWindow(){
        final Capabilities capabilities = DesiredCapabilities.firefox();        
        driver = new FirefoxDriver(capabilities);        
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


}
