package com.carrot.backend.chatting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChattingRoom {
    @Id
    private String roomId;
    private String myName;

    private String yourName;

    private String myURL;

    private String yourURL;

    private Integer articleId;
    private String type;

    private String lastMessage;

    @Builder
    public ChattingRoom(String roomId,String myName, String yourName,String myURL,String yourURL, String type, Integer articleId){
        this.myName=myName;
        this.yourName=yourName;
        this.roomId=roomId;
        this.myURL = myURL;
        this.yourURL=yourURL;
        this.type = type;
        this.articleId=articleId;

    }


}
