package com.runner.statistics.services.implementations;

import com.runner.statistics.domain.PointsStatistics;
import com.runner.statistics.domain.Run;
import com.runner.statistics.repositories.PointsStatisticsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.runner.statistics.services.RunKafkaConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RunKafkaConsumerServiceImpl implements RunKafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(RunKafkaConsumerServiceImpl.class);

    private final PointsStatisticsRepository rankingRepository;

    private final RankingSchedulerServiceImpl rankingSchedulerService;

    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "run.upload")
    public void consumeRunFromKafka(String payload) throws JsonProcessingException {
        LOG.debug("Received payload from Kafka: {}", payload);
        // Deserialize the payload and save to the database
        Run run = objectMapper.readValue(payload, Run.class);
        LOG.debug("Deserialized run: {}", run);

        updatePointsStatistics(run);

        rankingSchedulerService.publishUserNotificationForRanking(run.getUserId());

    }

    private void updatePointsStatistics(Run run) {
        PointsStatistics pointsStatistics = rankingRepository.findByUserId(run.getUserId()).orElseGet(() -> {
            PointsStatistics newPointsStatistics = new PointsStatistics();
            newPointsStatistics.setUserId(run.getUserId());
            return newPointsStatistics;
        });

        LocalDate runDate = run.getCompletedOn().toLocalDate();
        LocalDate lastRunDate = pointsStatistics.getLastRunDateForActiveCalculation();

        if (lastRunDate != null && runDate.isEqual(lastRunDate.plusDays(1))) {
            pointsStatistics.setCounterOf5ActiveDaysWithoutDayOff(pointsStatistics.getCounterOf5ActiveDaysWithoutDayOff() + 1);
            pointsStatistics.setCounterOf15ActiveDaysWithoutDayOff(pointsStatistics.getCounterOf15ActiveDaysWithoutDayOff() + 1);
        } else {
            pointsStatistics.setCounterOf5ActiveDaysWithoutDayOff(1);
            pointsStatistics.setCounterOf15ActiveDaysWithoutDayOff(1);
        }

        if (pointsStatistics.getCounterOf15ActiveDaysWithoutDayOff() == 15) {
            pointsStatistics.setPoints(pointsStatistics.getPoints() + 800);
            pointsStatistics.setCounterOf15ActiveDaysWithoutDayOff(0);
            pointsStatistics.setCounterOf5ActiveDaysWithoutDayOff(0);
        } else if (pointsStatistics.getCounterOf5ActiveDaysWithoutDayOff() == 5) {
            pointsStatistics.setPoints(pointsStatistics.getPoints() + 100);
            pointsStatistics.setCounterOf5ActiveDaysWithoutDayOff(0);
        } else {
            pointsStatistics.setPoints(Optional.ofNullable(pointsStatistics.getPoints()).orElse(0) + 1);
        }

        pointsStatistics.setLastRunDateForActiveCalculation(runDate);
        pointsStatistics.setTotalRuns(Optional.ofNullable(pointsStatistics.getTotalRuns()).orElse(0) + 1);

        LOG.debug("Updating points statistics: {}", pointsStatistics);
        rankingRepository.save(pointsStatistics);
    }

}
