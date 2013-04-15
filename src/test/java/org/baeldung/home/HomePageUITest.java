package org.baeldung.home;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.baeldung.base.BaeldungBaseTest;
import org.junit.Test;

public final class HomePageUITest extends BaeldungBaseTest {

    // tests

    @Test
    public final void whenLoggedInAsAdmin_thenAdminPage() {
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

}
