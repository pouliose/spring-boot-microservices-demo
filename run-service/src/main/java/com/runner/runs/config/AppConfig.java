package com.runner.runs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
    @Value("${user.service.url}") String userServiceUrl;

    @Bean
    public RestClient restClient() {
        RestClient.Builder builder = RestClient.builder();
        return builder.baseUrl(userServiceUrl).build();
    }
}