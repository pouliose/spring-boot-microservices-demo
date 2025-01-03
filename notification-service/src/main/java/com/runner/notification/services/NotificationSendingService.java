package com.runner.notification.services;

import com.runner.notification.domain.Notification;

public interface NotificationSendingService {

    void sendMidnightRankingNotification(Notification notification);
}
