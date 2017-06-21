package org.baeldung.config;

import org.baeldung.site.home.HomePageDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("org.baeldung.site")
@PropertySource({ "classpath:smoke-${smokeTarget:dev}.properties" })
public class MainConfig {

    @Bean
    public SeleniumConfig seleniumConfig() {
        return new SeleniumConfig();
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
