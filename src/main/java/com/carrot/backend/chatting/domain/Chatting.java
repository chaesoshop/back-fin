package com.carrot.backend.chatting.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chatting {
    public enum MessageType{
        ENTER, TALK
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId;
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime createDate;

}
