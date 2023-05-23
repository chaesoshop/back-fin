package com.carrot.backend.boardLike.domain;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="board_like")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch =FetchType.LAZY)
    private Board board;
    @ManyToOne(fetch =FetchType.LAZY)
    private User user;

    public BoardLike(Board board, User user){
        this.board = board;
        this.user = user;
    }

}
