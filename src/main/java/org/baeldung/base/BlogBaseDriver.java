package org.baeldung.base;

import org.openqa.selenium.WebDriver;
import org.selenium.base.AbstractDriver;
import org.selenium.base.SmokeUriUtil;
import org.selenium.event.DriverChangeEvent;
import org.selenium.spring.SpringContext;

public abstract class BlogBaseDriver<D extends AbstractDriver<D>> extends AbstractDriver<D> {
    public BlogBaseDriver(final WebDriver driverToSet) {
        super(driverToSet);

        if (SpringContext.context() != null) {
            SpringContext.context().publishEvent(new DriverChangeEvent(this, this));
        }
    }

    // API

    @Override
    public final boolean isErrorPresent() {
        throw new UnsupportedOperationException();
    }

    // template

    @Override
    protected String getBaseUri() {
        return SmokeUriUtil.getBaseUri();
    }

}
