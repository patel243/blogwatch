package com.baeldung.selenium.page;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.common.GlobalConstants;
import com.baeldung.selenium.common.BaseUISeleniumTest;
import com.baeldung.site.SpringMicroservicesGuidePage;
import com.jayway.restassured.RestAssured;

public final class SpringMicroservicesGuideUITest extends BaseUISeleniumTest {

    @Autowired
    private SpringMicroservicesGuidePage springMicroservicesGuidePage;

    @Test
    @Tag(GlobalConstants.TAG_DAILY)
    public final void givenOnTheMicroservicesGuidePage_whenOptinPopupIsLoaded_thenItContainsImages() {
        this.springMicroservicesGuidePage.loadUrl();

        springMicroservicesGuidePage.clickAccessTheGuideButton();

        List<WebElement> elements = springMicroservicesGuidePage.findImages();
        elements.forEach(element -> {
            assertEquals(200, RestAssured.given().head(element.getAttribute("src")).getStatusCode());
        });

    }

}
