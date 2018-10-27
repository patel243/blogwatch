package com.baeldung.common;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;

public class EUProxyUITest extends BaseUITest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindowWithEUProxy();
    }

    @Test
    @Tag("vat-pricing-test")
    public final void givenOnTheCoursePage_whenThePageLoadsInEUCountry_thenTheVATPricesAreShown() {

        page.setUrl("https://ipstack.com/");

        page.loadUrl();

        logger.info("Geo Location-----" + page.getGeoLocation());

        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);

        page.loadUrl();

        assertFalse(page.vatPricesNotAvailable(), "VAT prices not displayed in EU region");
    }

}
