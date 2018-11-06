package com.baeldung.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.util.concurrent.RateLimiter;

@Configuration
public class CommonConfig {
    
    @Bean
    public RateLimiter createRateLimiter() {
        return RateLimiter.create(1);
    }

}
