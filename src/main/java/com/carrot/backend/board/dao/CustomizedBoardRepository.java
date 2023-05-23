package com.carrot.backend.board.dao;

import com.carrot.backend.board.dto.BoardDto;

import javax.transaction.Transactional;

public interface CustomizedBoardRepository {

    BoardDto getQslBoardAndImagesByBoardId(Integer boardId);

    @Transactional
    void deleteQslBoardAndImageByBoardId(Integer boardId);
}
