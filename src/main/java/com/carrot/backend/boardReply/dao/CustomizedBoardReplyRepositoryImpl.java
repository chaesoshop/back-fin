package com.carrot.backend.boardReply.dao;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.boardReply.domain.BoardReply;
import com.carrot.backend.boardReply.domain.QBoardReply;
import com.carrot.backend.boardReply.dto.BoardReplyDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomizedBoardReplyRepositoryImpl implements CustomizedBoardReplyRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<BoardReplyDto> getQslReplyByBoard(Board board) {

        List<BoardReply> boardReply = jpaQueryFactory
                .select(QBoardReply.boardReply1)
                .from(QBoardReply.boardReply1)
                .where(QBoardReply.boardReply1.board.eq(board))
                .fetch();

        System.out.println("댓글" + boardReply.size());
        List<BoardReplyDto> boardReplyDto = new ArrayList<>();
                for(int i = 0; i < boardReply.size(); i++){
                    BoardReplyDto boardReplys = BoardReplyDto.builder()
                .ReplyUserAddress(boardReply.get(i).getReplyUserAddress())
                .replyUserid(boardReply.get(i).getUser().getUserid())
                .boardReply(boardReply.get(i).getBoardReply())
                .id(boardReply.get(i).getId())
                .profileImage(boardReply.get(i).getProfileImage())
                .ReplyNickname(boardReply.get(i).getReplyNickname())
                .build();
                boardReplyDto.add(boardReplys);
                }
                return  boardReplyDto;


    }
}
