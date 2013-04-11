package org.baeldung.home;

import org.baeldung.base.BlogBaseDriver;
import org.openqa.selenium.WebDriver;
import org.selenium.util.Selenium2Utils;

public final class HomePageDriver extends BlogBaseDriver<HomePageDriver> {

    public HomePageDriver(final WebDriver driverToSet) {
        super(driverToSet);
    }

    // API

    // template methods

    @Override
    public final HomePageDriver wait(final int seconds) {
        Selenium2Utils.Wait.tryWaitForElementFoundById(getWebDriver(), "", seconds);
        return this;
    }

    @Override
    public final boolean isHere() {
        return getWebDriver().getCurrentUrl().contains(getBaseUri());
    }

}
