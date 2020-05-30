package com.baeldung.jsoup.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.baeldung.jsoup")
@PropertySource("classpath:blog.properties")
public class JSoupMainConfig { }