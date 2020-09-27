package com.afb.arkanimals

import org.springframework.stereotype.Repository

@Repository
class DefaultBoardRepository: BoardRepository {
    override fun getBoards(): List<Board> {
        return listOf(
                Board(
                        name = "my first board"
                )
        )
    }

    override fun getBoardById(id: String): Board {
        return Board(
                name = "this a specific board",
                id = 7.toLong()
        )
    }
}
