package org.baeldung.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumConfig {

    private WebDriver driver;

    public SeleniumConfig() {
        //final Capabilities capabilities = DesiredCapabilities.firefox();
        final Capabilities capabilities = DesiredCapabilities.htmlUnit();
        //driver = new FirefoxDriver(capabilities);
        driver = new HtmlUnitDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /*static {
        System.setProperty("webdriver.gecko.driver", findFile("/geckodriver.exe"));
    }*/

    /*static private String findFile(final String filename) {
        final String paths[] = { "", "bin/", "target/classes" }; // drivers are in bin/ directory
        for (final String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }*/

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(final WebDriver driver) {
        this.driver = driver;
    }

}
