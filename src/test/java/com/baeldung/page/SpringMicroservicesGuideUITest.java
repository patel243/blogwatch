package com.baeldung.page;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.base.BaseUITest;
import com.baeldung.config.GlobalConstants;
import com.baeldung.site.guide.SpringMicroservicesGuidePage;
import com.jayway.restassured.RestAssured;

@ExtendWith(SpringExtension.class)
public final class SpringMicroservicesGuideUITest extends BaseUITest {

    @Autowired
    private SpringMicroservicesGuidePage springMicroservicesGuidePage;

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void givenOnTheMicroservicesGuidePage_whenOptinPopupIsLoaded_thenItContainsImages() {
        this.springMicroservicesGuidePage.loadUrl();

        springMicroservicesGuidePage.clickAccessTheGuideButton();

        assertEquals(200, RestAssured.given().get(springMicroservicesGuidePage.findFirstImagePath()).getStatusCode());
        assertEquals(200, RestAssured.given().get(springMicroservicesGuidePage.find2ndImagePath()).getStatusCode());
    }

}
