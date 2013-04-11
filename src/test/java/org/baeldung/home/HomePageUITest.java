package org.baeldung.home;

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
    }

}
