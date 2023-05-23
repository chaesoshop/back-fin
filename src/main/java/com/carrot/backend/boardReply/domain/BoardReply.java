package com.carrot.backend.boardReply.domain;

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
@Table(name="board_reply")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BoardReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String createDate;

    @Column
    String boardReply;
    @ManyToOne(fetch =FetchType.LAZY)
    private Board board;
    @ManyToOne(fetch =FetchType.LAZY)
    private User user;

    public BoardReply(Board board, User user){
        this.board = board;
        this.user = user;
    }

    @Column
    String profileImage;

    @Column
    String ReplyUserAddress;

    @Column
    String ReplyNickname;

}
