package com.carrot.backend.chatting.dto;

import lombok.Data;

@Data
public class ChattingRoomDto {
    private String roomId;
    private String myName;
    private String yourName;
    private String myURL;
    private String yourURL;
    private Integer articleId;
    private String type;


}
