package com.runner.notification.services;

import com.runner.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Page<Notification> findAll(Pageable pageable);

    Notification find(String id);

    Page<Notification> findByUserId(Integer id, Pageable pageable);

    void create(Notification notification);
}
