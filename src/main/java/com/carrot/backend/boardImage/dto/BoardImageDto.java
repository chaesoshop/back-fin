package com.carrot.backend.boardImage.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardImageDto {

    Long boardImageId;

    String boardPath;

    Integer boardId;
    public BoardImageDto(Integer boardId, String boardPath){
        this.boardId = boardId;
        this.boardPath = boardPath;

    }
}
