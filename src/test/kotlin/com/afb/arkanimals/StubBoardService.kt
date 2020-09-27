package com.afb.arkanimals

class StubBoardService: BoardService {
    lateinit var setReturn_getBoards: List<Board>
    lateinit var setReturn_getBoardById: Board

    override fun getBoards(): List<Board> {
        return setReturn_getBoards
    }

    override fun getBoardById(id: String): Board {
        return setReturn_getBoardById
    }
}