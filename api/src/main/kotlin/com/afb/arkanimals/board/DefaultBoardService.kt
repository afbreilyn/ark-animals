package com.afb.arkanimals.board

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultBoardService : BoardService {

    @Autowired
    lateinit var boardRepository: BoardRepository

    override fun findById(id: Long): Optional<Board> {
        return boardRepository.findById(id)
    }

    override fun save(board: Board): Board {
        return boardRepository.save(board)
    }
}