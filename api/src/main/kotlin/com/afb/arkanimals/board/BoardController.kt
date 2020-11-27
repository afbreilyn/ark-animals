package com.afb.arkanimals.board

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class BoardController {
    @Autowired
    lateinit var boardService: BoardService

    @GetMapping("/boards/{id}")
    fun getBoardById(@PathVariable id: Long): ResponseEntity<Board?> {
        val optionalBoard: Optional<Board> = boardService.findById(id)
        val board: Board? = optionalBoard.get()

        val status: HttpStatus = if (null != board) {
            HttpStatus.OK
        } else {
            HttpStatus.I_AM_A_TEAPOT
        }

        return ResponseEntity(board, status)
    }

    @PostMapping("/boards")
    fun createBoard(@RequestBody board: Board): ResponseEntity<Board> {
        val status = HttpStatus.CREATED
        val saved: Board = boardService.save(board)
        return ResponseEntity(saved, status)
    }
}
