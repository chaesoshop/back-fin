package com.carrot.backend.notification.NotificationDto;

import com.carrot.backend.notification.domain.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private String userid;
    private NotificationType notificationType;
    private String content;
    private String url;
    private String sender;


}