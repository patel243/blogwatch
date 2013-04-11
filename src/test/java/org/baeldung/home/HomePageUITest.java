package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import org.baeldung.base.BaeldungBaseTest;
import org.junit.Test;

public final class HomePageUITest extends BaeldungBaseTest {

    // tests

    @Test(timeout = 30000)
    public final void whenLoggedInAsAdmin_thenAdminPage() {
        // When
        final HomePageDriver driver = new HomePageDriver(getWebDriver()).navigateToCurrent();

        // Then
        assertTrue(driver.isHere());
    }

}
