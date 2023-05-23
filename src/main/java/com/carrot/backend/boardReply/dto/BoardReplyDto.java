package com.carrot.backend.boardReply.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardReplyDto {
    Integer id;

    String boardReply;

    String replyUserid;

    String profileImage;

    String ReplyUserAddress;

    String ReplyNickname;
}
