package com.carrot.backend.boardLike.dao;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.boardLike.domain.BoardLike;
import com.carrot.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {

    Optional<BoardLike> findByBoardAndUser(Board board, User user);


}
