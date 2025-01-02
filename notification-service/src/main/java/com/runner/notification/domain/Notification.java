package com.runner.notification.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private Integer userId;
    private String message;
    private LocalDateTime timestamp;

    private List<NotificationAction> actions;

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class NotificationAction {
        private String type;
        private String url;
    }
}