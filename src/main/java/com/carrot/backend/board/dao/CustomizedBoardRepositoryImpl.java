package com.carrot.backend.board.dao;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.board.domain.QBoard;
import com.carrot.backend.board.dto.BoardDto;
import com.carrot.backend.boardImage.domain.QBoardImage;
import com.carrot.backend.boardLike.domain.QBoardLike;
import com.carrot.backend.boardReply.domain.QBoardReply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomizedBoardRepositoryImpl  implements CustomizedBoardRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public BoardDto getQslBoardAndImagesByBoardId(Integer boardId) {
        Board board = jpaQueryFactory
                .select(QBoard.board)
                .from(QBoard.board)
                .innerJoin(QBoardImage.boardImage)
                .on(QBoard.board.boardId.eq(boardId))
                .fetchOne();

        List<String> imagePath = new ArrayList<>();
        board.getImages().stream().forEach(boardImage->imagePath.add(boardImage.getBoardPath()));

        BoardDto boardDto = BoardDto.builder()
                .boardAddress(board.getBoardAddress())
                .boardAgree(board.getBoardAgree())
                .boardCategory(board.getBoardCategory())
                .boardContent(board.getBoardContent())
                .boardUserid(board.getBoardUserid())
                .boardView(board.getBoardView())
                .boardId(board.getBoardId())
                .boardChattingNum(board.getBoardChattingNum())
                .createDate(board.getCreateDate())
                .profileImage(board.getProfileImage())
                .images(imagePath)
                .build();

        return boardDto;
    }


    @Override
    @Transactional
    public void deleteQslBoardAndImageByBoardId(Integer boardId) {
        Long board2 = jpaQueryFactory
                .delete(QBoardLike.boardLike)
                .where(QBoardLike.boardLike.board.boardId.eq(boardId))
                .execute();
        Long boards = jpaQueryFactory
                .delete(QBoardReply.boardReply1)
                .where(QBoardReply.boardReply1.board.boardId.eq(boardId))
                .execute();
        Long board = jpaQueryFactory
                .delete(QBoard.board)
                .where(QBoard.board.boardId.eq(boardId))
                .execute();
    }
}