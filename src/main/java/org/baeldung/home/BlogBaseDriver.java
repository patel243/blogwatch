package org.baeldung.home;

import org.openqa.selenium.WebDriver;
import org.selenium.base.AbstractDriver;
import org.selenium.base.SmokeUriUtil;
import org.selenium.event.DriverChangeEvent;
import org.selenium.spring.SpringContext;

public abstract class BlogBaseDriver extends AbstractDriver {
	public BlogBaseDriver(final WebDriver driverToSet) {
		super(driverToSet);

		if (SpringContext.context() != null) {
			SpringContext.context().publishEvent(
					new DriverChangeEvent(this, this));
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
