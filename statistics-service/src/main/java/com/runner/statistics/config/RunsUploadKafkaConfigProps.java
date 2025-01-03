package com.runner.statistics.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "runs.upload.kafka")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunsUploadKafkaConfigProps {

    private String topic;

}