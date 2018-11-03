package com.baeldung.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.GlobalConstants;
import com.baeldung.base.BaseUITest;

public class EUProxyUITest extends BaseUITest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindowWithEUProxy();
    }

    @Test
    @Tag("vat-pricing-test")
    public final void givenOnTheCoursePage_whenThePageLoadsInEUCountry_thenTheVATPricesAreShown() {                
        
        page.getWebDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        page.getWebDriver().manage().timeouts().pageLoadTimeout(600, TimeUnit.SECONDS);        

        page.setUrl("https://ipstack.com/");

        page.loadUrl();

        logger.info("Geo Location-----" + page.getGeoLocation());

        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);
        
        page.loadUrl();
        

        assertTrue(page.vatPricesAvailableThePage(), "VAT prices not displayed in EU region");
    }

}
