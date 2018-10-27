package com.baeldung.config.application;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.config.GlobalConstants;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;

public class SeleniumHeadlessBrowserConfig extends SeleniumConfig {

    @Value("${headless.browser.name}")
    private String headlessBrowserName;

    public String getHeadlessBrowserName() {
        return headlessBrowserName;
    }

    @Override
    public void openNewWindow() {
        logger.info("headlessBrowserName-->" + this.headlessBrowserName);

        if (GlobalConstants.HEADLESS_BROWSER_HTMLUNIT.equalsIgnoreCase(this.headlessBrowserName)) {
            driver = new HtmlUnitDriver(BrowserVersion.getDefault(), true) {
                @Override
                protected WebClient newWebClient(BrowserVersion version) {
                    WebClient webClient = super.newWebClient(version);
                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    return webClient;
                }
            };
        } else {

            DesiredCapabilities caps = getPhantomJSDesiredCapabilities();

            driver = new PhantomJSDriver(caps);
        }
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Override
    public void openNewWindowWithEUProxy() {

        logger.info("headlessBrowserName-->" + this.headlessBrowserName);

        if (GlobalConstants.HEADLESS_BROWSER_HTMLUNIT.equalsIgnoreCase(this.headlessBrowserName)) {
            ProxyConfig proxyConfig = new ProxyConfig(super.getEuProxyServerIP(), Integer.valueOf(super.getEuProxyServerPort()));
            driver = new HtmlUnitDriver(BrowserVersion.getDefault(), true) {
                @Override
                protected WebClient newWebClient(BrowserVersion version) {
                    WebClient webClient = super.newWebClient(version);
                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    webClient.getOptions().setProxyConfig(proxyConfig);
                    return webClient;
                }
            };
        } else {

            DesiredCapabilities caps = getPhantomJSDesiredCapabilities();

            /*  ArrayList<String> cliArgsCap = new ArrayList<String>(); 
            cliArgsCap.add("--proxy=" + this.getEuProxyServerIP() + ":" + this.getEuProxyServerPort());
            cliArgsCap.add("--web-security=false"); 
            cliArgsCap.add("--ignore-ssl-errors=true");            
            cliArgsCap.add("--ssl-protocol=any");
            
            caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);*/

            org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
            proxy.setHttpProxy(this.getEuProxyServerIP() + ":" + this.getEuProxyServerPort());
            proxy.setSslProxy(this.getEuProxyServerIP() + ":" + this.getEuProxyServerPort());
            caps.setCapability(CapabilityType.PROXY, proxy);

            driver = new PhantomJSDriver(caps);
        }
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

    }

    private DesiredCapabilities getPhantomJSDesiredCapabilities() {
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

        // caps.setCapability("takesScreenshot", true);

        return caps;
    }

}
