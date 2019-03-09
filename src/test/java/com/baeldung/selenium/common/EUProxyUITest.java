package com.baeldung.selenium.common;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;

public class EUProxyUITest extends BaseUISeleniumTest {

    @Value("#{'${proxy.eu.servers}'.split(',')}")
    private List<String> euProxyServers;

    int retryCount = 0;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        retryCount = 0;
    }

    @Test
    @Tag("vat-pricing-test")
    @Tag(GlobalConstants.TAG_DAILY_HTMLUNIT)
    public final void givenOnTheCoursePage_whenThePageLoadsInEUCountry_thenTheVATPricesAreShown() {

        try {
            page.openNewWindowWithProxy(Utils.getProxyServerIP(euProxyServers.get(retryCount)), Utils.getProxyServerPort(euProxyServers.get(retryCount)));
            page.getWebDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            page.getWebDriver().manage().timeouts().pageLoadTimeout(280, TimeUnit.SECONDS);
            page.getWebDriver().manage().timeouts().setScriptTimeout(290, TimeUnit.SECONDS);

            // logger.info("Geo Location-----" + page.getGeoLocation());

            page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);

            page.loadUrl();

            assertTrue(page.vatPricesAvailableThePage(), "VAT prices not displayed in EU region. Proxy Server:" + Utils.getProxyServerIP(euProxyServers.get(retryCount)) + ":" + Utils.getProxyServerPort(euProxyServers.get(retryCount)));

        } catch (Exception e) {
            // e.printStackTrace();
            logger.info("Exception----> " + e.getMessage());
            if (euProxyServers.size() == retryCount + 1) {
                logger.debug(euProxyServers.size() + " retries completed with TimeoutException");
                fail(e.getMessage());
            } else {
                page.closeWindow();
                retryCount++;
                givenOnTheCoursePage_whenThePageLoadsInEUCountry_thenTheVATPricesAreShown();
            }
        }
    }

}
