package com.example.pharmacy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryConfig {

    /*
    Annotation 을 활용한 방안만 사용할 거라 주석
    */
//    @Bean
//    public RetryTemplate retryTemplate() {
//        return new RetryTemplate();
//    }
}
