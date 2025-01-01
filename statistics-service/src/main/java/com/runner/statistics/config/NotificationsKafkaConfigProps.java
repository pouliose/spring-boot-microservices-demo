package com.runner.statistics.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "notifications.kafka")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationsKafkaConfigProps {

    private String topic;

}