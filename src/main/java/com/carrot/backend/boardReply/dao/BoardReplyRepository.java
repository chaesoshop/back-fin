package com.carrot.backend.boardReply.dao;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.boardReply.domain.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Integer>,CustomizedBoardReplyRepository {
    @Transactional
    void deleteAllByBoard(Board board);

    BoardReply findByBoardBoardId(Integer boardId);
}
