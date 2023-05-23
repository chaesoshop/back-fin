package com.carrot.backend.boardImage.controller;

import com.carrot.backend.board.service.BoardService;
import com.carrot.backend.boardImage.service.BoardImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BoardImageController {
    private final BoardService boardService;
    private final BoardImageService boardImageService;
}
