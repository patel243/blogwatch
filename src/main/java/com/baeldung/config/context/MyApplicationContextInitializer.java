package com.baeldung.config.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.baeldung.config.GlobalConstants;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public MyApplicationContextInitializer() {
        super();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        final String activeProfiles = environment.getProperty(GlobalConstants.ENV_PROPERTY_SPRING_PROFILE);
        final String baseURL = environment.getProperty(GlobalConstants.ENV_PROPERTY_BASE_URL);
        final String targetEnv = environment.getProperty(GlobalConstants.ENV_PROPERTY_TARGET_ENV);

        if (StringUtils.isBlank(activeProfiles)) {
            environment.setActiveProfiles(GlobalConstants.DEFAULT_SPRING_PROFILE);
        }

        if (StringUtils.isBlank(baseURL)) {
            System.setProperty(GlobalConstants.ENV_PROPERTY_BASE_URL, GlobalConstants.BAELDUNG_HOME_PAGE_URL);
        }

        if (StringUtils.isBlank(targetEnv)) {
            System.setProperty(GlobalConstants.ENV_PROPERTY_TARGET_ENV, GlobalConstants.TARGET_ENV_WINDOWS);
        }

    }

}
