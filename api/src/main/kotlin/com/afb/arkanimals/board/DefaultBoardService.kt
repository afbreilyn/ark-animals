package com.afb.arkanimals.board

import com.afb.arkanimals.stickynote.StickyNoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultBoardService : BoardService {

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var stickyNoteRepository: StickyNoteRepository

    override fun findById(id: Long): Optional<Board> {
        return boardRepository.findById(id).map {
            Board(
                id = it.id,
                title = it.title,
                stickyNotes = stickyNoteRepository.findAllByBoardId(it.id!!).toList()
            )
        }
    }

    override fun save(board: Board): Board {
        val boardEntity = BoardEntity(title = board.title)
        val savedBoard = boardRepository.save(boardEntity)
        return Board(
            title = savedBoard.title,
            id = savedBoard.id,
            stickyNotes = emptyList()
        )
    }
}