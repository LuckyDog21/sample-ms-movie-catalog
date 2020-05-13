package com.sample.movie_catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    WebClient.Builder webClient(){
        return WebClient.builder();
    }

}
