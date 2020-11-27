package com.afb.arkanimals.board

import java.util.*

interface BoardService {
    fun findById(id: Long): Optional<Board>
    fun save(board: Board): Board
}