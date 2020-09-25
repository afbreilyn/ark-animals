package com.afb.arkanimals

import org.springframework.stereotype.Repository

@Repository
class DefaultBoardRepository : BoardRepository {
    override fun getBoards(): List<Board> {
        return listOf(
                Board(
                        name = "my first board"
                )
        )
    }
}
