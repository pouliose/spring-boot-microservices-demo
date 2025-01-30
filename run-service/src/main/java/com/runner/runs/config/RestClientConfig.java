package com.runner.runs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean("restApi2Client")
    RestClient restApi2Client(@Value("${user.service.url}") String restApi2Url, RestClient.Builder restClientBuilder) {
        return restClientBuilder.baseUrl(restApi2Url)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).build();
    }

}
