package com.example.statistics.services;

import com.example.statistics.domain.Ranking;
import com.example.statistics.domain.Run;
import com.example.statistics.repositories.RankingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class RunKafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(RunKafkaConsumerService.class);

    private final RankingRepository rankingRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public RunKafkaConsumerService(ObjectMapper objectMapper, RankingRepository rankingRepository) {
        this.objectMapper = objectMapper;
        this.rankingRepository = rankingRepository;
    }

    @KafkaListener(topics = "run.upload")
    public void consumeRunFromKafka(String payload) throws JsonProcessingException {
        LOG.debug("Received payload from Kafka: {}", payload);
        // Deserialize the payload and save to the database
        Run run = objectMapper.readValue(payload, Run.class);
        LOG.debug("Deserialized run: {}", run);

        Ranking ranking = new Ranking();

        ranking.setUserId(run.getUserId());
        ranking.setLastRunDate(run.getCompletedOn().toLocalDate());

        rankingRepository.save(ranking);
    }
}
