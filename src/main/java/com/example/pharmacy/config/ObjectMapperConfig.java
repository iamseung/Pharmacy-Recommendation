package com.example.pharmacy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
ObjectMapper SingleTon 으로 지정

[참고자료]
Spring Framework에서 @Bean으로 선언된 메서드가 제공하는 객체는 기본적으로 싱글톤
즉, Spring 컨테이너는 해당 빈(bean)의 인스턴스를 단 하나만 생성하고 관리
(Spring의 기본 빈 스코프가 'singleton')
 */
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
