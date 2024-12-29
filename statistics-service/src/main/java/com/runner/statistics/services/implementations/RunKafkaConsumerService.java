package com.runner.statistics.services.implementations;

import com.runner.statistics.domain.Ranking;
import com.runner.statistics.domain.Run;
import com.runner.statistics.repositories.StatisticsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class RunKafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(RunKafkaConsumerService.class);

    private final StatisticsRepository rankingRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public RunKafkaConsumerService(ObjectMapper objectMapper, StatisticsRepository rankingRepository) {
        this.objectMapper = objectMapper;
        this.rankingRepository = rankingRepository;
    }

    @KafkaListener(topics = "run.upload")
    public void consumeRunFromKafka(String payload) throws JsonProcessingException {
        LOG.debug("Received payload from Kafka: {}", payload);
        // Deserialize the payload and save to the database
        Run run = objectMapper.readValue(payload, Run.class);
        LOG.debug("Deserialized run: {}", run);

        updateRanking(run);
    }

    private void updateRanking(Run run) {
        Ranking ranking = rankingRepository.findByUserId(run.getUserId()).orElseGet(() -> {
            Ranking newRanking = new Ranking();
            newRanking.setUserId(run.getUserId());
            return newRanking;
        });

        LocalDate runDate = run.getCompletedOn().toLocalDate();
        LocalDate lastRunDate = ranking.getLastRunDateForActiveCalculation();

        if (lastRunDate != null && runDate.isEqual(lastRunDate.plusDays(1))) {
            ranking.setCounterOf5ActiveDaysWithoutDayOff(ranking.getCounterOf5ActiveDaysWithoutDayOff() + 1);
            ranking.setCounterOf15ActiveDaysWithoutDayOff(ranking.getCounterOf15ActiveDaysWithoutDayOff() + 1);
        } else {
            ranking.setCounterOf5ActiveDaysWithoutDayOff(1);
            ranking.setCounterOf15ActiveDaysWithoutDayOff(1);
        }

        if (ranking.getCounterOf15ActiveDaysWithoutDayOff() == 15) {
            ranking.setPoints(ranking.getPoints() + 800);
            ranking.setCounterOf15ActiveDaysWithoutDayOff(0);
            ranking.setCounterOf5ActiveDaysWithoutDayOff(0);
        } else if (ranking.getCounterOf5ActiveDaysWithoutDayOff() == 5) {
            ranking.setPoints(ranking.getPoints() + 100);
            ranking.setCounterOf5ActiveDaysWithoutDayOff(0);
        } else {
            ranking.setPoints(Optional.ofNullable(ranking.getPoints()).orElse(0) + 1);
        }

        ranking.setLastRunDateForActiveCalculation(runDate);
        ranking.setTotalRuns(Optional.ofNullable(ranking.getTotalRuns()).orElse(0) + 1);

        LOG.debug("Updating ranking: {}", ranking);
        rankingRepository.save(ranking);
    }

}
