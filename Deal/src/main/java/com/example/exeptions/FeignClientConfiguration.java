package com.example.exeptions;

import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {

    @Bean
    public feign.codec.ErrorDecoder errorDecoder() {
        return new ErrorDecoder();
    }
}