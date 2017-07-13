package org.baeldung.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumHeadlessBrowserConfig extends SeleniumConfig {

    @Override
    public void openNewWindow(){
        final Capabilities capabilities = DesiredCapabilities.htmlUnit();
        driver = new HtmlUnitDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


}
