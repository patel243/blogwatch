package com.baeldung.config.application;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.baeldung.config.GlobalConstants;

public class SeleniumHeadlessBrowserConfig extends SeleniumConfig {

    @Override
    public void openNewWindow() {

        /*driver = new HtmlUnitDriver(BrowserVersion.getDefault(), true){
           @Override
           protected WebClient newWebClient(BrowserVersion version) {
               WebClient webClient = super.newWebClient(version);
               webClient.getOptions().setThrowExceptionOnScriptError(false);
               return webClient;
           }
        };*/

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        // caps.setCapability("takesScreenshot", true);
        if (GlobalConstants.TARGET_ENV_WINDOWS.equalsIgnoreCase(this.getTargetEnv())) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "bin/" + this.getTargetEnv() + "/phantomjs.exe");
        } else {

            File file = new File("bin/" + this.getTargetEnv() + "/phantomjs");

            file.setExecutable(true, false);
            file.setReadable(true, false);
            file.setWritable(true, false);

            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "bin/" + this.getTargetEnv() + "/phantomjs");
        }

        caps.setCapability("takesScreenshot", false);

        driver = new PhantomJSDriver(caps);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

}
