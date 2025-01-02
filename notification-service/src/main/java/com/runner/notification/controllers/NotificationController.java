package com.runner.notification.controllers;

import com.runner.notification.services.NotificationService;
import com.runner.notification.domain.Notification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@RequestMapping("api/v1/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")

    Page<Notification> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "10") int pageSize,
                               @RequestParam(defaultValue = "id") String sortBy,
                               @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Notification> notifications = notificationService.findAll(pageable);
        return notifications;
    }

    @GetMapping("/users/{userId}")
    Page<Notification> findNotificationsByUserId(@PathVariable Integer userId, @RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                 @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return notificationService.findByUserId(userId, pageable);
    }
}
