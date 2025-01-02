package com.runner.notification.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationKafkaConsumerService {
    void consumeNotificationFromKafka(String payload) throws JsonProcessingException;
}
