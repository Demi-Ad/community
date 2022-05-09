package com.example.community.domain.notification.dto;


import com.example.community.domain.notification.entity.Notification;
import com.example.community.domain.notification.eventType.EventType;
import lombok.Data;

@Data
public class NotificationDto {
    private Long notificationId;
    private EventType eventType;
    private String content;

    private String uri;

    public NotificationDto(Notification notification) {
        this.notificationId = notification.getId();
        this.eventType = notification.getEventType();
        this.content = notification.getContent();
        this.uri = notification.getUri();
    }
}
