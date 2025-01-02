package com.runner.notification.services.implementations;

import POJO.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.notification.domain.Notification.NotificationAction;
import com.runner.notification.repositories.NotificationRepository;
import com.runner.notification.services.NotificationKafkaConsumerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationKafkaConsumerServiceImpl implements NotificationKafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationKafkaConsumerServiceImpl.class);

    private static final List<NotificationAction> actions = List.of(
            new NotificationAction("MidnightRankingNotification", "/midnight-ranking"));

    private final NotificationRepository notificationRepository;

    private final ObjectMapper objectMapper;

    public NotificationKafkaConsumerServiceImpl(NotificationRepository notificationRepository, ObjectMapper objectMapper) {
        this.notificationRepository = notificationRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @KafkaListener(topics = "midnight.ranking.notification")
    public void consumeNotificationFromKafka(String payload) throws JsonProcessingException {
        LOG.debug("Received payload from Kafka: {}", payload);
        // Deserialize the payload and save to the database
        Notification notification = objectMapper.readValue(payload, Notification.class);
        LOG.debug("Deserialized notification: {}", notification);

        saveNotification(notification);
    }

    private void saveNotification(Notification notification) {
        com.runner.notification.domain.Notification notificationUpdated = new com.runner.notification.domain.Notification();

        notificationUpdated.setUserId(notification.getUserId());
        notificationUpdated.setMessage(notification.getMessage());
        notificationUpdated.setTimestamp(LocalDateTime.now());
        notificationUpdated.setActions(actions);

        LOG.debug("Updating notification: {}", notification);
        notificationRepository.insert(notificationUpdated);
    }

}
