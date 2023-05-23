package com.carrot.backend.notification.NotificationDto;

import com.carrot.backend.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NotificationDto {


    private Long id;

    private String content;

    private String url;

    private String userid;

    private String sender;

    private String type;

    private boolean is_read;

    public static NotificationDto create(Notification notification) {
        return new NotificationDto(notification.getId(), notification.getContent(),
                notification.getUrl(),notification.getUser().getUserid(),notification.getSender().getUserid(),notification.getNotificationType().toString(),notification.getIsRead());
    }
}