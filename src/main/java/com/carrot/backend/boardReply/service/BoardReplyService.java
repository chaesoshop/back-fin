package com.carrot.backend.boardReply.service;

import com.carrot.backend.board.dao.BoardRepository;
import com.carrot.backend.board.domain.Board;
import com.carrot.backend.boardReply.dao.BoardReplyRepository;
import com.carrot.backend.boardReply.dao.CustomizedBoardReplyRepositoryImpl;
import com.carrot.backend.boardReply.domain.BoardReply;
import com.carrot.backend.boardReply.dto.BoardReplyDto;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import com.carrot.backend.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardReplyService {
    private final BoardReplyRepository boardReplyRepository;
    private final CustomizedBoardReplyRepositoryImpl customizedBoardReplyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardReply getReply(Integer boardId) {
        return boardReplyRepository.findById(boardId).orElseThrow(() -> new DataNotFoundException("boardReply not found"));
    }

    public boolean createReply(BoardReplyDto boardReplyDto, Integer boardId) {
      try   {
        BoardReply boardReply = new BoardReply();
        Board board = boardRepository.findByBoardId(boardId).get();
        String userid = boardReplyDto.getReplyUserid();
        User user = userRepository.findByUserid(userid).get();
        LocalDateTime date = LocalDateTime.now();
        String dates = date.toString();
        String yymmdd = dates.substring(0,10);
        boardReply.setUser(user);
        boardReply.setReplyUserAddress(user.getAddress());
        boardReply.setProfileImage(user.getProfileImage());
        boardReply.setReplyNickname(user.getNickname());
        boardReply.setBoard(board);
        boardReply.setBoardReply(boardReplyDto.getBoardReply());
        boardReply.setCreateDate(yymmdd);
        board.setBoardChattingNum(board.getBoardChattingNum() + 1);
        boardRepository.save(board);
        boardReplyRepository.save(boardReply);
      }catch (Exception e){
          return false;
      }
        return true;
    }

    public List<BoardReplyDto> getBoardAndReply(Integer boardId) {
        Board board = boardRepository.findByBoardId(boardId).get();
        List<BoardReplyDto> boardReply = customizedBoardReplyRepository.getQslReplyByBoard(board);
        return boardReply;
    }

    public void delete(Integer boardId) {
        Board board  = boardRepository.findByBoardId(boardId).get();
        boardReplyRepository.deleteAllByBoard(board);
    }

    public void replyDelete(Integer replyId) {

        BoardReply boardReply = boardReplyRepository.findById(replyId).orElseThrow(()->new DataNotFoundException("reply not found"));
        Board board = boardReply.getBoard();
        board.setBoardChattingNum(board.getBoardChattingNum() - 1);
        boardRepository.save(board);
        boardReplyRepository.deleteById(replyId);

    }

    public int test(String a,String p){
        int answer = 0;

        return answer;
    }
}
