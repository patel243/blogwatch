package com.baeldung.common;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;

public class CommonUITestWithEUProxy extends BaseUITest {

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindowWithEUProxy();
    }

    @Test
    @Tag("VAT")
    public final void givenOnTheCoursePage_whenThePageLoadsInEUCountry_thenTheVATPricesAreShown() throws IOException {

        page.setUrl("https://ipstack.com/");

        page.loadUrl();

        logger.info("Geo Location-----" + page.getGeoLocation());

        page.setUrl(page.getBaseURL() + GlobalConstants.COURSE_PAGE_FOR_VAT_TEST);

        page.loadUrl();

        assertFalse("VAT prices not displayed in EU region", page.vatPricesNotAvailable());
    }

}
