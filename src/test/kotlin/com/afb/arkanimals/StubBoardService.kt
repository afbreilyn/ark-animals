package com.afb.arkanimals

class StubBoardService: BoardService {
    lateinit var setReturn_getBoards: List<Board>

    override fun getBoards(): List<Board> {
        return setReturn_getBoards
    }
}