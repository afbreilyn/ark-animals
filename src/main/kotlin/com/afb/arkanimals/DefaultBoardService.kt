package com.afb.arkanimals

import org.springframework.stereotype.Service

@Service
class DefaultBoardService(val boardRepository: BoardRepository): BoardService {
    override fun getBoards(): List<Board> {
        return boardRepository.getBoards()
    }
}