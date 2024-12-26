package com.runner.runs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.runs.config.KafkaConfigProps;
import com.runner.runs.domain.Run;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RunKafkaProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(RunKafkaProducerService.class);

    private ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    final KafkaConfigProps kafkaConfigProps;


    public void sendRunToKafka(Run run) throws JsonProcessingException {
        final String payload = objectMapper.writeValueAsString(run);
        LOG.debug("Sending run to Kafka: {}", payload);
        kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
    }

}
