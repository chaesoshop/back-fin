package com.carrot.backend.notification.domain;

import com.carrot.backend.util.DataNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class NotificationContent {

    private static final int Max_LENGTH = 50;

    @Column(nullable = false,length = Max_LENGTH)
    private String content;

    public NotificationContent(String content){
        if(isNotValidNotificationContent(content)){
            throw new DataNotFoundException("data not found");
        }
        this.content = content;
    }

    private boolean isNotValidNotificationContent(String content) {
        return Objects.isNull(content) || content.length() > Max_LENGTH
                || content.isEmpty();

    }
}