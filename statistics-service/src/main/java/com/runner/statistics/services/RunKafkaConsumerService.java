package com.runner.statistics.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RunKafkaConsumerService {
    void consumeRunFromKafka(String payload) throws JsonProcessingException;
}
