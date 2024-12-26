package com.runner.runs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runner.runs.domain.Run;

public interface RunKafkaProducerService {
    void sendRunToKafka(Run run) throws JsonProcessingException;
}