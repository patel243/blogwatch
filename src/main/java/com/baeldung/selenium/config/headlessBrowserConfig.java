package com.baeldung.selenium.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.common.GlobalConstants;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;


public class headlessBrowserConfig extends browserConfig {

    @Value("${headless.browser.name}")
    private String headlessBrowserName;

    public String getHeadlessBrowserName() {
        return headlessBrowserName;
    }

    @Override
    public void openNewWindow() {
        logger.info("headlessBrowserName-->" + this.headlessBrowserName);

        if (GlobalConstants.HEADLESS_BROWSER_HTMLUNIT.equalsIgnoreCase(this.headlessBrowserName)) {
            webDriver = new HtmlUnitDriver(BrowserVersion.getDefault(), true) {
                @Override
                protected WebClient newWebClient(BrowserVersion version) {
                    WebClient webClient = super.newWebClient(version);
                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    return webClient;
                }
            };
        } else {

            DesiredCapabilities caps = getPhantomJSDesiredCapabilities();

            webDriver = new PhantomJSDriver(caps);
        }
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Override
    public void openNewWindowWithProxy(String proxyHost, String proxyServerPort, String proxyUsername, String proxyPassword) {

        logger.info("headlessBrowserName-->" + this.headlessBrowserName);

        if (GlobalConstants.HEADLESS_BROWSER_HTMLUNIT.equalsIgnoreCase(this.headlessBrowserName)) {
            ProxyConfig proxyConfig = new ProxyConfig(proxyHost, Integer.valueOf(proxyServerPort));
            webDriver = new HtmlUnitDriver(BrowserVersion.getDefault(), true) {
                @Override
                protected WebClient newWebClient(BrowserVersion version) {
                    WebClient webClient = super.newWebClient(version);
                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    webClient.getOptions().setProxyConfig(proxyConfig);
                    webClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new NTCredentials(proxyUsername, proxyPassword, "", ""));
                    return webClient;
                }
            };
        } else {

            DesiredCapabilities caps = getPhantomJSDesiredCapabilities();

            /*org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
            proxy.setHttpProxy(proxyServerIP + ":" + proxyServerPort);
            proxy.setSslProxy(proxyServerIP + ":" + proxyServerPort);
            proxy.setSocksUsername(proxyUsername);
            proxy.setSocksPassword(proxyPassword);
            caps.setCapability(CapabilityType.PROXY, proxy);*/

            List<String> cliArgsCap = new ArrayList<String>();
            cliArgsCap.add("--proxy=" + proxyHost + ":" + proxyServerPort);
            cliArgsCap.add("--proxy-auth=" + proxyUsername + ":" + proxyPassword);
            cliArgsCap.add("--proxy-type=http");
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
            caps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:66.0.4) Gecko/20100101 Firefox/66.0.4");
            
            //set logging level
            /*Logger rootLogger = LogManager.getLogManager().getLogger("");
            rootLogger.setLevel(Level.INFO);
            for (Handler h : rootLogger.getHandlers()) {
                h.setLevel(Level.WARNING);
            }*/
            webDriver = new PhantomJSDriver(caps);
        }
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

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
