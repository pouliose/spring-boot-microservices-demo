package com.runner.statistics.services.implementations;

import POJO.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.statistics.domain.Ranking;
import com.runner.statistics.repositories.StatisticsRepository;
import com.runner.statistics.services.RankingSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingSchedulerServiceImpl implements RankingSchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(RankingSchedulerServiceImpl.class);

    private final StatisticsRepository statisticsRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public RankingSchedulerServiceImpl(StatisticsRepository statisticsRepository, KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.statisticsRepository = statisticsRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void calculateAndPublishNotifications() {
        List<Ranking> rankings = statisticsRepository.findAll();
        rankings.sort((r1, r2) -> r2.getPoints().compareTo(r1.getPoints()));

        List<Notification> notifications = rankings.stream()
                .map(ranking -> {
                    String message = String.format("Dear runner, your current overall ranking are at position %d, top %.2f over %d participants with points %d",
                            rankings.indexOf(ranking) + 1,
                            calculateOverallRanking(rankings.indexOf(ranking) + 1, rankings.size()),
                            rankings.size(),
                            ranking.getPoints()
                    );
                    return new Notification(ranking.getUserId(), message);
                })
                .toList();

        notifications.forEach(notification -> {
            try {
                String payload = objectMapper.writeValueAsString(notification);
                kafkaTemplate.send("midnight.ranking.notification", payload);
                LOG.debug("Published notification: {}", payload);
            } catch (JsonProcessingException e) {
                LOG.error("Error serializing notification: {}", e.getMessage());
            }
        });
    }

    private double calculateOverallRanking(int currentPosition, int totalParticipants) {
        return ((double) currentPosition /totalParticipants)*100; // Placeholder
    }
}
