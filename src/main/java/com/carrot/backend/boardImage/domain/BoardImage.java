package com.carrot.backend.boardImage.domain;


import com.carrot.backend.board.domain.Board;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "board_image")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long boardImageId;

    @Column
    String boardPath;


    @ManyToOne
    private Board board;
}
