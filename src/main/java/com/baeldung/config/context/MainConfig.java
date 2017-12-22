package com.baeldung.config.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.baeldung.config.application.SeleniumHeadlessBrowserConfig;
import com.baeldung.config.application.SeleniumUIBrowserConfig;
import com.baeldung.site.home.HomePageDriver;
import com.google.common.util.concurrent.RateLimiter;

@Configuration
@ComponentScan("com.baeldung.site")
@PropertySource({ "classpath:smoke-${smokeTarget:dev}.properties" })
public class MainConfig {

    @Bean
    @Profile("headless-browser")
    public SeleniumHeadlessBrowserConfig seleniumHeadlessBrowserConfig() {
        return new SeleniumHeadlessBrowserConfig();
    }

    @Bean
    @Profile("ui-browser")
    public SeleniumUIBrowserConfig seleniumUIBrowserConfig() {
        return new SeleniumUIBrowserConfig();
    }

    @Bean
    public RateLimiter createRateLimiter() {
        return RateLimiter.create(1);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(final String[] args) {

        final ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        final HomePageDriver test = ctx.getBean(HomePageDriver.class);

    }

}
