package com.carrot.backend.board.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    Integer boardId;

    String boardContent;

    String boardUserid;

    String boardCategory;

    String boardAddress;

    Integer boardView;

    Integer boardChattingNum;

    Integer boardAgree;

    String createDate;

    String profileImage;

    List<String> images;
    List<String> replies;
}
