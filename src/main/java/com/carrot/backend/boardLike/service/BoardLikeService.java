package com.carrot.backend.boardLike.service;

import com.carrot.backend.board.dao.BoardRepository;
import com.carrot.backend.board.domain.Board;
import com.carrot.backend.boardLike.dao.BoardLikeRepository;
import com.carrot.backend.boardLike.domain.BoardLike;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import com.carrot.backend.util.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class BoardLikeService {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final UserRepository userRepository;

    public boolean addBoardLike(Integer boardId, String userid) {
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Board board = boardRepository.findByBoardId(boardId).orElseThrow(()-> new DataNotFoundException("board not found"));
        if(isNotAlreadyLike(board, user)){
            boardLikeRepository.save(new BoardLike(board, user));
            int prev = board.getBoardAgree();
            board.setBoardAgree(prev + 1);
            return true;
        }else{
            BoardLike boardLike = boardLikeRepository.findByBoardAndUser(board, user).orElseThrow(()-> new DataNotFoundException("like not found"));
            int prev = board.getBoardAgree();
            board.setBoardAgree(prev - 1);
            boardLikeRepository.delete(boardLike);
            return false;
        }
    }

    private boolean isNotAlreadyLike(Board board, User user) {
        return boardLikeRepository.findByBoardAndUser(board, user).isEmpty();
    }
    public boolean likeCheck(Integer boardId, String userid) {
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Board board = boardRepository.findByBoardId(boardId).orElseThrow(()-> new DataNotFoundException("board not found"));

        if(isNotAlreadyLike(board, user)){
            return false;
        }else{
            return true;
        }
    }




}
