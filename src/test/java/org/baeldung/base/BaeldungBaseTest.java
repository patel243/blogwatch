package org.baeldung.base;

import static com.google.common.base.Preconditions.checkNotNull;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.selenium.base.AbstractBaseTest;
import org.selenium.setup.DriverType;
import org.selenium.setup.SeleniumDriverUtil;
import org.selenium.spring.SeleniumConfig;
import org.selenium.spring.SpringContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SeleniumConfig.class })
public abstract class BaeldungBaseTest extends AbstractBaseTest {

    public BaeldungBaseTest() {
        super();
    }

    // fixtures

    @Override
    @Before
    public void before() {
        super.before();

        if (webDriver == null) {
            driverType = DriverType.valueOf(checkNotNull(SpringContext.context().getEnvironment().getProperty("driver")));
            webDriver = new SeleniumDriverUtil().getDriver(driverType);
        }
    }

}
