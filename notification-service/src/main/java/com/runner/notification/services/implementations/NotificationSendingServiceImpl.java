package com.runner.notification.services.implementations;

import com.runner.notification.domain.Notification;
import com.runner.notification.services.NotificationSendingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationSendingServiceImpl implements NotificationSendingService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationSendingServiceImpl.class);

    @Override
    public void sendMidnightRankingNotification(Notification notification) {
        LOG.debug("Sending notification: {}", notification);
    }
}
