package com.runner.statistics.services.implementations;

import POJO.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.statistics.domain.PointsStatistics;
import com.runner.statistics.repositories.PointsStatisticsRepository;
import com.runner.statistics.services.RankingSchedulerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RankingSchedulerServiceImpl implements RankingSchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(RankingSchedulerServiceImpl.class);

    private final PointsStatisticsRepository statisticsRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishUserNotificationForRanking(Integer userId) {
        List<PointsStatistics> pointsStatistics = statisticsRepository.findAll();
        pointsStatistics.sort((r1, r2) -> r2.getPoints().compareTo(r1.getPoints()));

        List<Notification> notifications = pointsStatistics.stream().filter(ranking -> ranking.getUserId().equals(userId))
                .map(ranking -> {
                    String message = String.format("Dear runner, your current overall ranking are at position %d, top %.2f over %d participants with points %d",
                            pointsStatistics.indexOf(ranking) + 1,
                            calculateOverallRanking(pointsStatistics.indexOf(ranking) + 1, pointsStatistics.size()),
                            pointsStatistics.size(),
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
        return ((double) currentPosition /totalParticipants)*100;
    }
}
