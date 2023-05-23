package com.carrot.backend.board.dao;

import com.carrot.backend.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> , CustomizedBoardRepository{
    List<Board> findAllByBoardCategory(String boardCategory);

    Optional<Board> findByBoardId(Integer boardId);

    List<Board> findByBoardAddressContainingOrBoardContentContaining(String keyword,String keyword1);


}
