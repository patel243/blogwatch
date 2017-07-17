package org.baeldung.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class SeleniumHeadlessBrowserConfig extends SeleniumConfig {

    @Override
    public void openNewWindow() {
        final Capabilities capabilities = DesiredCapabilities.htmlUnit();
        driver = new HtmlUnitDriver(capabilities) {
            @Override
            protected WebClient newWebClient(BrowserVersion version) {
                WebClient webClient = super.newWebClient(version);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                return webClient;
            }
        };

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


}
