package org.baeldung.home;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.baeldung.article.ArticlePageDriver;
import org.baeldung.base.BaeldungBaseTest;
import org.junit.Ignore;
import org.junit.Test;

public final class HomePageUITest extends BaeldungBaseTest {

    // tests

    @Test
    @Ignore("fails with htmlunit - investigate")
    public final void whenOnHomepage_thenHomepageCorrect() {
        // When
        final HomePageDriver driver = new HomePageDriver(getWebDriver()).navigateToCurrent();

        // Then
        assertTrue(driver.isHere());
        assertTrue(driver.containsPartialText("Powered"));
        assertTrue(driver.containsLinkText("Persistence with Spring series"));
        assertTrue(driver.containsLinkText("REST with Spring series"));
        assertFalse(driver.containsLinkText("Some Other Page"));
        assertTrue(driver.containsLinkText("About"));
    }

    @Test
    @Ignore("after Phase 4 from Bogdan")
    public final void whenOnHomepage_thenReadFullStoryAndCommentLinksNotDisplayed() {
        // When
        final HomePageDriver driver = new HomePageDriver(getWebDriver()).navigateToCurrent();
        driver.wait(1);

        // Then
        assertFalse(driver.containsLinkText("Read full story"));
        assertFalse(driver.containsPartialLinkText(" Comments"));
    }

    @Test
    @Ignore("driver navigation doesn't work any more")
    public final void givenOnHomepage_whenNavigatingToArticle_thenNoExceptions() {
        // When
        final HomePageDriver driver = new HomePageDriver(getWebDriver()).navigateToCurrent();
        final ArticlePageDriver articlePageDriver = driver.toArticle("Multipart Upload on S3 with jclouds");

        // Then
        assertTrue(articlePageDriver.isHere());
    }

}
