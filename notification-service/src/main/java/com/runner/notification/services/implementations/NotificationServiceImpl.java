package com.runner.notification.services.implementations;

import com.runner.notification.domain.Notification;
import com.runner.notification.exceptions.NotificationNotFoundException;
import com.runner.notification.repositories.NotificationRepository;
import com.runner.notification.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification find(String id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isEmpty()) {
            throw new NotificationNotFoundException();
        }
        return notification.get();
    }

    @Override
    public void create(Notification notification) {
        notificationRepository.insert(notification);
    }

    @Override
    public Page<Notification> findByUserId(Integer id, Pageable pageable) {
        return notificationRepository.findByUserId(id, pageable);
    }

    @Override
    public void delete(String id) {
        notificationRepository.deleteById(id);
    }
}
