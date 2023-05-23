package com.carrot.backend.boardReply.dao;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.boardReply.dto.BoardReplyDto;

import java.util.List;

public interface CustomizedBoardReplyRepository {
    List<BoardReplyDto> getQslReplyByBoard(Board board );
}
