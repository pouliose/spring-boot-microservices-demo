package com.runner.runs;

import com.runner.runs.config.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableDiscoveryClient
@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Run Service API", version = "1.0", description = "Run Service API v1.0")
)
public class RunApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}

}
