package org.baeldung.base;

import org.openqa.selenium.WebDriver;
import org.selenium.base.AbstractDriverNew;
import org.selenium.base.SmokeUriUtil;
import org.selenium.event.DriverChangeEventNew;
import org.selenium.spring.SpringContext;

public abstract class BlogBaseDriver<D extends AbstractDriverNew<D>> extends AbstractDriverNew<D> {
    public BlogBaseDriver(final WebDriver driverToSet) {
        super(driverToSet);

        if (SpringContext.context() != null) {
            SpringContext.context().publishEvent(new DriverChangeEventNew(this, this));
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
