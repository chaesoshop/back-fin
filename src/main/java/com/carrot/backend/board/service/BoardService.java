package com.carrot.backend.board.service;

import com.carrot.backend.board.dao.BoardRepository;
import com.carrot.backend.board.dao.CustomizedBoardRepositoryImpl;
import com.carrot.backend.board.domain.Board;
import com.carrot.backend.board.dto.BoardDto;
import com.carrot.backend.util.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CustomizedBoardRepositoryImpl customizedBoardRepository;
    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public Board getBoard(Integer boardId) {
        return boardRepository.findById(boardId).orElseThrow(()->new DataNotFoundException("board not found"));
    }

    public Integer createBoard(BoardDto boardDto) {
        Board newboard = new Board();

        newboard.setBoardContent(boardDto.getBoardContent());
        newboard.setBoardChattingNum(0);
        newboard.setBoardAgree(0);
        newboard.setBoardAddress(boardDto.getBoardAddress());
        newboard.setBoardUserid(boardDto.getBoardUserid());
        newboard.setBoardCategory(boardDto.getBoardCategory());
        newboard.setBoardView(0);
        LocalDateTime date = LocalDateTime.now();
        String dates = date.toString();
        String yymmdd = dates.substring(0, 10);
        System.out.println(yymmdd);
        newboard.setCreateDate(yymmdd);
        boardRepository.save(newboard);

        return newboard.getBoardId();
    }

    public BoardDto getBoardWithImage(Integer boardId) {
        BoardDto board = customizedBoardRepository.getQslBoardAndImagesByBoardId(boardId);
        return  board;
    }

    public List<Board> getBoardCate(Integer num){
        List<Board> getBoardCate = boardRepository.findAllByBoardCategory("동네 카페");
        List<Board> boards = new ArrayList<>();
        if(getBoardCate.size() - (num + 1) * 7 > 0) {
            for (int i = getBoardCate.size() - (num * 7) - 1; i > getBoardCate.size() - (num + 1) * 7 - 1; i--) {

                boards.add(getBoardCate.get(i));
            }
        }else{
            for (int i = getBoardCate.size() - (num * 7) - 1; i >= 0; i--){
                boards.add(getBoardCate.get(i));

            }
        }
        return  boards;
    }

    public List<Board> getQueBoard(Integer qnum) {
        List<Board> getQueBoard = boardRepository.findAllByBoardCategory("동네 질문");
        List<Board> boardList = new ArrayList<>();
        if(getQueBoard.size() - (qnum + 1) * 7 > 0) {
            for (int i = getQueBoard.size() - (qnum * 7) - 1; i > getQueBoard.size() - (qnum + 1) * 7 - 1; i--) {

                boardList.add(getQueBoard.get(i));
            }
        }else{
            for (int i = getQueBoard.size() - (qnum * 7) - 1; i >= 0; i--){
                boardList.add(getQueBoard.get(i));

            }
        }
        return boardList;
    }


    public void boardView(Integer boardId) {
        Board board = boardRepository.findByBoardId(boardId).orElseThrow(()-> new DataNotFoundException("board not found"));
        board.setBoardView(board.getBoardView() + 1);
        boardRepository.save(board);
    }

    public Board setBoard(Integer boardId, BoardDto boardDto) {
        Board board = boardRepository.findByBoardId(boardId).orElseThrow(()-> new DataNotFoundException("board not found"));

        board.setBoardAddress(boardDto.getBoardAddress());
        board.setBoardContent(boardDto.getBoardContent());
        board.setBoardCategory(boardDto.getBoardCategory());
        LocalDateTime date = LocalDateTime.now();
        String dates = date.toString();
        String yymmdd = dates.substring(0, 10);
        System.out.println(yymmdd);
        board.setCreateDate(yymmdd);

        return boardRepository.save(board);

    }
}
