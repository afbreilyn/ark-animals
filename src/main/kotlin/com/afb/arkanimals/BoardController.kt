package com.afb.arkanimals

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(val boardService: BoardService) {

    @GetMapping("/boards")
    fun getBoards(): List<Board> {
        return boardService.getBoards()
    }

    @GetMapping("/boards/{id}")
    fun getBoardById(@PathVariable id: String): Board {
        return boardService.getBoardById(id)
    }
}